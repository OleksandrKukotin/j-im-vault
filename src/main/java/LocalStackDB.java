import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.List;

public class LocalStackDB {

    private final static String ENDPOINT = "https://0.0.0.0:4566";
    private final static String REGION = "Ukraine";
    private String disableSSLVerify = "false";
    private DynamoDB dynamoDB;

    void init() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(ENDPOINT, REGION))
            .build();
        this.dynamoDB = new DynamoDB(client);
    }

    public Boolean isDisableSSLVerify() {
        return Boolean.parseBoolean(disableSSLVerify);
    }

    public void setDisableSSLVerify(boolean disableSSLVerification) {
        this.disableSSLVerify = String.format("%b",disableSSLVerification);
        System.setProperty(SDKGlobalConfiguration.DISABLE_CERT_CHECKING_SYSTEM_PROPERTY, this.disableSSLVerify);
    }

    public LocalStackDB createTable(String tableName, List<KeySchemaElement> keySchemaList,
                                          List<AttributeDefinition> attributeDefinitionsList,
                                          ProvisionedThroughput provisionedThroughput) {
        try {
            Table table = this.dynamoDB.createTable(tableName, keySchemaList, attributeDefinitionsList, provisionedThroughput);
            table.waitForActive();
        }
        catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }
        return this;
    }

    public LocalStackDB deleteTable(String tableName) {
        try {
            this.dynamoDB.getTable(tableName).delete();
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
        return this;
    }

    public Table getTable(String tableName) {
        try {
            for(Table table : this.getTables()){
                if(table.getTableName().equals(tableName)){
                    return table;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public DynamoDB getClient() {
        return this.dynamoDB;
    }

    public TableCollection<ListTablesResult> getTables() {
        return this.dynamoDB.listTables();
    }
}
