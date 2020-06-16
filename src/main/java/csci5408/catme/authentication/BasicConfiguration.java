package csci5408.catme.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author Aman Vishnani (aman.vishnani@dal.ca)
 */
@Configuration
@EnableWebSecurity
public class BasicConfiguration extends WebSecurityConfigurerAdapter {

    private final CatmeAuthenticationProvider authProvider;

    final
    ISessionStore sessionStore;

    public BasicConfiguration(CatmeAuthenticationProvider authProvider, ISessionStore sessionStore) {
        this.authProvider = authProvider;
        this.sessionStore = sessionStore;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterAfter(new SessionFilter(sessionStore), BasicAuthenticationFilter.class);

        http
                .csrf()
                .disable()
                .httpBasic(AbstractHttpConfigurer::disable);
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll();
    }
}
