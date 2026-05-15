package ac.muast.it.asset_registry.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @GetMapping("/api/test-auth")
    @PreAuthorize("hasAuthority('READ_USERS')")
    public String test() {
        return "You have READ_USERS";
    }
}
