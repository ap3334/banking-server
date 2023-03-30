package com.example.bankingserver.core.security.jwt;

import com.example.bankingserver.core.user.entity.Users;
import com.example.bankingserver.core.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    private final UserService userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = jwtProvider.resolveToken((HttpServletRequest) request);

        log.info("token={}", token);

        if (token != null && jwtProvider.validateToken(token)) {

            String username = jwtProvider.getUsername(token);

            log.info("username={}", username);

            Users user = userService.findByUsername(username);

            Authentication authentication = jwtProvider.getAuthentication(user);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);

    }

}
