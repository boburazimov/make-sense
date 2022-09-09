package uz.yshub.makesense.service.mapper;

import org.springframework.stereotype.Service;
import uz.yshub.makesense.domain.Bbox;
import uz.yshub.makesense.service.dto.BboxDTO;

/**
 * Mapper for the entity {@link Bbox} and its DTO {@link BboxDTO}.
 */
@Service
public class BboxMapper {

    public BboxDTO toDto(Bbox bbox) {
        if (bbox != null) {
            return new BboxDTO(bbox);
        } else {
            return null;
        }
    }

    public Bbox toEntity(BboxDTO bboxDTO) {
        if (bboxDTO != null) {
            return new Bbox(bboxDTO);
        } else {
            return null;
        }
    }

    public void partialUpdate(Bbox entity, BboxDTO dto) {

        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getBboxX() != null ) {
            entity.setBboxX( dto.getBboxX() );
        }
        if ( dto.getBboxY() != null ) {
            entity.setBboxY( dto.getBboxY() );
        }
        if ( dto.getBboxWidth() != null ) {
            entity.setBboxWidth( dto.getBboxWidth() );
        }
        if ( dto.getBboxHeght() != null ) {
            entity.setBboxHeght( dto.getBboxHeght() );
        }
    }
}
