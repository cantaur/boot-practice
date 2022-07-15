package com.cantaur.practice.config.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.cantaur.practice.mapper",  sqlSessionFactoryRef = "sqlSessionFactory")
public class MyBatisConfig extends AbstractMybatisConfig{

    public MyBatisConfig(DataSource dataSource) {
        this.dataSource = dataSource;
        this.mapperLocationsPath = "classpath:/sql/*.xml";
    }

    @Override
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory() throws Exception {
        return configureSqlSessionFactory().getObject();
    }

//    @Override
//    @Bean(name = "memberTransactionManager")
//    public DataSourceTransactionManager getTransactionManager() {
//        return new DataSourceTransactionManager(dataSource);
//    }
}
