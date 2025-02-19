package com.scm.contactmanger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.contactmanger.services.impl.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {
    // Create in Memory-- user--manually

    // @Bean
    // public UserDetailsService userDetailsService(){
    //     UserDetails user1 = User.withDefaultPasswordEncoder()
    //     .username("admin123")
    //     .password("abcd")
    //     .roles("ADMIN","USER")
    //     .build();
    //     InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1);
    //     return inMemoryUserDetailsManager;
    // }

    @Autowired
    private SecurityCustomUserDetailService userDetailService;
    @Autowired
    private oAuthAuthenticationSuccessHandler oAuthAuthenticationSuccessHandler_obj;
    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(authorize ->{
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });
        // Customizer.withDefaults() -> for using inbuilt form login page of spring boot
        // httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.formLogin(formlogin ->{
            formlogin.loginPage("/getin");
            formlogin.loginProcessingUrl("/authenticate");
            formlogin.defaultSuccessUrl("/user/profile");
            // formlogin.failureForwardUrl("/getin?error=true");
            formlogin.usernameParameter("email");
            formlogin.passwordParameter("password");
            
        });
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/logout");
            logoutForm.logoutSuccessUrl("/getin?logout=true");
        });
        httpSecurity.oauth2Login(oauth ->{
            oauth.loginPage("/getin");
            oauth.successHandler(oAuthAuthenticationSuccessHandler_obj);
        });
        return httpSecurity.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
