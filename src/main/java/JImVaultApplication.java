import configuration.DataSourceProvider;
import configuration.amazon.AmazonS3Service;
import configuration.flyway.FlywayListener;
import image.ImageService;
import image.PostgreSQLImageRepository;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.http.HttpServlet;
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

        initializeHttpServlet(ADDING_IMAGE_SERVLET_NAME, "/imageUpload",
            new AddingImageServlet(imageService, amazonS3Service),
            context, tomcat);
        initializeHttpServlet(DISPLAY_IMAGES_SERVLET_NAME, "/imagesPreview",
            new DisplayImagesServlet(imageService, amazonS3Service),
            context, tomcat);
        initializeHttpServlet(SEARCH_BY_SIZE_RANGE_SERVLET_NAME, "/searchBySizeRange",
            new SearchBySizeRangeServlet(imageService, amazonS3Service),
            context, tomcat);
        initializeFlyway(context, dataSource);

        tomcat.getConnector();
        try {
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            logger.error("Error starting or running Tomcat", e);
        } finally {
            try {
                tomcat.stop();
                tomcat.destroy();
            } catch (LifecycleException e) {
                logger.error("Error stopping Tomcat:", e);
            }
        }
    }

    private static void initializeHttpServlet(String name, String path, HttpServlet servlet, Context context, Tomcat tomcat) {
        tomcat.addServlet(context.getPath(), name, servlet);
        context.addServletMappingDecoded(path, name);
    }

    private static void initializeFlyway(Context context, DataSource dataSource) {
        final FlywayListener flywayListener = new FlywayListener(dataSource);
        final ServletContextEvent servletContextEvent = new ServletContextEvent(context.getServletContext());
        flywayListener.contextInitialized(servletContextEvent);
    }
}
