package ro.netinstructor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.netinstructor.repositries.UserRepository;

import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(u -> new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPassword(),
                        Set.of(new SimpleGrantedAuthority("ROLE_" + u.getUserRole().name()))))
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
