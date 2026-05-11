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
import ac.muast.it.asset_registry.model.User;
import ac.muast.it.asset_registry.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRespository;

	// @Override
	public UserDetails loadUserByUsername(String username){
		User user = userRespository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("User with username:" + username + " not found"));

		if (!user.isAccountNonLocked()) {
			throw new LockedException("User account is locked");
		}
		
		if (!user.isEnabled()) {
			throw new DisabledException("User account is disabled");
		}
		
		if (!user.isAccountNonExpired()) {
			throw new AccountExpiredException("User account has expired");
		}
		
		if (!user.isCredentialsNonExpired()) {
			throw new CredentialsExpiredException("User credentials have expired");
		}

		return (UserDetails) user;
	}
}
