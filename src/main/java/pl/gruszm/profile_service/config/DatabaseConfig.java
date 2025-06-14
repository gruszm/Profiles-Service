package pl.gruszm.profile_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig
{
    @Value("${PROFILE_DB_USER}")
    private String dbUsername;

    @Value("${PROFILE_DB_PASSWORD}")
    private String dbPassword;

    @Value("${PROFILE_DB_SERVICE_NAME}")
    private String dbServiceName;

    @Value("${PROFILE_DB_SERVICE_PORT}")
    private String dbServicePort;

    @Value("${PROFILE_DB_NAME}")
    private String dbName;

    @Bean
    public DataSource getDataSource()
    {
        System.out.println("Trying to connect to the database with parameters:\n" +
                "\tdbName = " + dbName + "\n" +
                "\tdbServicePort = " + dbServicePort + "\n" +
                "\tdbUsername = " + dbUsername + "\n" +
                "\tdbPassword = " + dbPassword + "\n" +
                "\tdbServiceName = " + dbServiceName);

        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url(getDataSourceUrl(dbServiceName))
                .username(dbUsername)
                .password(dbPassword)
                .build();
    }

    private String getDataSourceUrl(String dbServiceName)
    {
        return "jdbc:postgresql://"
                + dbServiceName + ":"
                + dbServicePort + "/"
                + dbName;
    }
}
