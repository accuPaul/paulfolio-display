package com.paulmount.paulfoliodisplay.config;

import com.paulmount.paulfoliodisplay.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;


/**
 * *  Created by paulm on 2/6/2024
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    @Order(0)
    SecurityFilterChain resources(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/images/**", "/**.css", "/**.js")
                .authorizeHttpRequests(c -> c.anyRequest().permitAll())
                .securityContext(c -> c.disable())
                .sessionManagement(c -> c.disable())
                .requestCache(c -> c.disable())
                .build();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        return http
                .authorizeHttpRequests( auth -> {
                    auth.requestMatchers( antMatcher(HttpMethod.GET, "/")).permitAll();
                    auth.requestMatchers(mvc.pattern("/login/**")).permitAll();
                    auth.requestMatchers(mvc.pattern("/error")).permitAll();
                    auth.requestMatchers(antMatcher("/logout")).permitAll();
                    auth.anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .oauth2Login(oc-> oc
                        .userInfoEndpoint(ui -> ui.userService(oAuth2LoginHandler()))
                        .loginPage("/login")
                )
                .logout(lo -> lo.logoutSuccessUrl("/"))
                .build();
    }

    private org.springframework.security.oauth2.client.userinfo.OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2LoginHandler() {
        return userRequest -> {
            DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
            OAuth2User oAuth2User = delegate.loadUser(userRequest);
            return AppUser.builder()
                    .userName(oAuth2User.getAttribute("login"))
                    .attributes(oAuth2User.getAttributes())
                    .userId(oAuth2User.getName())
                    .accessToken(userRequest.getAccessToken().getTokenValue())
                    .authorities(oAuth2User.getAuthorities())
                    .isAdmin(oAuth2User.getAttribute("login").equals("accuPaul"))
                    .build();
        };
    }
}
