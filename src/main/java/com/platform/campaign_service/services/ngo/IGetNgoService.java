package com.platform.campaign_service.services.ngo;

import com.platform.campaign_service.dtos.ngo.NgoDto;

import java.util.Map;
import java.util.UUID;

/**
 * Service interface for retrieving NGO information.
 */
public interface IGetNgoService {
    /**
     * Retrieves the NGO information associated with the current user context.
     *
     * @return the NgoDto.
     */
    NgoDto getNgoUserContext();
    /**
     * Retrieves the details of a specific NGO by its ID.
     *
     * @param ngoId the unique identifier of the NGO
     * @return the NgoDto representing the NGO details
     */
    NgoDto getNgoById(UUID ngoId);

    /**
     * Retrieves all approved NGOs.
     *
     * @return a map of NGO identifiers to their corresponding NgoDto.
     */
    Map<String, NgoDto> getAllNgosApproved();
    /**
     * Retrieves all NGOs.
     *
     * @return a map of NGO identifiers to their corresponding NgoDto.
     */
    Map<String, NgoDto> getAllNgos();
}
