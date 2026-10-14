package io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.config;

import org.springframework.context.annotation.Configuration;

import io.github.roony11_1.error.rest.HttpStatusRegistry;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception.OrdenErrorCategory;
import jakarta.annotation.PostConstruct;

@Configuration 
public class OrdenErrorConfig 
{
    @PostConstruct 
    public void registrar()
    {
        HttpStatusRegistry.register(OrdenErrorCategory.NOT_FOUND, 404);
        HttpStatusRegistry.register(OrdenErrorCategory.INVALID, 400);
        HttpStatusRegistry.register(OrdenErrorCategory.STATE_CONFLICT, 422);
    }
}
