package com.novaraspace.model.dto.user;

import java.time.LocalDate;

public class UserDocumentDTO {
    private String docId;
    private String filename;
    private LocalDate uploadedOn;

    public String getDocId() {
        return docId;
    }

    public UserDocumentDTO setDocId(String docId) {
        this.docId = docId;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public UserDocumentDTO setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public LocalDate getUploadedOn() {
        return uploadedOn;
    }

    public UserDocumentDTO setUploadedOn(LocalDate uploadedOn) {
        this.uploadedOn = uploadedOn;
        return this;
    }
}
