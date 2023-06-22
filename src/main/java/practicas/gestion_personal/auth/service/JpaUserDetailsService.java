package practicas.gestion_personal.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practicas.gestion_personal.domain.entities.UserEntity;
import practicas.gestion_personal.domain.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> o=userRepository.findByUsername(username);
        if(o.isEmpty()){
            throw new UsernameNotFoundException(String.format("Username %s no existe en el sistema",username));
        }
        UserEntity user = o.orElseThrow();

        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(r->new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
        //$2a$10$DOMDxjYyfZ/e7RcBfUpzqeaCs8pLgcizuiQWXPkU35nOhZlFcE9MS
        return new User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }
}
