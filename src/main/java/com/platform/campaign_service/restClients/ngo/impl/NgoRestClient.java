package com.platform.campaign_service.restClients.ngo.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.campaign_service.controllers.manageExceptions.CustomException;
import com.platform.campaign_service.dtos.common.ErrorApi;
import com.platform.campaign_service.dtos.ngo.NgoDto;
import com.platform.campaign_service.restClients.ngo.INgoRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * REST client implementation for interacting with the NGO service.
 */
@Service
public class NgoRestClient implements INgoRestClient {
    /** Logger instance for logging information and errors. */
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(NgoRestClient.class);
    /** RestTemplate instance for making HTTP requests. */
    private final RestTemplate restTemplate;
    /** ObjectMapper instance for JSON processing. */
    private final ObjectMapper objectMapper;
    /** Base URL for the auth service. */
    private final String baseUrl;
    /**
     * Constructs an NgoRestClient with the specified RestTemplate and base URL.
     *
     * @param restTemplateParam the RestTemplate instance for making HTTP requests
     * @param baseUrlParam      the base URL for the auth service,
     *                          injected from application properties
     * @param objectMapperParam the ObjectMapper instance for JSON processing
     */
    public NgoRestClient(RestTemplate restTemplateParam,
                         @Value("${pool.user.url}") String baseUrlParam,
                         ObjectMapper objectMapperParam) {
        this.objectMapper = objectMapperParam;
        this.restTemplate = restTemplateParam;
        this.baseUrl = baseUrlParam;
    }
    /**
     * Retrieves the NGO information associated with the current user.
     *
     * @return a ResponseEntity containing the NgoDto if found,
     * or an appropriate error response if no NGO is associated with the user
     */
    @Override
    public ResponseEntity<NgoDto> getMyNgo() {
        LOGGER.trace("getMyNgo RestClient - start");
        String url = baseUrl + "/api/v1/ngos/my-ngo";
       try {
           ResponseEntity<NgoDto> response = restTemplate.getForEntity(url, NgoDto.class);
           LOGGER.trace("getMyNgo RestClient - end");
           return response;
       } catch (HttpClientErrorException | HttpServerErrorException ex) {
              LOGGER.error("Error in getMyNgo NgoRestClient: {}", ex.getMessage());
              handleError(ex);
              return null; // This line will never be reached because handleError always throws an exception
       }
    }

    /**
     * Retrieves all approved NGOs.
     *
     * @return a ResponseEntity containing an array of
     * NgoDto representing all approved NGOs
     */
    @Override
    public ResponseEntity<NgoDto[]> getAllNgosApproved() {
        LOGGER.trace("getAllNgosApproved RestClient - start");
        String url = baseUrl + "/api/v1/ngos/all-approved";
        try {
            ResponseEntity<NgoDto[]> response = restTemplate.getForEntity(url, NgoDto[].class);
            LOGGER.trace("getAllNgosApproved RestClient - end");
            return response;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            LOGGER.error("Error in getAllNgosApproved NgoRestClient: {}", ex.getMessage());
            handleError(ex);
            return null; // This line will never be reached because handleError always throws an exception
        }
    }

    /**
     * Retrieves all NGOs.
     *
     * @return a ResponseEntity containing an array of
     * NgoDto representing all NGOs
     */
    @Override
    public ResponseEntity<NgoDto[]> getAllNgos() {
        LOGGER.trace("getAllNgos RestClient - start");
        String url = baseUrl + "/api/v1/ngos/all";
        try {
            ResponseEntity<NgoDto[]> response = restTemplate.getForEntity(url, NgoDto[].class);
            LOGGER.trace("getAllNgos RestClient - end");
            return response;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            LOGGER.error("Error in getAllNgos NgoRestClient: {}", ex.getMessage());
            handleError(ex);
            return null; // This line will never be reached because handleError always throws an exception
        }
    }

    /**
     * Retrieves the details of a specific NGO by its ID.
     *
     * @param ngoId the unique identifier of the NGO
     * @return a ResponseEntity containing the NgoDto representing the NGO details
     */
    @Override
    public ResponseEntity<NgoDto> getNgoById(UUID ngoId) {
        String getUrl = baseUrl + "/api/v1/ngos/{ngoId}";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<NgoDto> requestEntity = new HttpEntity<>(null, headers);
            LOGGER.trace("Sending GET request to URL: {}", getUrl);
            return restTemplate.exchange(
                    getUrl,
                    HttpMethod.GET,
                    requestEntity,
                    NgoDto.class,
                    ngoId
            );
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            LOGGER.error("HTTP error during get data ngo: {}", ex.getMessage());
            handleError(ex);
            return null;
        }
    }

    private void handleError(HttpStatusCodeException ex) {
        try {
            ErrorApi error = objectMapper.readValue(ex.getResponseBodyAsString(), ErrorApi.class);
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND || ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new CustomException(error.getMessage(), HttpStatus.valueOf(ex.getStatusCode().value()));
            }
            throw new CustomException("Unexpected error from user-service ", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException parseEx) {
            throw new CustomException("Unexpected error from user-service: " + ex.getMessage(),
                    HttpStatus.valueOf(ex.getStatusCode().value()), parseEx);
        }
    }
}
