package configuration;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceProvider {

    private static final String APP_PROPERTIES_FILENAME = "app.properties";

    private final Properties properties;

    public DataSourceProvider() {
        this.properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(APP_PROPERTIES_FILENAME)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new PropertiesFileNotExists("Properties file is not exists", e);
        }
    }

    public DataSource create() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerNames(new String[]{properties.getProperty("dataSource.host")});
        dataSource.setPortNumbers(new int[]{Integer.parseInt(properties.getProperty("dataSource.port"))});
        dataSource.setUser(properties.getProperty("dataSource.user"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));
        dataSource.setDatabaseName(properties.getProperty("dataSource.databaseName"));
        return dataSource;
    }

    private static final class PropertiesFileNotExists extends RuntimeException {

        private PropertiesFileNotExists(String message, Exception exception) {
            super(message, exception);
        }
    }
}
