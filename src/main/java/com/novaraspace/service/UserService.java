package com.novaraspace.service;

import com.novaraspace.model.dto.user.UserRegisterDTO;
import com.novaraspace.model.entity.User;
import com.novaraspace.model.entity.VerificationToken;
import com.novaraspace.model.enums.AccountStatus;
import com.novaraspace.model.exception.UserNotFoundException;
import com.novaraspace.model.mapper.UserMapper;
import com.novaraspace.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Transactional
    public void activateUserAccount(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setStatus(AccountStatus.ACTIVE);
        user.setVerification(null);
    }

    public User persistUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(this::entityToUserDetails)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
    }

    private UserDetails entityToUserDetails(User entity) {
        boolean isActive = entity.getStatus() == AccountStatus.ACTIVE;
        return new org.springframework.security.core.userdetails.User(
                entity.getEmail(),
                entity.getPassword(),
                isActive,
                true,
                true,
                true,
                entity.getRoles().stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                        .collect(Collectors.toList())
        );
    }

    public Optional<User> findEntityByAuthId(String authId) {
        return userRepository.findByAuthId(authId);
    }

    public Optional<String> getAuthIdByEmail(String email) {
        return userRepository.getAuthIdByEmail(email);
    }
}
