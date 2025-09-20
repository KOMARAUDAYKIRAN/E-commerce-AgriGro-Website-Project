package com.agriBazaar.backend.config;

import com.agriBazaar.backend.entities.User;
import com.agriBazaar.backend.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthUtil authUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");

        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        String token=authHeader.substring(7);
        String username=null;

        try{
            username=authUtil.getUsernameFromToken(token);
        }catch (Exception e){
            filterChain.doFilter(request,response);
            return;
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            User user=userRepository.findByUsername(username).orElseThrow(null);

            if(user!=null){
                UsernamePasswordAuthenticationToken passwordToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(passwordToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
