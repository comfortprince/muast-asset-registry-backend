// repository/AssetStatusRepository.java
package ac.muast.it.asset_register.repository;

import ac.muast.it.asset_register.model.AssetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AssetStatusRepository extends JpaRepository<AssetStatus, Long> {
    Optional<AssetStatus> findByName(String name);
}