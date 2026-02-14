package com.marcelopinotti.radar_financeiro.security;

import com.marcelopinotti.radar_financeiro.domain.model.Usuario;
import com.marcelopinotti.radar_financeiro.domain.service.UsuarioService;
import com.marcelopinotti.radar_financeiro.dto.usuario.UsuarioResponseDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private UsuarioService usuarioService;

    private ModelMapper mapper;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(header.substring(7));
            if (authentication != null && authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request,response);
    }
        private UsernamePasswordAuthenticationToken getAuthentication(String token){
            if(jwtUtil.isValidToken(token)){
                String username = jwtUtil.getUserName(token);
                UsuarioResponseDTO usuarioDto = usuarioService.buscarPorEmail(username);
                Usuario usuario = mapper.map(usuarioDto, Usuario.class);
                return new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            }
            return null;
        }
}
