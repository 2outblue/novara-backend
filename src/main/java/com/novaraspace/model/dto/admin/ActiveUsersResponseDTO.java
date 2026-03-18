package com.novaraspace.model.dto.admin;

import java.util.List;

public class ActiveUsersResponseDTO {

    private List<String> userEmails;
    private int totalCount;

    public List<String> getUserEmails() {
        return userEmails;
    }

    public ActiveUsersResponseDTO setUserEmails(List<String> userEmails) {
        this.userEmails = userEmails;
        return this;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public ActiveUsersResponseDTO setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }
}
