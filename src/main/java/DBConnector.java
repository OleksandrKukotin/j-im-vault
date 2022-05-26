import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {

    private static final String CREATE_IMAGES_TABLE = "CREATE TABLE IF NOT EXISTS images (id bigserial PRIMARY KEY, imgName varchar(255), img bytea)";
    private static final String CREATE_IMAGES_ADDR_TABLE = "CREATE TABLE IF NOT EXISTS image_addr (id bigserial PRIMARY KEY, imgName varchar(255), key varchar(255))";
    private static final String APP_PROPERTIES_FILENAME = "app.properties";
    private static final String PG_DEFAULT_VALUE = "postgres";
    private static final int DATASOURCE_DEFAULT_PORT = 5434;

    private final Properties properties;

    DBConnector() {
        this.properties = new Properties();

        try (final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(APP_PROPERTIES_FILENAME)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new PropertiesFileNotExists("Application properties file not exists");
        }
    }

    DataSource get() {
        try {
            final PGSimpleDataSource dataSource = new PGSimpleDataSource();
            dataSource.setServerNames(new String[]{properties.getProperty("dataSource.host")});
            dataSource.setPortNumbers(new int[]{Integer.parseInt(properties.getProperty("dataSource.port", String.valueOf(DATASOURCE_DEFAULT_PORT)))});
            dataSource.setUser(properties.getProperty("dataSource.user", PG_DEFAULT_VALUE));
            dataSource.setPassword(properties.getProperty("dataSource.password", PG_DEFAULT_VALUE));
            dataSource.setDatabaseName(properties.getProperty("dataSource.databaseName", PG_DEFAULT_VALUE));
            dataSource.getConnection().createStatement().execute(CREATE_IMAGES_ADDR_TABLE);
            return dataSource;
        } catch (SQLException exception) {
            throw new RuntimeException();
        }
    }

    private static final class PropertiesFileNotExists extends RuntimeException {
        public PropertiesFileNotExists(String errorMessage) {
            super(errorMessage);
        }
    }
}
