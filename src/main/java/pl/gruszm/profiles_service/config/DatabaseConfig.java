package pl.gruszm.profiles_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig
{
    @Value("${PROFILES_DB_USER}")
    private String dbUsername;

    @Value("${PROFILES_DB_PASSWORD}")
    private String dbPassword;

    @Value("${PROFILES_DB_SERVICE_NAME}")
    private String dbServiceName;

    @Value("${PROFILES_DB_SERVICE_PORT}")
    private String dbServicePort;

    @Value("${PROFILES_DB_NAME}")
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
                .url(getDataSourceUrl())
                .username(dbUsername)
                .password(dbPassword)
                .build();
    }

    private String getDataSourceUrl()
    {
        return "jdbc:postgresql://"
                + dbServiceName + ":"
                + dbServicePort + "/"
                + dbName;
    }
}
