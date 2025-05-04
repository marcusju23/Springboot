package se.iths.springbootgroupproject.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.client.RestClient;
import se.iths.springbootgroupproject.services.github.GithubOAuth2UserService;

@Configuration
@EnableRetry
public class SecurityConfig {

    private final GithubOAuth2UserService githubOAuth2UserService;

    public SecurityConfig(GithubOAuth2UserService githubOAuth2UserService) {
        this.githubOAuth2UserService = githubOAuth2UserService;
    }

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(
                                    "/web/welcome",
                                    "/login",
                                    "/oauth/**",
                                    "/logout",
                                    "/error**",
                                    "/static/**",
                                    "/api/**").permitAll()
                        .requestMatchers(
                                    "/web/myprofile",
                                    "/web/myprofile/editmessage*",
                                    "/web/myprofile/create",
                                    "/web/myprofile/edit",
                                    "/web/myprofile/deletemessage*",
                                    "/web/messages",
                                    "/web/messages/translate*",
                                    "/web/user*").authenticated()
                        .anyRequest().denyAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/web/myprofile", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfoEndpoint ->
                                userInfoEndpoint.userService(githubOAuth2UserService))
                        .successHandler(oauth2LoginSuccessHandler()))
                .logout(logout -> logout.logoutSuccessUrl("/web/welcome"));
        return http.build();
    }

    @Bean
    static RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER\n" +
                               "ROLE_USER > ROLE_GUEST");
        return hierarchy;
    }

    @Bean
    public AuthenticationSuccessHandler oauth2LoginSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/web/myprofile");
    }

    @Bean
    RestClient restClient() {
        return RestClient.create();
    }

}
