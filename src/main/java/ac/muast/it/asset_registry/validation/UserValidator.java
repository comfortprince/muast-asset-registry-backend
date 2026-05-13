// validation/UniqueFieldValidator.java
package ac.muast.it.asset_registry.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ac.muast.it.asset_registry.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

@Component("beforeCreateUserValidator")
public class UserValidator implements Validator {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        
        // Check email uniqueness
        checkUniqueness(user.getEmail(), "email", user.getId(), errors);
        
        // Check username uniqueness
        checkUniqueness(user.getUsername(), "username", user.getId(), errors);
    }
    
    private void checkUniqueness(String value, String field, Long userId, Errors errors) {
        if (value == null || value.trim().isEmpty()) {
            return; // Skip uniqueness check for empty values (let @NotBlank handle)
        }
        
        try (EntityManager em = entityManagerFactory.createEntityManager()) {
            String query = String.format(
                "SELECT COUNT(u) FROM User u WHERE u.%s = :value", field);
            
            if (userId != null) {
                query += " AND u.id != :userId";
            }
            
            var typedQuery = em.createQuery(query, Long.class)
                .setParameter("value", value);
            
            if (userId != null) {
                typedQuery.setParameter("userId", userId);
            }
            
            Long count = typedQuery.getSingleResult();
            
            if (count > 0) {
                errors.rejectValue(field, field + ".duplicate", 
                    field.substring(0, 1).toUpperCase() + field.substring(1) + 
                    " '" + value + "' is already taken");
            }
        } catch (Exception e) {
            errors.reject("database.error", "Unable to validate " + field + " uniqueness");
        }
    }
}