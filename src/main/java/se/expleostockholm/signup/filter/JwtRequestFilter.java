package se.expleostockholm.signup.filter;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import se.expleostockholm.signup.constant.JwtFilterConstant;
import se.expleostockholm.signup.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

/**
 * Servlet filter class checking if JWT token is valid.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException, ExpiredJwtException {

        final String authorizationHeader = request.getHeader(JwtFilterConstant.HEADER_STRING);

        if (authorizationHeader == null) {
            chain.doFilter(request, response);
            return;
        }

        if (authorizationHeader.startsWith(JwtFilterConstant.TOKEN_PREFIX)) {
            final String jwt = authorizationHeader.substring(7);
            final String username = jwtUtil.extractUsername(jwt);
            setUpUsernameAndPasswordAuth(request, jwt, username);
        }
        chain.doFilter(request, response);
    }

    private void setUpUsernameAndPasswordAuth(HttpServletRequest request, String jwt, String username) {
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = new User(username, "", true, true, true, true, new HashSet<>());

            if (jwtUtil.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
    }

}
