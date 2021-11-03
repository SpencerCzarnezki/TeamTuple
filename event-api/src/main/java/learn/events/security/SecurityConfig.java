package learn.events.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/authenticate", "/expire_token", "/user/create").permitAll()
                .antMatchers(HttpMethod.POST, "/refresh_token").authenticated()
                .antMatchers(HttpMethod.GET, "/meetup/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/user/password").authenticated()
                .antMatchers(HttpMethod.POST, "/meetup").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/meetup/*").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/meetup/*").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/user/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/user/update").hasAuthority("ADMIN")
                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(), converter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {


        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**")
                        .allowedMethods("DELETE", "GET", "POST", "PUT")
                        .allowedOrigins("http://localhost:3000")
                        .allowCredentials(true);
            }
        };
    }

}
