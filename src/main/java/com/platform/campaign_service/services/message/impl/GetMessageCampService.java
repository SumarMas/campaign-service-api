package com.platform.campaign_service.services.message.impl;

import com.platform.campaign_service.dtos.campaign.message.MessageCampaignDto;
import com.platform.campaign_service.entities.CampaignMessageEntity;
import com.platform.campaign_service.map.impl.MapperMessageCampaign;
import com.platform.campaign_service.repositories.CampaignMessageRepository;
import com.platform.campaign_service.services.message.IGetMessageCampService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/** Service implementation for retrieving message campaigns. */
@Service
@RequiredArgsConstructor
public class GetMessageCampService implements IGetMessageCampService {
    /** Logger instance for logging information and errors. */
    private static final Logger LOG = LoggerFactory.getLogger(GetMessageCampService.class);
    /** Repository for accessing campaign message data. */
    private final CampaignMessageRepository campaignMessageRepository;
    /** Mapper for converting campaign message entities to DTOs. */
    private final MapperMessageCampaign mapperMessageCampaign;
    /**
     * Retrieves a list of message
     * campaigns associated with a specific campaign ID.
     *
     * @param campaignId The UUID of the campaign.
     * @return A list of MessageCampaignDto objects.
     */
    @Override
    public List<MessageCampaignDto> getMessageCampaigns(UUID campaignId) {
        LOG.trace("In getMessageCampaigns");
        try {
            List<CampaignMessageEntity> campaignMessages =
                    campaignMessageRepository.findByCampaignIdAndEnable(campaignId);
            return campaignMessages.stream().map(mapperMessageCampaign::mapToDto).toList();
        } catch (DataAccessException e) {
            LOG.error("Data access error while retrieving"
                    + "message campaigns for campaignId {}: {}", campaignId, e.getMessage());
            return  List.of();
        }
    }
}
