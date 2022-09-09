package uz.yshub.makesense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.yshub.makesense.domain.Catalog;

/**
 * Spring Data JPA repository for the Catalog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {}
