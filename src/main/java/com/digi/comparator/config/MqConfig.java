package com.digi.comparator.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {

  public static final String QUEUE = "Notification Queue";
  public static final String EXCHANGE = "Notification Exchange";
  public static final String ROUTING_KEY = "Notification Routing Key";

  @Bean
  public Queue queue() {
    return new Queue(QUEUE);
  }

  @Bean
  public TopicExchange exchange(){
    return new TopicExchange(EXCHANGE);
  }

  @Bean
  public Binding bindings(Queue queue, TopicExchange topicExchange) {
    return BindingBuilder
        .bind(queue)
        .to(topicExchange)
        .with(ROUTING_KEY);
  }

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public AmqpTemplate notificationRabbitTemplate(ConnectionFactory connectionFactory) {
    final RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(jsonMessageConverter());
    return template;
  }

}
