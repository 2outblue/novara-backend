package com.novaraspace.listeners;

import com.novaraspace.model.dto.user.UserSummary;
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
        UserSummary userSummary = currentUserService.getUserSummary().orElse(null);

        boolean success = e.outcome().equals(Outcome.SUCCESS);
        if (success && userSummary != null) {
            id = userSummary.id();
            role = determineAuditRole(userSummary.roles());
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
        UserSummary userSummary = currentUserService.getUserSummary().orElse(null);
        AuditRole role = userSummary != null ? determineAuditRole(userSummary.roles()) : null;
        String actorEmail = userSummary != null ? userSummary.email() : null;

        AuditLog log = new AuditLog()
                .setAction(e.type().toAuditAction())
                .setActorId(userSummary != null ? userSummary.id() : null)
                .setActorRole(role)
                .setActorEmail(actorEmail)
                .setTargetType(AuditTargetType.USER)
                .setTargetDetails(e.email())
                .setOutcome(Outcome.SUCCESS);
        logRepository.save(log);
    }


    @EventListener
    public void handleBookingEvent(BookingEvent e) {
//        User user = currentUserService.getUserEntity().orElse(null);
//        AuditRole role = user != null ? determineAuditRole(user.getRoles()) : null;

        UserSummary userSummary = currentUserService.getUserSummary().orElse(null);
        AuditRole role = userSummary != null ? determineAuditRole(userSummary.roles()) : null;
        AuditLog log = new AuditLog()
                .setActorId(userSummary != null ? userSummary.id() : null)
                .setActorEmail(userSummary != null ? userSummary.email() : null)
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
        UserSummary userSummary = currentUserService.getUserSummary().orElse(null);
//        User user = currentUserService.getUserEntity().orElse(null);
        AuditRole role = userSummary != null ? determineAuditRole(userSummary.roles()) : null;

        String details = " new status: " +
                e.newStatus();

        AuditLog log = new AuditLog()
                .setActorId(userSummary != null ? userSummary.id() : null)
                .setActorEmail(userSummary != null ? userSummary.email() : null)
                .setActorRole(role)
                .setAction(AuditAction.CHANGE_USER_STATUS)
                .setTargetType(AuditTargetType.USER)
                .setTargetDetails(e.targetUserEmail())
                .setTargetId(e.targetUserId())
                .setOutcome(Outcome.SUCCESS)
                .setDetails(details);
        logRepository.save(log);
    }

    @EventListener
    public void handleScheduledTaskEvent(ScheduledTaskEvent e) {
        String details = e.type() + " records affected: " + e.count();

        AuditLog log = new AuditLog()
                .setActorRole(AuditRole.SYSTEM)
                .setAction(AuditAction.SCHEDULED_TASK)
                .setTargetType(AuditTargetType.MAINTENANCE)
                .setOutcome(e.outcome())
                .setDetails(details);
        logRepository.save(log);
    }


    private AuditRole determineAuditRole(Set<UserRole> userRoles) {
        if (userRoles.contains(UserRole.ADMIN) || userRoles.contains(UserRole.PUBLIC_ADMIN)) {
            return AuditRole.ADMIN;
        }

        return AuditRole.USER;
    }
}
