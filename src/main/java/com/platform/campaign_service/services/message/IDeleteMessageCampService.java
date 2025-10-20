package com.platform.campaign_service.services.message;

import java.util.UUID;

public interface IDeleteMessageCampService {
    /**
     * Deletes a message campaign by its ID.
     * @param messageCampaignId The UUID of the
     *                          message campaign to be deleted.
     */
    void deleteMessageCampaign(UUID messageCampaignId);
}
