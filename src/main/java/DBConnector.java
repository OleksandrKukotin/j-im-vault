import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

// TODO: integrate Flyway and exctract CREATE_IMAGES_ADDR_TABLE constant in migration file
public class DBConnector {

    // TODO: what is ADDR???? Rename to more clearly
    private static final String CREATE_IMAGES_ADDR_TABLE = "CREATE TABLE IF NOT EXISTS images (id bigserial PRIMARY KEY, name varchar(255), int size, time timestamp, key varchar(255))";
    private static final String APP_PROPERTIES_FILENAME = "app.properties";
    private static final String PG_DEFAULT_VALUE = "postgres";
    private static final int DATASOURCE_DEFAULT_PORT = 5434;

    private final Properties properties;

    DBConnector() {
        this.properties = new Properties();

        try (final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(APP_PROPERTIES_FILENAME)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new PropertiesFileNotExists("Application properties file not exists"); // TODO: also pass the exception as an argument; add logging!!!!!!!!!
        }
    }

    DataSource get() {
        try {
            final PGSimpleDataSource dataSource = new PGSimpleDataSource();
            dataSource.setServerNames(new String[]{properties.getProperty("dataSource.host")}); // TODO: use single method setServerName(...)
            dataSource.setPortNumbers(new int[]{Integer.parseInt(properties.getProperty("dataSource.port", String.valueOf(DATASOURCE_DEFAULT_PORT)))}); // TODO: use single method setServerPort(...)
            dataSource.setUser(properties.getProperty("dataSource.user", PG_DEFAULT_VALUE));
            dataSource.setPassword(properties.getProperty("dataSource.password", PG_DEFAULT_VALUE));
            dataSource.setDatabaseName(properties.getProperty("dataSource.databaseName", PG_DEFAULT_VALUE));
            dataSource.getConnection().createStatement().execute(CREATE_IMAGES_ADDR_TABLE); // TODO: use try-with-resources
            return dataSource;
        } catch (SQLException exception) {
            // TODO: DON'T throw Runtime Exceptions, it doesn't provide any information
            // TODO: create custom exception and log the problem! If dont understand what I want CONTACT with me!
            throw new RuntimeException();
        }
    }

    private static final class PropertiesFileNotExists extends RuntimeException {

        public PropertiesFileNotExists(String errorMessage) {
            super(errorMessage);
        }
    }
}
