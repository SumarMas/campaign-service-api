package com.platform.campaign_service.configs;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * RabbitMQ configuration for handling donation events.
 * Uses the same ObjectMapper configured in MappersConfig.
 */
@Configuration
public class RabbitMQConfig {
    /**
     * Name of the donation exchange.
     */
    private final String donationExchangeName;

    /**
     * Name of the donation queue.
     */
    private final String donationQueueName;

    /**
     * Constructs a RabbitMQConfig with
     * the specified donation exchange and queue names.
     *
     * @param donationExchangeNameParam the name of the donation exchange,
     *                                  injected from application properties
     * @param donationQueueNameParam    the name of the donation queue,
     *                                  injected from application properties
     */
    public RabbitMQConfig(@Value("${queues.donation.exchange}") String donationExchangeNameParam,
                          @Value("${queues.donation.queue}") String donationQueueNameParam) {
        this.donationExchangeName = donationExchangeNameParam;
        this.donationQueueName = donationQueueNameParam;
    }

    /**
     * Getter for donationExchange.
     *
     * @return the name of the donation exchange
     */
    public String getDonationExchangeName() {
        return donationExchangeName;
    }

    /**
     * Getter for donationQueue.
     *
     * @return the name of the donation queue
     */
    public String getDonationQueueName() {
        return donationQueueName;
    }

    /**
     * Message converter using the shared ObjectMapper (supports LocalDateTime, etc.).
     * @param objectMapper the shared ObjectMapper bean
     * @return the MessageConverter bean
     */
    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    /**
     * Defines a FanoutExchange for donation events.
     *
     * @return the FanoutExchange bean
     */
    @Bean
    public FanoutExchange donationExchange() {
        return new FanoutExchange(donationExchangeName, true, false);
    }

    /**
     * Defines the durable queue bean.
     * A durable queue is one that persists
     * across broker restarts, ensuring message durability.
     *
     * @return A Queue object representing the durable queue.
     */
    @Bean
    public Queue notificationDonationQueue() {
        return new Queue(donationQueueName, true); // true indicates the queue is durable
    }

    /**
     * Binds the durable queue to the Fanout Exchange.
     * This ensures that any message sent
     * to the Fanout Exchange is broadcast to the bound queue.
     *
     * @param donationExchange The Donation Fanout Exchange bean.
     * @param notificationDonationQueue The durable Donation Queue bean.
     * @return A Binding object representing the
     * connection between the exchange and the queue.
     */
    @Bean
    public Binding bindingDonationQueue(FanoutExchange donationExchange, Queue notificationDonationQueue) {
        return BindingBuilder.bind(notificationDonationQueue).to(donationExchange);
    }
}
