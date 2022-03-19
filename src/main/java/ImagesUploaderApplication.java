import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;

public class ImagesUploaderApplication {

    private static final String WEBAPP_DIR_LOCATION = "src/main/webapp/";
    private static final int TOMCAT_PORT = 8082;

    public static void main(String[] args) throws ServletException, LifecycleException {
        final DBConnector dbConnector = new DBConnector();
        final TextRepositoryDB textRepository = new TextRepositoryDB(dbConnector.get());
        final TextService textService = new TextService(textRepository);

        final Tomcat tomcat = new Tomcat();
        tomcat.setPort(TOMCAT_PORT);

        final Context context = tomcat.addWebapp("", new File(WEBAPP_DIR_LOCATION).getAbsolutePath());

        Tomcat.addServlet(context, "AddingText", new AddingTextServlet(textService));
        context.addServletMapping("/index", "AddingText");

        tomcat.start();
        tomcat.getServer().await();
    }
}
