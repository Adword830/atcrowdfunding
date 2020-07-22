package com.houpu.crowd.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * SpringSecurity配置类
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyWebSpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {

        builder.userDetailsService(userDetailsService).passwordEncoder(getBCryptPasswordEncoder());
    }
    //  对请求设置权限
    @Override
    protected void configure(HttpSecurity security) throws Exception {
         security.csrf().disable();// 禁用SpringSecurity _csrf功能

        security.authorizeRequests()
                .antMatchers("/index.jsp","/static/**","/admin/do/login/page.html")
                .permitAll()
                .antMatchers("/admin/get/page.html")
                .access("hasRole('经理') or hasAuthority('user:get')")
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/admin/do/login/page.html")
                .loginProcessingUrl("/security/do/login.html")
                .defaultSuccessUrl("/admin/to/main/page.html")
                .usernameParameter("loginAcct")
                .passwordParameter("loginPwd")
                .and()
                .logout()
                .logoutUrl("/security/do/loginOut.html")
                .logoutSuccessUrl("/admin/do/login/page.html");
    }
}
