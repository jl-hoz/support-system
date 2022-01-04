package dev.joseluis.ticket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.UUID;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    private final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Value("${root.email}")
    private String email;

    @Value("${root.password}")
    private String password;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
        email = email.isEmpty() ? "root@ticket.com" : email;
        password = password.isEmpty() ? UUID.randomUUID().toString() : password;
        auth.inMemoryAuthentication()
                .withUser(email)
                .password(getPasswordEncoder().encode(password))
                .roles("ROOT");
        logger.info("ROOT CREDENTIALS\nEmail: " + email + "\nPassword: " + password);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/activate", "/deactivate").hasAnyRole("ROOT", "ADMIN")
                .antMatchers("/service/**").hasAnyRole("ANALYST")
                .antMatchers("/profile").hasAnyRole("ADMIN", "ANALYST", "SUPPORT", "CUSTOMER")
                .and().formLogin();
        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies()
        );
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}