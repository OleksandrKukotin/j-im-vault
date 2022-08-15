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
        final ImageRepositoryImpl imageRepository = new ImageRepositoryImpl(dbConnector.get());
        final ImageService imageService = new ImageService(imageRepository);

        final Tomcat tomcat = new Tomcat();
        tomcat.setPort(TOMCAT_PORT);

        final Context context = tomcat.addWebapp("", new File(WEBAPP_DIR_LOCATION).getAbsolutePath());
        context.setAllowCasualMultipartParsing(true);

        tomcat.addServlet(context.getPath(), "AddingImage", new AddingImageServlet(imageService, amazonS3Service));
        context.addServletMappingDecoded("/imageUpload", "AddingImage");

        tomcat.addServlet(context.getPath(), "DisplayImages", new DisplayImagesServlet(imageService, amazonS3Service));
        context.addServletMappingDecoded("/imagesPreview", "DisplayImages");

        tomcat.addServlet(context.getPath(), "SearchBySizeRange", new SearchBySizeRangeServlet(imageService, amazonS3Service));
        context.addServletMappingDecoded("/searchBySizeRange", "SearchBySizeRange");

        final FlywayListener flywayListener = new FlywayListener(dbConnector.get());
        final ServletContextEvent servletContextEvent = new ServletContextEvent(context.getServletContext());
        flywayListener.contextInitialized(servletContextEvent);

        tomcat.getConnector();
        try {
            tomcat.start();
        } catch (LifecycleException e) {
            logger.error("LifecycleException had been catched");
        }
        tomcat.getServer().await();
    }
}
