package com.kairo.lojaWeb;

import com.kairo.lojaWeb.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityAdministrativo extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    //@Autowired
    //private PasswordEncoder encoder;
    // encoder do PasswordEncoder do Spring

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(
                "select email as username, senha as password, 1 as enable from funcionario where email=?"
        ).authoritiesByUsernameQuery(
                "select funcionario.email as username, papel.nome as authority from permissao inner join funcionario on funcionario.id=permissao.funcionario_id inner join papel on permissao.papel_id=papel.id where funcionario.email=?"
        ).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/administrativo/entrada/cadastro", "/password-recovery", "/administrativo/produtos/cargo-insert").permitAll()
                .antMatchers("/administrativo/**").hasAuthority(Constants.CARGO_GERENTE).and()
                .formLogin()
                .loginPage("/login").defaultSuccessUrl("/administrativo").permitAll().and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/administrativo").and()
                .exceptionHandling().accessDeniedPage("/negado");
    }


}
