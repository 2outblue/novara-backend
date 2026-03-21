package com.novaraspace.service;

import com.novaraspace.model.domain.UserStatusParams;
import com.novaraspace.model.dto.admin.AdminPanelDataRequestDTO;
import com.novaraspace.model.dto.admin.AdminPanelDataResponse;
import com.novaraspace.model.dto.admin.EmailsAndTotalCount;
import com.novaraspace.model.dto.admin.UsersStatusRequestDTO;
import com.novaraspace.model.entity.User;
import com.novaraspace.repository.RefreshTokenRepository;
import com.novaraspace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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



    //Maybe have some util that has functions and just call them in here?
    public AdminPanelDataResponse getAdminPanelData(AdminPanelDataRequestDTO req) {
        AdminPanelDataResponse resp = new AdminPanelDataResponse();

        if (req.getTotalUsers() != null && req.getTotalUsers()) {
            resp.setTotalUsers((int) userRepository.count());
        }
        if (req.getCurrentActiveUsers() != null && req.getCurrentActiveUsers()) {
            resp.setActiveUsers(getCurrentActiveUsers());
        }
        if (req.getUserLogins() != null) {
//            Instant instant = req.getUserLogins().atZone(ZoneId.systemDefault()).toInstant();
            Instant instant = req.getUserLogins().atOffset(ZoneOffset.UTC).toInstant();
            int totalCount = userRepository.countByLastLoginAtAfter(instant);
            List<String> emails = userRepository.findAllEmailsByLastLoginAtAfter(instant, Pageable.ofSize(100));
            resp.setUserLogins(new EmailsAndTotalCount().setEmails(emails).setTotalCount(totalCount));
        }
        if (req.getNewUsers() != null) {
            Instant instant = req.getNewUsers().atOffset(ZoneOffset.UTC).toInstant();
            int totalCount = userRepository.countByCreatedAtAfter(instant);
            List<String> emails = userRepository.findAllEmailsByCreatedAtAfter(instant, Pageable.ofSize(100));
            resp.setNewUsers(new EmailsAndTotalCount().setEmails(emails).setTotalCount(totalCount));
        }
        UsersStatusRequestDTO statusReq = req.getUsersStatus();
        if (statusReq != null) {
            Instant instant = statusReq.getRegisteredAfter().atOffset(ZoneOffset.UTC).toInstant();
            UserStatusParams params = new UserStatusParams(instant, statusReq.getStatus());
            int totalCount = userRepository.countForStatus(params);
            List<String> emails = userRepository.findUserEmailsForStatus(params, Pageable.ofSize(100));
            resp.setStatusUsers(new EmailsAndTotalCount().setEmails(emails).setTotalCount(totalCount));
        }
        return resp;
    }

    public EmailsAndTotalCount getCurrentActiveUsers() {
        LocalDateTime date = LocalDateTime.now().minusMinutes(jwtExpiryMinutes);
        List<String> authIds = refreshTokenRepository.getAllAuthIdsForActiveUsers(date, Pageable.ofSize(100));
        List<String> emails = userRepository.findAllByAuthIdIn(authIds).stream().map(User::getEmail).toList();
        Integer totalCount = refreshTokenRepository.getCountForActiveUsers(date);
        return new EmailsAndTotalCount().setEmails(emails).setTotalCount(totalCount);
    }


//    private EmailsAndTotalCount getUserLoginsSince(LocalDateTime date) {
//        if (date.isBefore(LocalDateTime.now().minusMonths(1).minusHours(12))) {
//            date = LocalDateTime.now().minusMonths(1);
//        }
//        Instant instant = date.atZone(ZoneId.systemDefault()).toInstant();
//        int totalCount = userRepository.countByLastLoginAtAfter(instant);
//        List<String> emails = userRepository.findAllEmailsByLastLoginAtAfter(instant, Pageable.ofSize(100));
//        return new EmailsAndTotalCount().setEmails(emails).setTotalCount(totalCount);
//    }




}
