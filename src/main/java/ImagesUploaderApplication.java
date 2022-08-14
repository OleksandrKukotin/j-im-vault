import jakarta.servlet.ServletContextEvent;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class ImagesUploaderApplication {

    private static final String WEBAPP_DIR_LOCATION = "src/main/webapp/";
    private static final int TOMCAT_PORT = 8082;

    public static void main(String[] args) throws LifecycleException { // TODO: handle exception inside method, dont throw it using 'throws'
        final DBConnector dbConnector = new DBConnector();
        final ImageRepositoryImpl imageRepository = new ImageRepositoryImpl(dbConnector.get()); // TODO: inline this line, because it is used a once
        final FlywayListener flywayListener = new FlywayListener(dbConnector.get()); // TODO: move this listener closer to flywayListener.contextInitialized(sce)
        final ImageService imageService = new ImageService(imageRepository); // TODO: move closer to the place where it used

        final Tomcat tomcat = new Tomcat();
        tomcat.setPort(TOMCAT_PORT);

        final Context context = tomcat.addWebapp("", new File(WEBAPP_DIR_LOCATION).getAbsolutePath());
        final ServletContextEvent sce = new ServletContextEvent(context.getServletContext()); // TODO: rename sce to smth normal - java is not a python. Also move it closer to flywayListener.contextInitialized(sce);
        context.setAllowCasualMultipartParsing(true);

        tomcat.addServlet(context.getPath(), "AddingImage", new AddingImageServlet(imageService)); // TODO: extract "AddingImage" to constant
        context.addServletMappingDecoded("/imageUpload", "AddingImage");

        tomcat.addServlet(context.getPath(), "DisplayImages", new DisplayImagesServlet(imageService)); // TODO: extract "DisplayImages" to constant
        context.addServletMappingDecoded("/imagesPreview", "DisplayImages");

        tomcat.addServlet(context.getPath(), "SearchBySizeRange", new SearchBySizeRangeServlet(imageService)); // TODO: extract "SearchBySizeRange" to constant
        context.addServletMappingDecoded("/searchBySizeRange", "SearchBySizeRange");
        flywayListener.contextInitialized(sce);

        tomcat.getConnector();
        tomcat.start();
        tomcat.getServer().await();
    }
}
