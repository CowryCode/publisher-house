package com.cowrycode;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityAdapter{

//	@Bean
//	public UserDetailsService userDetailsService() {
//		return new ShopmeUserDetailsService();
//	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}





	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("/", "/error").permitAll()
						.anyRequest().authenticated()
						)
				.oauth2Login(withDefaults());

		return http.build();
	}



// :::::::::::::::::::::: OLD :::::::::::::

//	@Bean
//	public WebSecurityCustomizer webSecurityCustomizer() {
//		return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
//	}


}


//@Configuration
//public class SecurityAdapter extends WebSecurityConfigurerAdapter {
//
//    @Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// @formatter:off
//		http
//			.authorizeRequests(a -> a
//				.antMatchers("/", "/error").permitAll()
//				.anyRequest().authenticated()
//			)
//			.exceptionHandling(e -> e
//				.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//			)
//			.csrf(c -> c
//				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//			)
//			.logout(l -> l
//				.logoutSuccessUrl("/").permitAll()
//			)
//			.oauth2Login();
//		// @formatter:on
//    }
//
//}
