package za.ac.ss.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Order
@Configuration
public class AuthorizationServer extends ResourceServerConfigurerAdapter implements AuthorizationServerConfigurer {

	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired private DataSource dataSource;
	@Autowired private AuthenticationManager authenticationManager;

	@Bean
	public TokenStore JdbcTokenStore() {
		 return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(JdbcTokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("slabbert");
		return converter;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("isAuthenticated()").checkTokenAccess("permitAll()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		// log.info("web:webpass");
//		clients.inMemory().withClient("slabbert").secret(passwordEncoder.encode("spass")).scopes("READ", "WRITE")
//				.authorizedGrantTypes("password", "authorization_code", "refresh_token").redirectUris("http://localhost:8282");
//		// .res;
		 clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// log.info("tokenStore");
		endpoints.tokenStore(JdbcTokenStore());
		endpoints.accessTokenConverter(accessTokenConverter());
		endpoints.authenticationManager(authenticationManager);
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("slabbert").tokenStore(JdbcTokenStore());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.formLogin().and()
        .authorizeRequests()
				//.antMatchers("/*", "/document/download/**").permitAll()
				.antMatchers("/#/reset-password/**", "/users/forgot-password*", "/email-notification*", "/raf/daysleft/**", "/users/logout/**", "/mobi/**").permitAll()
				.antMatchers("/actuator/**").permitAll()
				.antMatchers("/health").permitAll()
				//.antMatchers("/users/**").permitAll()
//				.antMatchers("/users/**").hasAnyRole("ROLE_Admin", "ROLE_Employee", "ROLE_Customer")
//				.antMatchers("/appointment/list").hasAnyRole("ROLE_Admin", "ROLE_Employee")
//				.antMatchers("/appointment/**").hasAnyRole("ROLE_Admin", "ROLE_Employee", "ROLE_Customer")
				.anyRequest().authenticated();
	}

}
