package fr.uga.l3miage.integrator.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/*@Configuration
@Profile("!test")
public class FilterConfig {

    @Bean
    FilterRegistrationBean<FireBaseTokenFilter> firebaseTokenFilter(){
        FilterRegistrationBean<FireBaseTokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new FireBaseTokenFilter());
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }
}*/
