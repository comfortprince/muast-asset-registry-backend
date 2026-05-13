package ac.muast.it.asset_registry.event;

import ac.muast.it.asset_registry.model.User;

import java.util.HashSet;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class UserEventHandler {

    @HandleBeforeCreate
    public void handleUserCreate(User user) {
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
    }
}