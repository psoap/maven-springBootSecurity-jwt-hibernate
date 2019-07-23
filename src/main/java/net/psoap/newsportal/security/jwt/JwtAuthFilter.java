package net.psoap.newsportal.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.psoap.newsportal.dao.UserDao;
import net.psoap.newsportal.model.entity.User;
import net.psoap.newsportal.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtTokenProvider tokenProvider;
    private final UserDao userDetailsService;

    @Value("${jwt.token.prefix:Bearer}")
    private String tokenPrefix;
    @Value("${jwt.http.header:Authorization}")
    private String header;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = true)
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String tokenHeader = req.getHeader(header);
        if (tokenHeader != null && tokenHeader.startsWith(tokenPrefix)) {
            setAuthenticationToContext(tokenHeader.replace(tokenPrefix, ""));
        }
        chain.doFilter(req, res);
    }

    private void setAuthenticationToContext(final String authToken){
        try {
            Long id = tokenProvider.getIdFromToken(authToken);
            Optional<User> userDb  = userDetailsService.findOneById(id);
            userDb.ifPresent(user -> {
                UserDetails userDetails = new UserDetailsImpl(user);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            });
        } catch (ExpiredJwtException | SignatureException e) {
            log.debug(e.getMessage());
        }
    }
}
