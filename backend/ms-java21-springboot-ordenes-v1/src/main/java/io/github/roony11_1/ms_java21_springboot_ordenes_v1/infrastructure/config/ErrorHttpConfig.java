package io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.roony11_1.error.rest.DefaultHttpStatusMapper;
import io.github.roony11_1.error.rest.IHttpStatusMapper;

@Configuration 
public class ErrorHttpConfig 
{
    @Bean
    IHttpStatusMapper httpStatusMapper()
    {
        return new DefaultHttpStatusMapper(
            Map.of(
                OrdenErrorCategory.INVALID, 400,
                OrdenErrorCategory.STATE_CONFLICT, 422
            )
        );
    }
}
