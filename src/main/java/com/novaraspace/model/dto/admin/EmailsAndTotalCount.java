package com.novaraspace.model.dto.admin;

import java.util.List;

public class EmailsAndTotalCount {
    private List<String> emails;
    private int totalCount;

    public List<String> getEmails() {
        return emails;
    }

    public EmailsAndTotalCount setEmails(List<String> emails) {
        this.emails = emails;
        return this;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public EmailsAndTotalCount setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }
}
