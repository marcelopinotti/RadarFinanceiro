package com.marcelopinotti.radar_financeiro.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean // bean é uma anotação que indica que esse método retorna um objeto que deve ser gerenciado pelo Spring
    public ModelMapper mapper() {
        return new ModelMapper();
    }
}
