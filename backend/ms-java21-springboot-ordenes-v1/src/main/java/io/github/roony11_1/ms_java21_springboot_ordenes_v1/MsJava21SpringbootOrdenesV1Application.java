package io.github.roony11_1.ms_java21_springboot_ordenes_v1;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableRabbit
public class MsJava21SpringbootOrdenesV1Application {

	public static void main(String[] args) {
		SpringApplication.run(MsJava21SpringbootOrdenesV1Application.class, args);
	}

}
