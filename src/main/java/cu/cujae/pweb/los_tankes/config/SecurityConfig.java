package cu.cujae.pweb.los_tankes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import cu.cujae.pweb.los_tankes.security.UserDetailsServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl();
  };
  
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  };
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
  }	
	
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    
    http.authorizeRequests().antMatchers("/javax.faces.resource/**", "/api/**")
        .permitAll().anyRequest().authenticated();
    
    
    http.formLogin().
    loginPage("/login").permitAll()
    .failureUrl("/login")
    .defaultSuccessUrl("/welcome")
    .and().exceptionHandling().accessDeniedPage("/access-denied");
    
    
    
    http.logout().logoutSuccessUrl("/login");
   
    http.csrf().disable().httpBasic();
  }

  
}
