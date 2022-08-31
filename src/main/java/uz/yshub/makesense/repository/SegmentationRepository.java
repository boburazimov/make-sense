package uz.yshub.makesense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.yshub.makesense.domain.Segmentation;

/**
 * Spring Data JPA repository for the Segmentation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SegmentationRepository extends JpaRepository<Segmentation, Long> {}
