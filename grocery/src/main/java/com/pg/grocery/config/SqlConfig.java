package com.pg.grocery.config;

import com.zaxxer.hikari.HikariDataSource;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SqlConfig {

    //all the below can be picked from a config file
    private int getDatabaseMaxTotal() {
        return 10;
    }

    private String getDatabaseUser() {
        return "####";
    }

    private String getDatabasePassword() {
        return "####";
    }

    private String getDatabaseSchema() {
        return "####";
    }

    private String getDatabaseUrl() {
        return "jdbc:mysql://localhost:3306";
    }

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(getDatabaseUrl() + "/" + getDatabaseSchema());
        hikariDataSource.setUsername(getDatabaseUser());
        hikariDataSource.setPassword(getDatabasePassword());
        hikariDataSource.setMaximumPoolSize(getDatabaseMaxTotal());
        hikariDataSource.setMinimumIdle(0);

        return ProxyDataSourceBuilder.create(hikariDataSource)
                .beforeQuery((execInfo, queryForList) -> {
                   System.out.println("Executing the query: " + execInfo.getStatement());
                })
                .afterQuery((execInfo, queryInfoList) -> {
                    if (execInfo.getElapsedTime() > 2000) {
                        String errorMsg = "HIGH TIME - " + execInfo.getElapsedTime() + " ms taken to process: " + execInfo.getStatement();
                        System.out.println(errorMsg);

                    }
                })
                .build();
    }
}
