package uz.yshub.makesense.service.mapper;

import org.springframework.stereotype.Service;
import uz.yshub.makesense.domain.Project;
import uz.yshub.makesense.service.dto.ProjectDTO;

/**
 * Mapper for the entity {@link Project} and its DTO {@link ProjectDTO}.
 */
@Service
public class ProjectMapper {

    public ProjectDTO toDto(Project project) {
        if (project != null) {
            return new ProjectDTO(project);
        } else {
            return null;
        }
    }

    public Project toEntity(ProjectDTO projectDTO) {
        if (projectDTO != null) {
            return new Project(projectDTO);
        } else {
            return null;
        }
    }

    public void partialUpdate(Project entity, ProjectDTO dto) {

        if (dto == null) {
            return;
        }

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getType() != null) {
            entity.setType(dto.getType());
        }
    }
}
