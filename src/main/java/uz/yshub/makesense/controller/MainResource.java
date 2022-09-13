package uz.yshub.makesense.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yshub.makesense.controller.errors.BadRequestAlertException;
import uz.yshub.makesense.domain.enumeration.ProjectTypeEnum;
import uz.yshub.makesense.repository.ImageRepository;
import uz.yshub.makesense.service.MainService;
import uz.yshub.makesense.service.dto.ApiResponse;
import uz.yshub.makesense.service.dto.MainAnnotationDTO;
import uz.yshub.makesense.service.dto.MainDTO;
import uz.yshub.makesense.service.dto.MainImageDTO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/main")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Main", description = "The Main API which make all need objects and field for store annotation model.")
public class MainResource {

    private final Logger log = LoggerFactory.getLogger(MainResource.class);
    private static final String ENTITY_NAME = "MainProject";

    private final MainService mainService;
    private final ImageRepository imageRepository;

    /**
     * @param mainDTO the object of project by type.
     * @return the {@link ApiResponse} with status {@code 201 (Created)} and with body the new Response.
     * @throws BadRequestAlertException if the object is empty (Null).
     * @code POST : Create new Project type of POINT.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> savePoint(@RequestBody MainDTO mainDTO, @RequestParam(name = "type") ProjectTypeEnum type) {
        log.debug("REST request to save POINT-annotations by Main endpoint.");

        if (mainDTO == null) {
            throw new BadRequestAlertException("Object of POINT must not be null!", ENTITY_NAME, "nullObject");
        }

        ApiResponse apiResponse;
        List<MainImageDTO> imageCollect = mainDTO.getImages().stream().filter(Objects::nonNull).collect(Collectors.toList());
        for (MainImageDTO el : imageCollect) {
            boolean existsById = imageRepository.existsById(el.getId());
            if (!existsById){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Image not found by ID: " + el.getId()));
            }
        }

        List<MainAnnotationDTO> annotationCollect = mainDTO.getAnnotations().stream().filter(Objects::nonNull).collect(Collectors.toList());
        for (MainAnnotationDTO el : annotationCollect) {
            boolean existsById = imageRepository.existsById(el.getImage_id());
            if (!existsById){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Image in the Annotation not found by ID: " + el.getImage_id()));

            }
        }

        apiResponse = mainService.savePoint(mainDTO, type);

        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST)
                .body(apiResponse);
    }
}
