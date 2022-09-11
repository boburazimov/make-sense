package uz.yshub.makesense.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yshub.makesense.service.dto.ApiResponse;
import uz.yshub.makesense.service.dto.PointDto;
import uz.yshub.makesense.service.impl.PointServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Point", description = "The Point API.")
public class PointController {

    @Autowired
    PointServiceImpl pointService;

    @PostMapping(value = "/point")
    public ResponseEntity<?> point(@RequestBody List<PointDto> pointDto) {
        pointService.save(pointDto);
        return ResponseEntity.ok(new ApiResponse(true,"point annotations exported successfully",null));
    }
}
