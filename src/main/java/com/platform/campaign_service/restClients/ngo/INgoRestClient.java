package com.platform.campaign_service.restClients.ngo;

import com.platform.campaign_service.dtos.ngo.NgoDto;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/**
 * REST client interface for interacting with the NGO service.
 */
public interface INgoRestClient {
    /**
     * Retrieves the NGO information associated with the current user.
     *
     * @return a ResponseEntity containing the NgoDto if found,
     * or an appropriate error response if no NGO is associated with the user
     */
    ResponseEntity<NgoDto> getMyNgo();

    /**
     * Retrieves all approved NGOs.
     *
     * @return a ResponseEntity containing an array of
     * NgoDto representing all approved NGOs
     */
    ResponseEntity<NgoDto[]> getAllNgosApproved();
    /**
     * Retrieves all NGOs.
     *
     * @return a ResponseEntity containing an array of
     * NgoDto representing all NGOs
     */
    ResponseEntity<NgoDto[]> getAllNgos();
    /**
     * Retrieves the details of a specific NGO by its ID.
     *
     * @param ngoId the unique identifier of the NGO
     * @return a ResponseEntity containing the NgoDto representing the NGO details
     */
    ResponseEntity<NgoDto> getNgoById(UUID ngoId);
}
