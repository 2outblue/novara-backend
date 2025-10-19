package com.novaraspace.service;

import com.novaraspace.model.dto.user.UserRegisterDTO;
import com.novaraspace.model.entity.User;
import com.novaraspace.model.entity.VerificationToken;
import com.novaraspace.model.enums.AccountStatus;
import com.novaraspace.model.exception.UserException;
import com.novaraspace.model.exception.VerificationException;
import com.novaraspace.model.mapper.UserMapper;
import com.novaraspace.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

//    @Transactional
//    public void activateUserAccount(Long userId) {
//        User user = userRepository.findById(userId).orElseThrow(UserException::notFound);
//        user.setStatus(AccountStatus.ACTIVE);
//        user.setVerification(null);
//    }

    @Transactional
    public void activateUserAccount(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(VerificationException::failed);
        user.setStatus(AccountStatus.ACTIVE);
        user.setVerification(null);
    }

    @Transactional
    public void updateUserVerification(String email, VerificationToken verification) {
        User user = userRepository.findByEmail(email).orElseThrow(VerificationException::failed);
        user.setVerification(verification);
    }

    public User persistUser(User user) {
        return userRepository.save(user);
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return userRepository.findByEmail(email)
//                .map(this::entityToUserDetails)
//                .orElseThrow(() -> new BadCredentialsException("Bad credentials."));
//    }
//
//    private UserDetails entityToUserDetails(User entity) {
//        boolean isActive = entity.getStatus() == AccountStatus.ACTIVE;
//        return new org.springframework.security.core.userdetails.User(
//                entity.getEmail(),
//                entity.getPassword(),
//                isActive,
//                true,
//                true,
//                true,
//                entity.getRoles().stream()
//                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
//                        .collect(Collectors.toList())
//        );
//    }

    public Optional<User> findEntityByAuthId(String authId) {
        return userRepository.findByAuthId(authId);
    }
    public Optional<String> getAuthIdByEmail(String email) {
        return userRepository.getAuthIdByEmail(email);
    }
    public Optional<User> getEntityByEmail(String email) { return userRepository.findByEmail(email); }
}
