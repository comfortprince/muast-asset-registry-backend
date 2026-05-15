package ac.muast.it.asset_registry.security;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ac.muast.it.asset_registry.model.User;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
	private final UserAuthRepository userAuthRespository;

	// @Override
	public UserDetails loadUserByUsername(String username){
		User user = userAuthRespository.findByUsername(username)
			.orElseThrow(() -> {
				log.error("User not found with username: {}", username);
				return new UsernameNotFoundException("User with username:" + username + " not found");
			});

		if (!user.isAccountNonLocked()) {
			log.error("User account is locked for user: {}", username);
			throw new LockedException("User account is locked");
		}
		
		if (!user.isEnabled()) {
			log.error("User account is disabled for user: {}", username);
			throw new DisabledException("User account is disabled");
		}
		
		if (!user.isAccountNonExpired()) {
			log.error("User account has expired for user: {}", username);
			throw new AccountExpiredException("User account has expired");
		}
		
		if (!user.isCredentialsNonExpired()) {
			log.error("User credentials have expired for user: {}", username);
			throw new CredentialsExpiredException("User credentials have expired");
		}

		return (UserDetails) user;
	}
}
