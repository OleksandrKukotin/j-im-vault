import jakarta.servlet.ServletContextEvent;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class ImagesUploaderApplication {

    private static final String WEBAPP_DIR_LOCATION = "src/main/webapp/";
    private static final int TOMCAT_PORT = 8082;

    public static void main(String[] args) throws LifecycleException {
        final DBConnector dbConnector = new DBConnector();
        final ImageRepositoryImpl imageRepository = new ImageRepositoryImpl(dbConnector.get());
        final FlywayListener flywayListener = new FlywayListener(dbConnector.get());
        final ImageService imageService = new ImageService(imageRepository);

        final Tomcat tomcat = new Tomcat();
        tomcat.setPort(TOMCAT_PORT);

        final Context context = tomcat.addWebapp("", new File(WEBAPP_DIR_LOCATION).getAbsolutePath());
        final ServletContextEvent sce = new ServletContextEvent(context.getServletContext());
        context.setAllowCasualMultipartParsing(true);

        tomcat.addServlet(context.getPath(), "AddingImage", new AddingImageServlet(imageService));
        context.addServletMappingDecoded("/imageUpload", "AddingImage");

        tomcat.addServlet(context.getPath(), "DisplayImages", new DisplayImagesServlet(imageService));
        context.addServletMappingDecoded("/imagesPreview", "DisplayImages");

        tomcat.addServlet(context.getPath(), "SearchBySizeRange", new SearchBySizeRangeServlet(imageService));
        context.addServletMappingDecoded("/searchBySizeRange", "SearchBySizeRange");
        flywayListener.contextInitialized(sce);

        tomcat.getConnector();
        tomcat.start();
        tomcat.getServer().await();
    }
}
