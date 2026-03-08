package com.novaraspace.model.entity;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public class UserDocument {
    private String docId;
    private String filename;
    private LocalDate uploadedAt;

    public String getDocId() {
        return docId;
    }

    public UserDocument setDocId(String docId) {
        this.docId = docId;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public UserDocument setFilename(String name) {
        this.filename = name;
        return this;
    }

    public LocalDate getUploadedAt() {
        return uploadedAt;
    }

    public UserDocument setUploadedAt(LocalDate uploadedAt) {
        this.uploadedAt = uploadedAt;
        return this;
    }
}
