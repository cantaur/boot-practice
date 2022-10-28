package com.cantaur.practice.config.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.cantaur.practice.mapper", sqlSessionFactoryRef = "memberSqlSessionFactory")
public class MemberMybatisConfig extends AbstractMybatisConfig{

    public MemberMybatisConfig(DataSource dataSource){
        this.dataSource = dataSource;
        this.mapperLocationsPath = "classpath:/sql/*.xml";
    }



    @Override
    @Bean(name = "memberSqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory() throws Exception {
        return configureSqlSessionFactory().getObject();
    }

    @Override
    @Bean(name = "memberTransactionManager")
    public DataSourceTransactionManager getTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }





}
