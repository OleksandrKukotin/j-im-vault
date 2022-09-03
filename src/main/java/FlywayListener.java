import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

public class FlywayListener implements ServletContextListener {

    private final DataSource dataSource;

    public FlywayListener(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        final Flyway flyway = new Flyway(Flyway.configure().dataSource(this.dataSource));
        flyway.baseline();
        flyway.migrate();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContextListener.super.contextDestroyed(servletContextEvent);
    }
}
