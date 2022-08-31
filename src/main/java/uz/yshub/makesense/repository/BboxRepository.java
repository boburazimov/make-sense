package uz.yshub.makesense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.yshub.makesense.domain.Bbox;

/**
 * Spring Data JPA repository for the Bbox entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BboxRepository extends JpaRepository<Bbox, Long> {}
