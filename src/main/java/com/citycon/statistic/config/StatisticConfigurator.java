package com.citycon.statistic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.sql.DataSource;

/**
 * Configuration class for Spring part of CityCon app. Configures Spring JDBC access.
 *
 * @author Mike
 * @version 2.0
 */
@Configuration
@ComponentScan(basePackages = {"com.citycon.statistic.controllers",
                                "com.citycon.statistic.repositories"})
public class StatisticConfigurator {
    @Bean
    public JndiObjectFactoryBean dataSource() {
        JndiObjectFactoryBean factory = new JndiObjectFactoryBean();
        factory.setJndiName("jdbc/City");
        factory.setProxyInterface(DataSource.class);
        return factory;
    }

    @Bean
    @Autowired
    @Qualifier("dataSource")
    public NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
    @Bean
    @Autowired
    @Qualifier("dataSource")
    public JdbcOperations jdbcOperations(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
