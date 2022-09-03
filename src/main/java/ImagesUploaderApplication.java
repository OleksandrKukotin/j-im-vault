import jakarta.servlet.ServletContextEvent;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.log4j.Logger;

import java.io.File;

public class ImagesUploaderApplication {

    private static final String WEBAPP_DIR_LOCATION = "src/main/webapp/";
    private static final int TOMCAT_PORT = 8082;
    private static final Logger logger = Logger.getLogger(ImagesUploaderApplication.class);

    public static void main(String[] args) {
        final AmazonS3Service amazonS3Service = new AmazonS3Service();
        final DBConnector dbConnector = new DBConnector();
        final Tomcat tomcat = new Tomcat();
        tomcat.setPort(TOMCAT_PORT);

        final Context context = tomcat.addWebapp("", new File(WEBAPP_DIR_LOCATION).getAbsolutePath());
        final ImageService imageService = new ImageService(new ImageRepositoryImpl(dbConnector.get()));

        context.setAllowCasualMultipartParsing(true);
        final AddingImageServlet addingImageServlet = new AddingImageServlet(imageService, amazonS3Service);
        final String ADDING_IMAGE_SERVLET_NAME = "AddingImage";
        tomcat.addServlet(context.getPath(), ADDING_IMAGE_SERVLET_NAME, addingImageServlet);
        context.addServletMappingDecoded("/imageUpload", ADDING_IMAGE_SERVLET_NAME);

        final DisplayImagesServlet displayImagesServlet = new DisplayImagesServlet(imageService, amazonS3Service);
        final String DISPLAY_IMAGES_SERVLET_NAME = "DisplayImages";
        tomcat.addServlet(context.getPath(), DISPLAY_IMAGES_SERVLET_NAME, displayImagesServlet);
        context.addServletMappingDecoded("/imagesPreview", DISPLAY_IMAGES_SERVLET_NAME);

        final SearchBySizeRangeServlet searchBySizeRangeServlet = new SearchBySizeRangeServlet(imageService, amazonS3Service);
        final String SEARCH_BY_SIZE_RANGE_SERVLET_NAME = "SearchBySizeRange";
        tomcat.addServlet(context.getPath(), SEARCH_BY_SIZE_RANGE_SERVLET_NAME, searchBySizeRangeServlet);
        context.addServletMappingDecoded("/searchBySizeRange", SEARCH_BY_SIZE_RANGE_SERVLET_NAME);

        final FlywayListener flywayListener = new FlywayListener(dbConnector.get());
        final ServletContextEvent servletContextEvent = new ServletContextEvent(context.getServletContext());
        flywayListener.contextInitialized(servletContextEvent);

        tomcat.getConnector();
        try {
            tomcat.start();
        } catch (LifecycleException e) {
            logger.error("LifecycleException had been caught");
        }
        tomcat.getServer().await();
    }
}
