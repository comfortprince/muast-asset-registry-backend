// repository/UserRepository.java
package ac.muast.it.asset_registry.repository;

import ac.muast.it.asset_registry.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    @Override
    List<User> findAll();
    
    @Override
    Optional<User> findById(Long id);
    
    @Override
    User save(User user);
    
    @Override
    void delete(User user);
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findByUsername(String username);
    
    boolean existsByEmail(String email);
    
    boolean existsByUsername(String username);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :username, '%'))")
    List<User> findByUsernameContainingIgnoreCase(@Param("username") String username);
}