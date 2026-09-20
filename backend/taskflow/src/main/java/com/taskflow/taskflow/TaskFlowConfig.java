package com.taskflow.taskflow;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskFlowConfig {
    @Bean 
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
