package com.marcelopinotti.radar_financeiro.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.marcelopinotti.radar_financeiro.domain.model.Usuario;
import com.marcelopinotti.radar_financeiro.dto.usuario.LoginRequestDTO;
import com.marcelopinotti.radar_financeiro.dto.usuario.LoginResponseDTO;
import com.marcelopinotti.radar_financeiro.dto.usuario.UsuarioResponseDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;


    private final JwtUtil jwtUtil;
    private ModelMapper mapper;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super();
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            LoginRequestDTO login = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDTO.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getSenha());
            return authenticationManager.authenticate(authToken);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credenciais inválidas");
        } catch (Exception ex){
            throw new InternalAuthenticationServiceException(ex.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        Usuario usuario = (Usuario) authResult.getPrincipal();
        String token = jwtUtil.gerarToken(authResult);
        UsuarioResponseDTO usuarioResponse = mapper.map(usuario, UsuarioResponseDTO.class);

        LoginResponseDTO loginResponse = new LoginResponseDTO();
        loginResponse.setToken("Bearer " + token);
        loginResponse.setUsuario(usuarioResponse);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(loginResponse));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson("Falha na autenticação: " + failed.getMessage()));
    }

}
