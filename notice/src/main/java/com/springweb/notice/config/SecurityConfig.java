package com.springweb.notice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // 스프링 시큐리티 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalDetailService principalDetailService;

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        // resources 모든 접근을 허용하는 설정을 해버리면
//        // HttpSecurity 설정한 ADIM권한을 가진 사용자만 resources 접근가능한 설정을 무시해버린다.
//        web.ignoring()
//                .antMatchers("/resources/**");
//    }

    // 비밀번호 암호화 인코더 스프링 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 로그인 인증시 시큐리티가 패스워드를 가져와서 암호화한 암호를 불러와 비교
        auth.userDetailsService(principalDetailService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // csrf 토큰 비활성화

                .authorizeRequests() // url별 권한 관리를 설정
                .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**, /jquery/**, /board/view/**").permitAll() // 해당 url 모두 접근 인증없이 허용
                .anyRequest().authenticated() // 요청에 대하여 인증요청

                .and()

                .formLogin()
                .loginPage("/auth/login") // 로그인 페이지
                .loginProcessingUrl("/auth/login") // 로그인 url
                .defaultSuccessUrl("/home", true) // '/'인 상태로 인덱스html을 호출하면 계속해서 jquery를 호출하여 강제로 입력하였음.

                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
