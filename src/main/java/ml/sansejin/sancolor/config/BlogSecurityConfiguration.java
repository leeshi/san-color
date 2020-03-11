package ml.sansejin.sancolor.config;

import ml.sansejin.sancolor.security.jwt.JwtAuthenticationTokenFilter;
import ml.sansejin.sancolor.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author sansejin
 * @className BlogSecurityConfiguration
 * @description TODO
 * @create 10/15/19 4:14 PM
 **/

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BlogSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Resource
    UserDetailsService userDetailsService;

    //api前缀
    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureAuthentication (AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 装载BCrypt密码编码器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure (HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf().disable()
                //一直保存用户认证信息
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()

                .authorizeRequests()
                // 匹配url
                // GET 请求全部允许
                // auth 的 POST 请求用于注册帐号
                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/auth/**").hasRole("USER")
                //只有ADMIN才可以控制category
                .antMatchers(HttpMethod.POST, apiPrefix + "/category/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, apiPrefix + "/category/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, apiPrefix + "/category/**").hasRole("ADMIN")
                .anyRequest().hasRole("USER");


        //禁用缓存
        httpSecurity.headers().cacheControl();

        //注入过滤器
        httpSecurity
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }
}
