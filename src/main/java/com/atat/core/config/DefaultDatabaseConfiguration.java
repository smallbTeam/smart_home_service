//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atat.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.atat.core.db.dao.SqlDao;
import com.atat.core.db.dao.impl.BaseSupportDaoImpl;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@EnableTransactionManagement
@ComponentScan({"com.atat.**.controller", "com.atat.**.dao","com.atat.**.service","com.atat.**.filter","com.atat.soft.**"})
@MapperScan({"com.atat.soft.**.dao"})
public class DefaultDatabaseConfiguration implements TransactionManagementConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(DefaultDatabaseConfiguration.class);
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.initialSize}")
    private int initialSize;
    @Value("${spring.datasource.minIdle}")
    private int minIdle;
    @Value("${spring.datasource.maxIdle}")
    private int maxIdle;
    @Value("${spring.datasource.maxActive}")
    private int maxActive;
    @Value("${spring.datasource.maxWait}")
    private int maxWait;
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;
    @Value("${spring.datasource.removeAbandoned}")
    private boolean removeAbandoned;
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${spring.datasource.filters}")
    private String filters;
    @Value("${spring.datasource.dialect}")
    private String dialect;
    private PlatformTransactionManager txManager;


    @Value("${mybatis.config-location}")
    private String mybatisConfigLocation;
    @Value("${mybatis.mapper-locations}")
    private String mybatisMapperLocations;

    public DefaultDatabaseConfiguration() {
    }

    @Bean(
            initMethod = "init",
            destroyMethod = "close"
    )
    public DataSource defaultDataSource() {
        DruidDataSource defaultDataSource = new DruidDataSource();

        try {
            defaultDataSource.setUrl(this.url);
            defaultDataSource.setUsername(this.username);
            defaultDataSource.setPassword(this.password);
            defaultDataSource.setInitialSize(this.initialSize);
            defaultDataSource.setMinIdle(this.minIdle);
            defaultDataSource.setMaxIdle(this.maxIdle);
            defaultDataSource.setMaxActive(this.maxActive);
            defaultDataSource.setMaxWait((long)this.maxWait);
            defaultDataSource.setTimeBetweenEvictionRunsMillis((long)this.timeBetweenEvictionRunsMillis);
            defaultDataSource.setMinEvictableIdleTimeMillis((long)this.minEvictableIdleTimeMillis);
            defaultDataSource.setTestWhileIdle(this.testWhileIdle);
            defaultDataSource.setTestOnBorrow(this.testOnBorrow);
            defaultDataSource.setTestOnReturn(this.testOnReturn);
            defaultDataSource.setRemoveAbandoned(this.removeAbandoned);
            defaultDataSource.setMaxPoolPreparedStatementPerConnectionSize(this.maxPoolPreparedStatementPerConnectionSize);
            defaultDataSource.setFilters(this.filters);
        } catch (SQLException var3) {
            logger.error("数据库连接池初始化失败", var3);
        }

        return defaultDataSource;
    }

    @Bean
    public PlatformTransactionManager defaultTxManager() {
        return new DataSourceTransactionManager(this.defaultDataSource());
    }

    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return this.defaultTxManager();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Autowired @Qualifier("defaultDataSource") DataSource defaultDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(defaultDataSource);
        sessionFactory.setFailFast(true);
        sessionFactory.setConfigLocation(new ClassPathResource(mybatisConfigLocation));
        sessionFactory.setMapperLocations((new PathMatchingResourcePatternResolver()).getResources(mybatisMapperLocations));
        return sessionFactory.getObject();
    }

    @Bean
    public SqlSessionTemplate defaultSqlSessionTemplate(@Autowired @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public SqlDao defaultSqlDao(@Autowired @Qualifier("defaultSqlSessionTemplate") SqlSessionTemplate defaultSqlSessionTemplate) throws Exception {
        return new BaseSupportDaoImpl(defaultSqlSessionTemplate, this.dialect);
    }
}
