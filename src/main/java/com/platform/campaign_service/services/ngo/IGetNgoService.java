package com.platform.campaign_service.services.ngo;

import com.platform.campaign_service.dtos.ngo.NgoDto;

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
}
