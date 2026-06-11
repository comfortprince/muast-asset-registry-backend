package ac.muast.it.asset_registry.configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.validation.Validator;

@Configuration
public class ValidatorEventRegister implements InitializingBean {

    final ValidatingRepositoryEventListener validatingRepositoryEventListener;

    private final Map<String, Validator> validators;

    ValidatorEventRegister(ValidatingRepositoryEventListener validatingRepositoryEventListener, Map<String, Validator> validators) {
        this.validatingRepositoryEventListener = validatingRepositoryEventListener;
        this.validators = validators;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<String> events = Arrays.asList("beforeCreate");
        for (Map.Entry<String, Validator> entry : validators.entrySet()) {
            events.stream()
              .filter(p -> entry.getKey().startsWith(p))
              .findFirst()
              .ifPresent(
                p -> validatingRepositoryEventListener
               .addValidator(p, entry.getValue()));
        }
    }
}