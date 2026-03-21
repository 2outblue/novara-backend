package com.novaraspace.model.dto.admin;

import org.springframework.lang.Nullable;

public class AdminPanelDataResponse {
    private Integer totalUsers;
    private EmailsAndTotalCount activeUsers;
    private EmailsAndTotalCount userLogins;
    private EmailsAndTotalCount newUsers;
    private EmailsAndTotalCount statusUsers;

    public Integer getTotalUsers() {
        return totalUsers;
    }

    public AdminPanelDataResponse setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
        return this;
    }

    public EmailsAndTotalCount getActiveUsers() {
        return activeUsers;
    }

    public AdminPanelDataResponse setActiveUsers(EmailsAndTotalCount activeUsers) {
        this.activeUsers = activeUsers;
        return this;
    }

    public EmailsAndTotalCount getUserLogins() {
        return userLogins;
    }

    public AdminPanelDataResponse setUserLogins(EmailsAndTotalCount userLogins) {
        this.userLogins = userLogins;
        return this;
    }

    public EmailsAndTotalCount getNewUsers() {
        return newUsers;
    }

    public AdminPanelDataResponse setNewUsers(EmailsAndTotalCount newUsers) {
        this.newUsers = newUsers;
        return this;
    }

    public EmailsAndTotalCount getStatusUsers() {
        return statusUsers;
    }

    public AdminPanelDataResponse setStatusUsers(EmailsAndTotalCount statusUsers) {
        this.statusUsers = statusUsers;
        return this;
    }
}
