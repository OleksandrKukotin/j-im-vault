import org.apache.log4j.Logger;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConnector {

    private static final String APPLICATION_PROPERTIES_FILENAME = "app.properties";
    private static final String PG_DEFAULT_VALUE = "postgres";
    private static final int DATASOURCE_DEFAULT_PORT = 5434;
    private static final Logger logger = Logger.getLogger(DBConnector.class);

    private final Properties properties;

    DBConnector() {
        this.properties = new Properties();
        try (final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(APPLICATION_PROPERTIES_FILENAME)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Application properties file not exists");
            throw new PropertiesFileNotExists(e);
        }
    }

    DataSource get() {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerNames(new String[]{properties.getProperty("dataSource.host")});
        dataSource.setPortNumbers(new int[]{Integer.parseInt(properties.getProperty("dataSource.port", String.valueOf(DATASOURCE_DEFAULT_PORT)))});
        dataSource.setUser(properties.getProperty("dataSource.user", PG_DEFAULT_VALUE));
        dataSource.setPassword(properties.getProperty("dataSource.password", PG_DEFAULT_VALUE));
        dataSource.setDatabaseName(properties.getProperty("dataSource.databaseName", PG_DEFAULT_VALUE));
        return dataSource;
    }

    private static final class PropertiesFileNotExists extends RuntimeException {
        public PropertiesFileNotExists(IOException e) {
            super(e);
        }
    }
}
