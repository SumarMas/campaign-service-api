package com.platform.campaign_service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The main application class for the Campaign Service.
 */
@SpringBootApplication
@EnableScheduling
@EnableRabbit
public class CampaignServiceApplication {
    /**
     * The main entry point of the Campaign Service application.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(CampaignServiceApplication.class, args);
    }
}
