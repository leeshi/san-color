package ml.sansejin.sancolor.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sansejin
 * @className JwtAuthenticationTokenFilter
 * @description JWT验证过滤器，每次接受请求都会调用该过滤器
 * @create 10/15/19 4:42 PM
 **/

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    UserDetailsService userDetailsService;

    @Value("${token.name}")
    private String tokenName;

    //加密后的头部
    @Value("${token.head}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        Cookie cookie = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        //如果cookie是空，下面都不用验证了
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(tokenName)) {
                    cookie = c;
                    break;
                }
            }
            final String authHeader = cookie != null ? cookie.getValue() : null;

            if (authHeader != null && authHeader.startsWith(tokenHead)) {
                final String authToken = authHeader.substring(tokenHead.length()); // The part after "Bearer "

                String username = JwtTokenUtil.getUsernameFromToken(authToken);

                logger.info("Checking authentication of USER:" + username);

                //将SecurityContext恢复出来，如果 SecurityContext == null 就设置认证信息
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                    if (JwtTokenUtil.validateToken(authToken, userDetails)) {

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                                httpServletRequest));
                        logger.info("Authenticated user " + username + ", setting service context");
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
