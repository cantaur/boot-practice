package com.cantaur.practice.config.mybatis;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;

import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;



public abstract class AbstractMybatisConfig {

    protected DataSource dataSource;

    protected String mapperLocationsPath;

    private static final String TYPE_ALIASES_PACKAGE = "com.cantaur.practice.model";


    protected SqlSessionFactoryBean configureSqlSessionFactory() throws Exception {
        Configuration configuration = new Configuration();
        configuration.setLazyLoadingEnabled(Boolean.TRUE);
        configuration.setCacheEnabled(Boolean.FALSE);
        configuration.setUseGeneratedKeys(Boolean.TRUE);
        configuration.setDefaultExecutorType(ExecutorType.REUSE);
        configuration.setMapUnderscoreToCamelCase(Boolean.TRUE);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setCallSettersOnNulls(Boolean.TRUE);

        PathMatchingResourcePatternResolver pathResolver = new PathMatchingResourcePatternResolver();

        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);
        sessionFactoryBean.setMapperLocations(pathResolver.getResources(this.mapperLocationsPath));
        sessionFactoryBean.setConfiguration(configuration);

        return sessionFactoryBean;
    }

    public abstract SqlSessionFactory getSqlSessionFactory() throws Exception;
}