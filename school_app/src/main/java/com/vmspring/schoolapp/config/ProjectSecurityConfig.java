package com.vmspring.schoolapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {
public class ProjectSecurityConfig {

   //@Override
   //protected void configure(HttpSecurity http) throws Exception {
// //      super.configure(http);

   //    http.authorizeRequests().anyRequest().permitAll().and().formLogin().and().httpBasic();
   //    //http.authorizeRequests().anyRequest().denyAll().and().formLogin().and().httpBasic();


   //}

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

//        // Permit All Requests inside the Web Application
//        http.authorizeRequests().anyRequest().permitAll().
//                and().formLogin()
//                .and().httpBasic();
//
//        // Deny All Requests inside the Web Application
//            /*http.authorizeRequests().anyRequest().denyAll().
//                    and().formLogin()
//                    .and().httpBasic();*/
//
//        return http.build();


//        http.authorizeRequests()

        //csrf enabled for all actions except for saveMessage as it is supposed
        //to be invoked from public page. So, doesn't require CSRF. Thus, anyone can submit any
        //kind of query (via POST method)
        //rest all pages have enabled CSRF


        //http.csrf().disable()
        http.csrf().ignoringAntMatchers("/saveMessage")
                .ignoringAntMatchers("/public/**")
                .ignoringAntMatchers("/data-api/**")
                .ignoringAntMatchers("/api/**")
                .ignoringAntMatchers("/school/actuator/**").and()
                //.ignoringAntMatchers("/h2-console/**").and()
                .authorizeRequests()
                .mvcMatchers("/dashboard").authenticated()
                .mvcMatchers("/displayProfile").authenticated()
                .mvcMatchers("/updateProfile").authenticated()
                .mvcMatchers("/displayMessages").hasRole("ADMIN")
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .mvcMatchers("/api/**").authenticated()
                .mvcMatchers("/data-api/**").authenticated()
                .mvcMatchers("/school/actuator/**").hasRole("ADMIN")
                .mvcMatchers("/student/**").hasRole("STUDENT")
                .mvcMatchers("/home").permitAll()
                .mvcMatchers("/holidays/**").permitAll()
                .mvcMatchers("/contact").permitAll()
                .mvcMatchers("/saveMessage").permitAll()
                .mvcMatchers("/courses").authenticated()
                .mvcMatchers("/about").permitAll()
                .mvcMatchers("/login").permitAll()
                .mvcMatchers("/public/**").permitAll()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/dashboard").failureUrl("/login?error=true").permitAll()
                .and().logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll()
                //.and().authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and().httpBasic();

        //http.headers().frameOptions().disable();

        return http.build();

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser("user").password("12345").roles("USER")
                    .and()
                    .withUser("admin").password("54321").roles("USER", "ADMIN")
                    .and().passwordEncoder(NoOpPasswordEncoder.getInstance());
        }*/


//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("12345")
//                .roles("USER")
//                .build();
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("54321")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }




}
