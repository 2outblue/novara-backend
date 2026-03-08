package com.novaraspace.model.dto.user;

import com.novaraspace.validation.annotations.ValidUserDocumentUpdateRequest;

@ValidUserDocumentUpdateRequest
public class UserDocumentUpdateRequest {
    private String docId;
    private String filename;
    private String action;

    public String getDocId() {
        return docId;
    }

    public UserDocumentUpdateRequest setDocId(String docId) {
        this.docId = docId;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public UserDocumentUpdateRequest setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public String getAction() {
        return action;
    }

    public UserDocumentUpdateRequest setAction(String action) {
        this.action = action;
        return this;
    }
}
