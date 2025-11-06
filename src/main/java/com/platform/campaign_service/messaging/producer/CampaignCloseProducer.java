package com.platform.campaign_service.messaging.producer;

import com.platform.campaign_service.configs.rabbit.exchanges.ExchangeAbstractConfig;
import com.platform.campaign_service.dtos.campaign.CampaignClosedEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.stereotype.Component;
/**
 * Producer class for publishing campaign closed events to RabbitMQ.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CampaignCloseProducer {
    /** RabbitTemplate for sending messages to RabbitMQ. */
    private final RabbitTemplate rabbitTemplate;
    /** Configuration for the campaign close exchange. */
    private final ExchangeAbstractConfig campaignCloseExchangeConfig;

    /**
     * Publishes a campaign closed event to the RabbitMQ exchange.
     *
     * @param campaignClosedEventDto the campaign closed event DTO
     *                               containing campaign details.
     */
    public void publishCampaignCloseEvent(CampaignClosedEventDto campaignClosedEventDto) {
        try {
            log.debug("Entering publishCampaignCloseEvent with campaign closed: {}", campaignClosedEventDto);
            rabbitTemplate.convertAndSend(
                    campaignCloseExchangeConfig.getExchangeName(), "", campaignClosedEventDto);
            log.debug("New campaign closed event published successfully");
        } catch (AmqpConnectException e) {
            log.error("❌ Failed to connect to RabbitMQ. The broker might be down.", e);
        } catch (MessageConversionException e) {
            log.error("❌ Error serializing campaign message: {}", campaignClosedEventDto, e);
        } catch (AmqpException e) {
            log.error("❌ General error publishing message to RabbitMQ.", e);
        }
    }
}
