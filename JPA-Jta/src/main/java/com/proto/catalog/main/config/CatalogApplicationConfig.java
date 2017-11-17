package com.proto.catalog.main.config;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication(scanBasePackages = {"com.proto", "com.sermon"})
@EnableAutoConfiguration
//@ComponentScan(basePackages = "com.proto.catalog.demo")
@EnableWebMvc
@CrossOrigin(allowCredentials = "false",
maxAge = 4800, allowedHeaders = {"X-Auth-Token", "Content-Type"},
methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.OPTIONS, RequestMethod.DELETE},
origins = "*")
@PropertySource({ "classpath:application.properties" })
public class CatalogApplicationConfig extends WebMvcConfigurerAdapter {
	
	private static final Logger logger = Logger.getLogger(CatalogApplicationConfig.class);
	
	@Value("${spring.datasource.jndi-name}")
    private String primaryJndiName;
			
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	logger.info("CORS enabled.");
    	registry.addMapping("/**")
    		.allowedOrigins("*")
    		.allowedMethods("POST", "GET",  "PUT", "OPTIONS", "DELETE")
    		.allowedHeaders("X-Auth-Token", "Content-Type")
    		.allowCredentials(false)
    		.maxAge(4800);
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() 
      throws NamingException {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.sermon.model");
        em.setPersistenceUnitName("testPU");        
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        // rest of entity manager configuration
        return em;
    }
 
    @Bean
    public DataSource dataSource() throws NamingException {
    	DataSource datasource = (DataSource) new JndiTemplate().lookup(primaryJndiName);
        return datasource;
    }
 
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    } 
    
    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return properties;
     }
}