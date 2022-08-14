import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

public class FlywayListener implements ServletContextListener {

    private static final Logger log = Logger.getLogger(FlywayListener.class); // TODO: rename from log to logger
    private final DataSource dataSource;

    public FlywayListener(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final Flyway flyway = new Flyway(Flyway.configure().dataSource(this.dataSource));
        flyway.baseline(); // TODO - FYE: https://stackoverflow.com/questions/27972214/what-is-flyway-baseline-feature-good-for
        flyway.migrate();
        log.debug("Migration successfully ended!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) { // TODO: rename sce to smth normal - java is not a python. Also move it closer to flywayListener.contextInitialized(sce);
        ServletContextListener.super.contextDestroyed(sce);
    }
}
