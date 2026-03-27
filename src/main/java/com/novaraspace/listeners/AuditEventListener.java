package com.novaraspace.listeners;

import com.novaraspace.model.entity.AuditLog;
import com.novaraspace.model.entity.User;
import com.novaraspace.model.enums.UserRole;
import com.novaraspace.model.enums.audit.*;
import com.novaraspace.model.events.*;
import com.novaraspace.repository.AuditLogRepository;
import com.novaraspace.service.CurrentUserService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class AuditEventListener {

    private final CurrentUserService currentUserService;
    private final AuditLogRepository logRepository;

    public AuditEventListener(CurrentUserService currentUserService, AuditLogRepository logRepository) {
        this.currentUserService = currentUserService;
        this.logRepository = logRepository;
    }

    @EventListener
    public void handleUserLoginEvent(UserLoginEvent e) {

        Long id = null;
        AuditRole role = null;
        User user = currentUserService.getAuthenticatedUser().orElse(null);
        boolean success = e.outcome().equals(Outcome.SUCCESS);
        if (success && user != null) {
            id = user.getId();
            role = determineAuditRole(user.getRoles());
        }

        AuditLog log = new AuditLog()
                .setActorId(id)
                .setActorRole(role)
                .setActorEmail(success ? e.email() : null)
                .setAction(AuditAction.LOGIN)
                .setTargetType(AuditTargetType.USER)
                .setTargetDetails(success ? null : e.email())
                .setOutcome(e.outcome());
        logRepository.save(log);
    }

    @EventListener
    public void handleUserLogoutEvent(UserLogoutEvent e) {
        AuditLog log = new AuditLog()
                .setActorId(e.userId())
                .setActorEmail(e.userEmail())
                .setAction(AuditAction.LOGOUT)
                .setTargetType(AuditTargetType.USER)
                .setTargetId(e.userId())
                .setOutcome(Outcome.SUCCESS);
        logRepository.save(log);
    }

    @EventListener
    public void handleUserRegisterEvent(UserRegisterEvent e) {
        AuditLog log = new AuditLog()
                .setAction(AuditAction.REGISTER)
                .setTargetType(AuditTargetType.USER)
                .setTargetDetails(e.userEmail())
                .setTargetId(e.userId())
                .setOutcome(Outcome.SUCCESS);
        logRepository.save(log);
    }

    @EventListener
    public void handlePasswordEvent(PasswordEvent e) {
        User user = currentUserService.getAuthenticatedUser().orElse(null);
        AuditRole role = user != null ? determineAuditRole(user.getRoles()) : null;
        String actorEmail = user != null ? user.getEmail() : null;

        AuditLog log = new AuditLog()
                .setAction(e.type().toAuditAction())
                .setActorId(user != null ? user.getId() : null)
                .setActorRole(role)
                .setActorEmail(actorEmail)
                .setTargetType(AuditTargetType.USER)
                .setTargetDetails(e.email())
                .setOutcome(Outcome.SUCCESS);
        logRepository.save(log);
    }


    @EventListener
    public void handleBookingEvent(BookingEvent e) {
        User user = currentUserService.getAuthenticatedUser().orElse(null);

        AuditRole role = user != null ? determineAuditRole(user.getRoles()) : null;
        AuditLog log = new AuditLog()
                .setActorId(user != null ? user.getId() : null)
                .setActorEmail(user != null ? user.getEmail() : null)
                .setActorRole(role)
                .setAction(e.type().toAuditAction())
                .setTargetType(AuditTargetType.BOOKING)
                .setTargetDetails(e.reference())
                .setTargetId(e.id())
                .setOutcome(Outcome.SUCCESS);
        logRepository.save(log);
    }

    @EventListener
    public void handleUserStatusChangeEvent(ChangeUserStatusEvent e) {
        User user = currentUserService.getAuthenticatedUser().orElse(null);
        AuditRole role = user != null ? determineAuditRole(user.getRoles()) : null;

        StringBuilder sb = new StringBuilder();
        String details = sb.append(" new status: ")
                .append(e.newStatus()).toString();

        AuditLog log = new AuditLog()
                .setActorId(user != null ? user.getId() : null)
                .setActorEmail(user != null ? user.getEmail() : null)
                .setActorRole(role)
                .setAction(AuditAction.CHANGE_USER_STATUS)
                .setTargetType(AuditTargetType.USER)
                .setTargetDetails(e.targetUserEmail())
                .setTargetId(e.targetUserId())
                .setOutcome(Outcome.SUCCESS)
                .setDetails(details);
        logRepository.save(log);
    }


    private AuditRole determineAuditRole(Set<UserRole> userRoles) {
        if (userRoles.contains(UserRole.ADMIN)) {
            return AuditRole.ADMIN;
        }
        if (userRoles.contains(UserRole.USER) && !userRoles.contains(UserRole.ADMIN)) {
            return AuditRole.USER;
        }
        return AuditRole.USER;
    }
}
