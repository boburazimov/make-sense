package uz.yshub.makesense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.yshub.makesense.domain.Catalog;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Catalog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    Optional<List<Catalog>> findAllByParentIsNull();
    Optional<List<Catalog>> findAllByParentId(Long parentId);
}
