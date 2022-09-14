package uz.yshub.makesense.service;

import uz.yshub.makesense.domain.enumeration.ProjectTypeEnum;
import uz.yshub.makesense.service.dto.ApiResponse;
import uz.yshub.makesense.service.dto.MainDTO;

/**
 * Service Interface for managing all entities.
 */
public interface MainService {

    /**
     * Create all Entities by the loop.
     * TODO: Rename method from savePoint to save.
     * @param mainDTO the entity to save.
     * @return the persisted entity.
     */
    ApiResponse savePoint(MainDTO mainDTO, ProjectTypeEnum type);

}
