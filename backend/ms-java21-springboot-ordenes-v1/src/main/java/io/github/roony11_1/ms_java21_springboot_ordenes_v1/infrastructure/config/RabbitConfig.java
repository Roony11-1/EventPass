package io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig
{
    public static final String EXCHANGE = "eventpass";
    public static final String RK_ORDEN_CREADA = "orden.creada";
    public static final String RK_TICKETS_EMITIDOS = "tickets.emitidos";
    public static final String Q_ORDEN_CREADA = "q.tickets.ordenes-creadas";
    public static final String Q_TICKETS_EMITIDOS = "q.ordenes.tickets-emitidos";

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory)
    {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    TopicExchange eventpassExchange()
    {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    Queue ordenCreadaQueue()
    {
        return new Queue(Q_ORDEN_CREADA, true);
    }

    @Bean
    Queue ticketsEmitidosQueue()
    {
        return new Queue(Q_TICKETS_EMITIDOS, true);
    }

    @Bean
    Binding ordenCreadaBinding(TopicExchange eventpassExchange, Queue ordenCreadaQueue)
    {
        return BindingBuilder.bind(ordenCreadaQueue).to(eventpassExchange).with(RK_ORDEN_CREADA);
    }

    @Bean
    Binding ticketsEmitidosBinding(TopicExchange eventpassExchange, Queue ticketsEmitidosQueue)
    {
        return BindingBuilder.bind(ticketsEmitidosQueue).to(eventpassExchange).with(RK_TICKETS_EMITIDOS);
    }
}
