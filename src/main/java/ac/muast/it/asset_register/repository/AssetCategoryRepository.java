// repository/AssetCategoryRepository.java
package ac.muast.it.asset_register.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ac.muast.it.asset_register.model.AssetCategory;

import java.util.Optional;

public interface AssetCategoryRepository extends JpaRepository<AssetCategory, Long> {
    Optional<AssetCategory> findByName(String name);
    boolean existsByName(String name);
}