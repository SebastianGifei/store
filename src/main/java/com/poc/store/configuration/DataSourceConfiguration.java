package com.poc.store.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Primary
public class DataSourceConfiguration extends DataSourceProperties {

    @Value("${database-password}")
    private String database_password;

    @Value("${database-username}")
    private String database_username;

    @Value("${database-url}")
    private String database_url;

    @Value("${database-driver}")
    private String database_driver;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setUsername(database_username);
        this.setPassword(database_password);
        this.setDriverClassName(database_driver);
        this.setUrl(database_url);
        super.afterPropertiesSet();
    }
}
