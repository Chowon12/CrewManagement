package com.company.common.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

//web.xml에서 root-context부분을 구현하는 클래스

// 프로젝트 전체에 설정을 담당하는 클래스임을 선언.
@Configuration
// 31, 32번째 줄 scan
@ComponentScan(basePackages = {"com.company.*"})
@MapperScan(basePackages = {"com.company.mapper"})
@PropertySource("classpath:/application.properties")
public class RootConfig {
	//@Value("${properties의 키값}")
	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;
	
	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;
	
	//설정파일 적용에 사용되는 bean
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigPlaceon() {
		return new PropertySourcesPlaceholderConfigurer();
		//@PropertySource 어노테이션에 적힌 파일을 가져옴. 
		//bean으로 등록
	}
	
	// xml 설정 내 bean부분
	@Bean
	public DataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(driverClassName);
		hikariConfig.setJdbcUrl(url);
		hikariConfig.setUsername(username);
		hikariConfig.setPassword(password);
		
		HikariDataSource dataSource = new HikariDataSource(hikariConfig);
	return dataSource;
	// 10~20줄 히카리부분
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
									.getResources("classpath:/com/company/mapper/*.xml"));
		return sqlSessionFactoryBean.getObject();
		// 22~25줄 팩토리부분
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory());
	}
	
}
