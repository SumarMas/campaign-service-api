package com.platform.campaign_service.services.ngo.impl;


import com.platform.campaign_service.controllers.manageExceptions.CustomException;
import com.platform.campaign_service.dtos.ngo.NgoDto;
import com.platform.campaign_service.restClients.ngo.INgoRestClient;
import com.platform.campaign_service.services.ngo.IGetNgoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service implementation for retrieving NGO information.
 */
@Service
@RequiredArgsConstructor
public class GetNgoService implements IGetNgoService {
    /** Logger instance for logging information and errors. */
    private static final Logger LOG = LoggerFactory.getLogger(GetNgoService.class);
    /** REST client for interacting with the NGO service. */
    private final INgoRestClient ngoRestClient;

    /**
     * Retrieves the NGO information associated with the current user context.
     *
     * @return ngoDto.
     */
    @Override
    public NgoDto getNgoUserContext() {
        LOG.trace("getNgoUserContext()");
        NgoDto ngoDto = ngoRestClient.getMyNgo().getBody();
        if (ngoDto == null) {
            LOG.error("NgoDto is null");
            throw new CustomException("Error retrieve NGO info", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  ngoDto;
    }

    /**
     * Retrieves the details of a specific NGO by its ID.
     *
     * @param ngoId the unique identifier of the NGO
     * @return the NgoDto representing the NGO details
     */
    @Override
    public NgoDto getNgoById(UUID ngoId) {
        LOG.trace("getNgoById({})", ngoId);
        NgoDto ngoDto = ngoRestClient.getNgoById(ngoId).getBody();
        if (ngoDto == null) {
            LOG.error("NgoDto is null for ngoId: {}", ngoId);
            throw new CustomException("Error retrieve NGO info", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return   ngoDto;
    }

    /**
     * Retrieves all approved NGOs.
     *
     * @return a map of NGO identifiers to their corresponding NgoDto.
     */
    @Override
    public Map<String, NgoDto> getAllNgosApproved() {
        LOG.trace("getAllNgosApproved()");
        NgoDto[] ngosDto;
        try {
            ngosDto = ngoRestClient.getAllNgosApproved().getBody();
            if (ngosDto == null) {
                LOG.error("NgosDto is null");
                ngosDto = new NgoDto[0];
            }
        } catch (CustomException e) {
            LOG.error("Error retrieving approved NGOs: {}", e.getMessage());
            ngosDto = new NgoDto[0];
        }
        return Arrays.stream(ngosDto).collect(Collectors.toMap(NgoDto::getId, ngoDto -> ngoDto, (a, b) -> a));
    }

    /**
     * Retrieves all NGOs.
     *
     * @return a map of NGO identifiers to their corresponding NgoDto.
     */
    @Override
    public Map<String, NgoDto> getAllNgos() {
        LOG.trace("getAllNgos()");
        NgoDto[] ngosDto;
        try {
            ngosDto = ngoRestClient.getAllNgos().getBody();
            if (ngosDto == null) {
                LOG.error("NgosDto is null");
                ngosDto = new NgoDto[0];
            }
        } catch (CustomException e) {
            LOG.error("Error retrieving NGOs: {}", e.getMessage());
            ngosDto = new NgoDto[0];
        }
        return Arrays.stream(ngosDto).collect(Collectors.toMap(NgoDto::getId, ngoDto -> ngoDto, (a, b) -> a));
    }
}

