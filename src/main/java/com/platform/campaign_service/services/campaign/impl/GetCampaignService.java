package com.platform.campaign_service.services.campaign.impl;


import com.platform.campaign_service.controllers.manageExceptions.CustomException;
import com.platform.campaign_service.dtos.campaign.CampaignDto;
import com.platform.campaign_service.dtos.ngo.NgoDto;
import com.platform.campaign_service.entities.CampaignEntity;
import com.platform.campaign_service.enums.CampaignState;
import com.platform.campaign_service.map.impl.MapperCampaign;
import com.platform.campaign_service.repositories.CampaignRepository;
import com.platform.campaign_service.services.campaign.ICampaignQueryService;
import com.platform.campaign_service.services.campaign.IGetCampaignService;
import com.platform.campaign_service.services.ngo.IGetNgoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Service implementation for retrieving campaign information.
 */
@Service
@RequiredArgsConstructor
public class GetCampaignService implements IGetCampaignService {
    /** Logger for logging information and errors. */
    private static final Logger LOG = LoggerFactory.getLogger(GetCampaignService.class);
    /** Repository for accessing campaign data. */
    private final CampaignRepository campaignRepository;
    /** Service for querying campaign data. */
    private final ICampaignQueryService campaignQueryService;
    /** Mapper for converting between CampaignEntity and CampaignDto. */
    private final MapperCampaign mapperCampaign;
    /** Service for retrieving NGO information. */
    private final IGetNgoService getNgoService;
    /**
     * Retrieves a CampaignEntity by its unique identifier.
     *
     * @param campaignId The unique identifier of the campaign.
     * @return The CampaignEntity corresponding to the provided ID.
     */
    @Override
    public CampaignEntity getCampaignEntityById(UUID campaignId) {
        LOG.trace("Getting campaign with ID: {}", campaignId);
        try {
            Optional<CampaignEntity> campaignEntity = campaignRepository.findById(campaignId);
            if (campaignEntity.isEmpty()) {
                LOG.trace("No campaign with ID: {}", campaignId);
                throw new CustomException("Campaign not found", HttpStatus.NOT_FOUND);
            }
            return campaignEntity.get();
        } catch (DataAccessException ex) {
            LOG.error("Data access error while retrieving campaign with ID: {}", campaignId, ex);
            throw new CustomException("An error occurred while retrieving campaign", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    /**
     * Retrieves a list of CampaignDto objects based on provided filters.
     *
     * @param campaignState  The state of the campaigns to filter by.
     * @param categoryIds    A set of category IDs to filter the campaigns.
     * @param tags           A set of tags to filter the campaigns.
     * @param organizationId The ID of the organization to which the campaigns belong.
     * @return A list of CampaignDto objects that match the provided filters.
     */
    @Override
    public List<CampaignDto> getCapaignsByFilters(CampaignState campaignState, Set<UUID> categoryIds,
                                                  Set<String> tags, UUID organizationId) {
        LOG.trace("Getting campaigns with filters - State: {}, Category IDs: {}, Tags: {}, Organization ID: {}",
                campaignState, categoryIds, tags, organizationId);
        List<CampaignEntity> campaignEntities = campaignQueryService.getCampaigns(
                campaignState, categoryIds, tags, organizationId);
        Map<String, NgoDto> ngoDtos = getNgoService.getAllNgosApproved();
        return campaignEntities.stream()
                .map(campaignEntity -> mapToDto(campaignEntity, ngoDtos))
                .toList();
    }

    /**
     * Retrieves a CampaignDto by its unique identifier.
     *
     * @param campaignId The unique identifier of the campaign.
     * @return The CampaignDto corresponding to the provided ID.
     */
    @Override
    public CampaignDto getCampaignById(UUID campaignId) {
        CampaignEntity campaignEntity = getCampaignEntityById(campaignId);
        CampaignDto campaignDto = mapToDto(campaignEntity);
        NgoDto ngoDto = getNgoService.getNgoById(campaignEntity.getOrganizationId());
        campaignDto.setNgo(ngoDto);
        return campaignDto;
    }

    private CampaignDto mapToDto(CampaignEntity campaignEntity, Map<String, NgoDto> ngoDtos) {
        CampaignDto campaignDto = mapperCampaign.mapToDto(campaignEntity);
        NgoDto ngoDto = ngoDtos.get(campaignEntity.getOrganizationId().toString());
        campaignDto.setNgo(ngoDto);
        return campaignDto;
    }

    private CampaignDto mapToDto(CampaignEntity campaignEntity) {
        return mapperCampaign.mapToDto(campaignEntity);
    }
}
