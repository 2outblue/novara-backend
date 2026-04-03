package com.novaraspace.model.dto.user;

import com.novaraspace.validation.annotations.ValidUserDocumentUpdateRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@ValidUserDocumentUpdateRequest
public class UserDocumentUpdateRequest {
    @Size(min = 1, max = 10)
    private String docId;
    @Size(min = 1, max = 200)
    private String filename;
    @Size(min = 6, max = 6)
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
