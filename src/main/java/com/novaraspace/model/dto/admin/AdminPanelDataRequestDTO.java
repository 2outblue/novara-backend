package com.novaraspace.model.dto.admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Past;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public class AdminPanelDataRequestDTO {
    @Nullable
    private Boolean totalUsers;
    @Nullable
    private Boolean currentActiveUsers;
    @Past
    @Nullable
    private LocalDateTime userLogins;
    @Past
    @Nullable
    private LocalDateTime newUsers;
    @Nullable
    @Valid
    private UsersStatusRequestDTO usersStatus;


    @AssertTrue(message = "Invalid userLogins date")
    public boolean isUserLoginsDateInvalid() {
        return userLogins == null || userLogins.isAfter(getMinDate());
    }
    @AssertTrue(message = "Invalid newUsers date")
    public boolean isNewUsersDateInvalid() {
        return newUsers == null || newUsers.isAfter(getMinDate());
    }

    private LocalDateTime getMinDate() {
        return LocalDateTime.now().minusMonths(1).minusHours(1);
    }

    public Boolean getTotalUsers() {
        return totalUsers;
    }

    public AdminPanelDataRequestDTO setTotalUsers(Boolean totalUsers) {
        this.totalUsers = totalUsers;
        return this;
    }

    public Boolean getCurrentActiveUsers() {
        return currentActiveUsers;
    }

    public AdminPanelDataRequestDTO setCurrentActiveUsers(Boolean currentActiveUsers) {
        this.currentActiveUsers = currentActiveUsers;
        return this;
    }

    public LocalDateTime getUserLogins() {
        return userLogins;
    }

    public AdminPanelDataRequestDTO setUserLogins(LocalDateTime userLogins) {
        this.userLogins = userLogins;
        return this;
    }

    public LocalDateTime getNewUsers() {
        return newUsers;
    }

    public AdminPanelDataRequestDTO setNewUsers(LocalDateTime newUsers) {
        this.newUsers = newUsers;
        return this;
    }

    public UsersStatusRequestDTO getUsersStatus() {
        return usersStatus;
    }

    public AdminPanelDataRequestDTO setUsersStatus(UsersStatusRequestDTO usersStatus) {
        this.usersStatus = usersStatus;
        return this;
    }
}
