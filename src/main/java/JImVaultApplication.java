import configuration.DataSourceProvider;
import configuration.amazon.AmazonS3Service;
import configuration.flyway.FlywayListener;
import image.ImageService;
import image.PostgreSQLImageRepository;
import jakarta.servlet.ServletContextEvent;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.File;

public class JImVaultApplication {

    private static final String ADDING_IMAGE_SERVLET_NAME = "AddingImage";
    private static final String DISPLAY_IMAGES_SERVLET_NAME = "DisplayImages";
    private static final String SEARCH_BY_SIZE_RANGE_SERVLET_NAME = "SearchBySizeRange";

    private static final String WEBAPP_DIR_LOCATION = "src/main/webapp/";
    private static final int TOMCAT_PORT = 8082;
    private static final Logger logger = LoggerFactory.getLogger(JImVaultApplication.class);

    public static void main(String[] args) {
        final Tomcat tomcat = new Tomcat();
        tomcat.setPort(TOMCAT_PORT);

        final Context context = tomcat.addWebapp("", new File(WEBAPP_DIR_LOCATION).getAbsolutePath());
        context.setAllowCasualMultipartParsing(true);

        final DataSource dataSource = new DataSourceProvider().create();
        final ImageService imageService = new ImageService(new PostgreSQLImageRepository(dataSource));
        final AmazonS3Service amazonS3Service = new AmazonS3Service();

        // TODO: Create a helper method that takes the servlet class, servlet name, and mapping path as arguments.
        final AddingImageServlet addingImageServlet = new AddingImageServlet(imageService, amazonS3Service);
        tomcat.addServlet(context.getPath(), ADDING_IMAGE_SERVLET_NAME, addingImageServlet);
        context.addServletMappingDecoded("/imageUpload", ADDING_IMAGE_SERVLET_NAME);

        final DisplayImagesServlet displayImagesServlet = new DisplayImagesServlet(imageService, amazonS3Service);
        tomcat.addServlet(context.getPath(), DISPLAY_IMAGES_SERVLET_NAME, displayImagesServlet);
        context.addServletMappingDecoded("/imagesPreview", DISPLAY_IMAGES_SERVLET_NAME);

        final SearchBySizeRangeServlet searchBySizeRangeServlet = new SearchBySizeRangeServlet(imageService, amazonS3Service);
        tomcat.addServlet(context.getPath(), SEARCH_BY_SIZE_RANGE_SERVLET_NAME, searchBySizeRangeServlet);
        context.addServletMappingDecoded("/searchBySizeRange", SEARCH_BY_SIZE_RANGE_SERVLET_NAME);

        initializeFlyway(context, dataSource);

        // TODO: Wrap tomcat.start() and tomcat.getServer().await() in try-catch-finally block to ensure server is stopped properly
        tomcat.getConnector();
        try {
            tomcat.start();
        } catch (LifecycleException e) {
            logger.error("Something went wrong during tomcat running");
        }
        tomcat.getServer().await();
    }

    private static void initializeFlyway(Context context, DataSource dataSource) {
        // TODO: Refactor flywayListener to use try-with-resources to close the connection automatically
        final FlywayListener flywayListener = new FlywayListener(dataSource);
        final ServletContextEvent servletContextEvent = new ServletContextEvent(context.getServletContext());
        flywayListener.contextInitialized(servletContextEvent);
    }
}
