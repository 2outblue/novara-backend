package com.novaraspace.service;

import com.novaraspace.model.domain.UserStatusParams;
import com.novaraspace.model.dto.admin.*;
import com.novaraspace.model.entity.User;
import com.novaraspace.model.enums.AccountStatus;
import com.novaraspace.model.enums.audit.PassEventType;
import com.novaraspace.model.events.ChangeUserStatusEvent;
import com.novaraspace.model.events.PasswordEvent;
import com.novaraspace.model.exception.UserException;
import com.novaraspace.model.mapper.UserMapper;
import com.novaraspace.model.other.PageResponse;
import com.novaraspace.repository.AuditLogRepository;
import com.novaraspace.repository.RefreshTokenRepository;
import com.novaraspace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Value("${app.jwt.expiry-minutes}")
    private int jwtExpiryMinutes;

    @Value("${app.enable-email-password-reset}")
    private boolean passResetsEnabled;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthService authService;
    private final ApplicationEventPublisher eventPublisher;
    private final AuditLogRepository auditLogRepository;


    public AdminService(UserRepository userRepository, UserMapper userMapper, RefreshTokenRepository refreshTokenRepository, AuthService authService, ApplicationEventPublisher eventPublisher, AuditLogRepository auditLogRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.refreshTokenRepository = refreshTokenRepository;
        this.authService = authService;
        this.eventPublisher = eventPublisher;
        this.auditLogRepository = auditLogRepository;
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
            Long totalLogins = auditLogRepository.countTotalLoginsAfter(instant);
            resp.setTotalLogins(totalLogins);
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



    public PageResponse<UserControlResult> getUserSearchPage(UserControlSearchDTO req) {
        Pageable pageable = PageRequest.of(
                req.getPage(),
                req.getSize(),
                Sort.by("createdAt").ascending());

        Page<User> page = userRepository.getPageForUcSearch(req, pageable);
        List<UserControlResult> list = page.getContent().stream().map(userMapper::entityToUcResult).toList();
        return new PageResponse<UserControlResult>()
                .setContent(list)
                .setPage(page.getNumber())
                .setSize(page.getSize())
                .setTotalElements(page.getTotalElements())
                .setTotalPages(page.getTotalPages());
    }

    @Transactional(readOnly = true)
    public UserDetailsDTO getUserDetails(UserDetailsRequestDTO req) {
        Optional<User> user = Optional.empty();
        if (req.getId() != null) {
            user = userRepository.findById(req.getId());
        } else if (req.getEmail() != null) {
            user = userRepository.findByEmail(req.getEmail());
        }
        User u = user.orElseThrow(UserException::notFound);
        Long totalUserLogins = auditLogRepository.countLoginsForUser(u.getId());
        return userMapper.entityToUcDetails(u).setTotalLogins(totalUserLogins);
    }

    @Transactional
    public UserDetailsDTO changeUserStatus(ChangeUserStatusRequestDTO req) {
        int count = userRepository.updateUserStatus(req);
        if (count <= 0) { throw UserException.notFound(); }
        User user = userRepository.findById(req.getId()).orElse(null);
        if (user == null) {
            throw UserException.notFound();
        }
        if (req.getStatus().equals(AccountStatus.DEACTIVATED) || req.getStatus().equals(AccountStatus.SUSPENDED)) {
            authService.invalidateAllUserSessions(user.getAuthId());
        }

        eventPublisher.publishEvent(new ChangeUserStatusEvent(
                user.getId(), user.getEmail(), user.getStatus()));
        return userMapper.entityToUcDetails(user);
    }

    @Transactional
    public void resetUserPassword(UcPasswordResetRequest req) {
        if (!passResetsEnabled) { return; };
        User user = userRepository.findById(req.getId()).orElseThrow(UserException::notFound);
        String email = user.getEmail();
        authService.sendPasswordResetLink(email);
        eventPublisher.publishEvent(new PasswordEvent(PassEventType.RESET_REQUEST, email));
    }



}
