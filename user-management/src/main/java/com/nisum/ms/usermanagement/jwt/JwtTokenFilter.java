package com.nisum.ms.usermanagement.jwt;

import io.jsonwebtoken.ExpiredJwtException;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nisum.ms.usermanagement.service.UserDetailsServiceImpl;

@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    
    @Autowired
    private UserDetailsServiceImpl jwtUserDetailsService;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        log.info("JwtTokenFilter.doFilterInternal");
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenProvider.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                log.info("No se puede obtener el token JWT");
            } catch (ExpiredJwtException e) {
                log.info("El token JWT ha caducado");
            }
        } else {
            log.warn("El token JWT no comienza con Bearer String");
        }
        // Una vez que obtengamos el token validarlo.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
            // si el token es válido, configure Spring Security para configurar manualmente
            // la autenticación
            if (jwtTokenProvider.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails ,null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Después de configurar la Autenticación en el contexto, especificamos
                // que el usuario actual está autenticado. Por lo tanto, pasa las
                // configuraciones de Spring Security con éxito.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
         }
        chain.doFilter(request, response);
        }
    
}