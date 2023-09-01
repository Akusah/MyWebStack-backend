package com.example.config;


import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.utils.JwtUtils;
import com.example.common.vo.Result;
import com.example.filter.JwtAuthorizeFilter;
import com.example.sys.entity.response.AuthorizeVo;
import com.example.sys.mapper.UserMapper;
import com.example.sys.service.impl.AuthorizeServiceUserImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Leo
 * @Date: 2023/08/16/下午8:38
 * @Description:
 */
@Configuration
@EnableWebSecurity
public class MySecurityConfig {

    @Resource
    JwtAuthorizeFilter jwtAuthorizeFilter;

    @Resource
    private DataSource dataSource;

    @Resource
    private AuthorizeServiceUserImpl authorizeService;

    @Resource
    private UserMapper userMapper;

    @Resource
    JwtUtils utils;


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity security) throws Exception {
        return security
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(authorizeService)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, PersistentTokenRepository repository) throws Exception{
        return http
                .authorizeHttpRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/admin/**").hasRole("admin")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/api/auth/login")
                .successHandler(this::onAuthenticationSuccess)
                .failureHandler(this::onAuthenticationFailure)
                .and()
                .logout().logoutUrl("/api/auth/logout").logoutSuccessHandler(this::onLogoutSuccess)
                .and()
                //开启security异常处理，一个是认证异常，一个是权限不足异常
                .exceptionHandling().authenticationEntryPoint(this::onAuthenticationFailure).accessDeniedHandler(this::AccessDeniedHandler)
                .and()
                .csrf().disable()
                .cors().configurationSource(this.getCorsConfiguration())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthorizeFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    public void AccessDeniedHandler(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().write(JSON.toJSONString(Result.fail("权限不足")));
    }


    public CorsConfigurationSource getCorsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addExposedHeader("*");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }



    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        if (exception.getMessage().equals("Full authentication is required to access this resource")){
            response.getWriter().write(JSON.toJSONString(Result.fail("请登录后再试")));
        }else {
            System.out.println(exception.getClass());
            response.getWriter().write(JSON.toJSONString(Result.fail(exception.getMessage())));
        }

    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        User user = (User) authentication.getPrincipal();
        com.example.sys.entity.User loginUser = userMapper.selectOne(new QueryWrapper<com.example.sys.entity.User>().eq("username", user.getUsername()).or().eq("email",user.getUsername()));
        String token = utils.createJwt(user,loginUser.getId(), loginUser.getUsername());
        AuthorizeVo vo = new AuthorizeVo();
        vo.setExpire(utils.expireTime());
        vo.setRole(loginUser.getIsAdmin()==1 ? "admin":"user");
        vo.setToken(token);
        vo.setUsername(loginUser.getUsername());

        response.getWriter().write(JSON.toJSONString(Result.success(vo,"登录成功")));

    }

    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        PrintWriter writer = response.getWriter();
        String authorization = request.getHeader("Authorization");
        if (utils.invalidateJwt(authorization)){
            writer.write(JSON.toJSONString(Result.success()));
        } else {
            writer.write(JSON.toJSONString(Result.fail(400,"退出登录失败")));
        }

    }





}
