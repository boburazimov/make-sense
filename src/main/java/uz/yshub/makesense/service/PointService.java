package uz.yshub.makesense.service;

import uz.yshub.makesense.service.dto.PointDto;

import java.util.List;

public interface PointService {
    String save(List<PointDto> pointDtos);
}
