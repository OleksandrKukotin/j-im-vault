import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

public class FlywayListener implements ServletContextListener {

    private static final Logger log = Logger.getLogger(FlywayListener.class);
    private final DataSource dataSource;

    public FlywayListener(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final Flyway flyway = new Flyway(Flyway.configure().dataSource(this.dataSource));
        flyway.baseline();
        flyway.migrate();
        log.debug("Migration successfully ended!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
