package uz.yshub.makesense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.yshub.makesense.domain.Image;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Image entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<List<Image>> findAllByCatalogIsNull();
    Optional<List<Image>> findAllByCatalogId(Long catalogId);
}
