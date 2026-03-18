package com.novaraspace.service;

import com.novaraspace.model.dto.admin.ActiveUsersResponseDTO;
import com.novaraspace.model.entity.User;
import com.novaraspace.repository.RefreshTokenRepository;
import com.novaraspace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {

    @Value("${app.jwt.expiry-minutes}")
    private int jwtExpiryMinutes;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public AdminService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }




    public ActiveUsersResponseDTO getCurrentActiveUsers() {
        LocalDateTime date = LocalDateTime.now().minusMinutes(jwtExpiryMinutes);
        List<String> authIds = refreshTokenRepository.getAllUserAuthIdsByCreatedOnAfter(date, Pageable.ofSize(100));
        List<String> emails = userRepository.findAllByAuthIdIn(authIds).stream().map(User::getEmail).toList();
        Integer totalCount = refreshTokenRepository.countByCreatedOnAfter(date);

        return new ActiveUsersResponseDTO().setUserEmails(emails).setTotalCount(totalCount);
    }


}
