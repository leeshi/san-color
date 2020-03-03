package ml.sansejin.sancolor.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author sansejin
 * @className JwtAuthenticationTokenFilter
 * @description JWT验证过滤器，每次接受请求都会调用该过滤器
 * @create 10/15/19 4:42 PM
 **/

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
        //如果用户正在访问认证接口，不需要进行过滤
        //TODO 用配置文件进行路径过滤配置
        if (httpServletRequest.getRequestURL().indexOf("api") < 0) {
            logger.info("Not requesting api, pass on to the next filter...");
        } else {

            logger.info("URL:" + httpServletRequest.getRequestURL() + ". Doing JwtAuthenticationTokenFilter...");
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
                    //用于覆盖的cookie
                    Cookie newCookie;
                    //检测Token是否合法
                    String username = JwtTokenUtil.getUsernameFromToken(authToken);
                    Date created = JwtTokenUtil.getCreatedDateFromToken(authToken);
                    //不合法
                    if (username == null || created == null) {
                        logger.info("Token is illegal! Resetting context...");
                        newCookie = new Cookie(tokenName, "empty");
                        newCookie.setMaxAge(0);
                        newCookie.setPath("/");
                        httpServletResponse.addCookie(newCookie);
                        SecurityContextHolder.getContext().setAuthentication(null); //重置验证，要注意principal为空的问题
                    } else {
                        //cookie合法，进行检测与更新
                        //1. 检测token是否在白名单内
                        //2. 检测token是否可以更新，如果是就自动进行刷新
                        //  1. token在刷新期内，可以更新
                        //  2. token不在刷新期内，但是没有过期，不进行更新，白名单不处理
                        //  3. token不在刷新期内，而且已经过期，覆盖cookie与白名单
                        logger.info("Checking authentication of USER:" + username);
                        if (JwtTokenUtil.isTokenInWhiteList(authToken, username)) {

                            if (JwtTokenUtil.isTokenExpired(created)) {
                                logger.info("Token has expired, resetting context...");
                                //token已经过期
                                //覆盖token
                                newCookie = new Cookie(tokenName, "empty");
                                newCookie.setMaxAge(0);
                                newCookie.setPath("/");
                                httpServletResponse.addCookie(newCookie);
                                //白名单
                                JwtTokenUtil.setTokenInWhiteList(null, username);
                                SecurityContextHolder.getContext().setAuthentication(null);
                            } else {
                                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                                if (!JwtTokenUtil.isTokenAlive(created)) {
                                    logger.info("Token can be refreshed, refreshing...");
                                    //token在刷新期内，进行刷新
                                    String refreshedToken = JwtTokenUtil.generateToken(userDetails);
                                    JwtTokenUtil.setTokenInWhiteList(refreshedToken.substring(tokenHead.length()), username);

                                    newCookie = new Cookie(tokenName, refreshedToken);
                                    newCookie.setMaxAge(60 * 60 * 24 * 3);
                                    newCookie.setPath("/");
                                    newCookie.setHttpOnly(true);
                                    httpServletResponse.addCookie(newCookie);
                                }
                                //token在存活期内，不进行特别的操作

                                //Tomcat中有session系统，SpringSecurity可以将验证的上下文存放在内存当中，这样就不需要每一次都进行验证。
                                //将SecurityContext恢复出来，如果 SecurityContext为空，就代表还没有session还没有被设置或者已经过时，需要进行重新设置
                                if (SecurityContextHolder.getContext().getAuthentication() == null) {

                                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                            userDetails, null, userDetails.getAuthorities());
                                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                                            httpServletRequest));
                                    logger.info("Authenticated user " + username + ", setting service context...");
                                    SecurityContextHolder.getContext().setAuthentication(authentication);
                                }
                            }
                        } else {
                            logger.info("Token not in whitelist, resetting context...");
                            newCookie = new Cookie(tokenName, "empty");
                            newCookie.setMaxAge(0);
                            newCookie.setPath("/");
                            httpServletResponse.addCookie(newCookie);
                            SecurityContextHolder.getContext().setAuthentication(null);
                        }
                    }
                } else {
                    logger.info("Cookies not compatible");
                }
            } else {
                logger.info("No cookie");
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
        logger.info("Filtering finished!");

    }
}
