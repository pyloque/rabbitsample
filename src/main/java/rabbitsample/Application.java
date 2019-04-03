package rabbitsample;

import java.util.Collections;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ConnectionFactory factory() {
		var cachingFactory = new CachingConnectionFactory("localhost", 5672);
		cachingFactory.setUsername("guest");
		cachingFactory.setPassword("guest");
		// cachingFactory.setPublisherConfirms(true);
		return cachingFactory;
	}

	@Bean
	public RabbitTemplate template() {
		var template = new RabbitTemplate(factory());
		return template;
	}

	@Bean
	public RabbitAdmin admin() {
		var admin = new RabbitAdmin(template());
		admin.declareExchange(new TopicExchange("ex_notify_transfer"));
		admin.declareExchange(new TopicExchange("ex_notify"));
		var transferQueue = QueueBuilder.durable("q_notify_transfer").withArgument("x-message-ttl", 5000)
				.withArgument("x-dead-letter-exchange", "ex_notify")
				.withArgument("x-dead-letter-routing-key", "r_notify").build();
		admin.declareQueue(transferQueue);
		admin.declareQueue(QueueBuilder.durable("q_notify").build());
		admin.declareBinding(new Binding("q_notify_transfer", DestinationType.QUEUE, "ex_notify_transfer",
				"r_notify_transfer", Collections.emptyMap()));
		admin.declareBinding(
				new Binding("q_notify", DestinationType.QUEUE, "ex_notify", "r_notify", Collections.emptyMap()));
		return admin;
	}

	@Bean(name = "notifyListenerContainerFactory")
	public SimpleRabbitListenerContainerFactory listenerContainerFactory() {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(factory());
		factory.setAcknowledgeMode(AcknowledgeMode.NONE);
		factory.setConcurrentConsumers(4);
		factory.setPrefetchCount(4);
		return factory;
	}

}
