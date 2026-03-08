package com.novaraspace.validation.validators;

import com.novaraspace.model.dto.user.UserDocumentUpdateRequest;
import com.novaraspace.validation.annotations.ValidUserDocumentUpdateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UserDocumentUpdateRequestValidator implements ConstraintValidator<ValidUserDocumentUpdateRequest, UserDocumentUpdateRequest> {

    private final Set<String> validActions = new HashSet<>(Arrays.asList("upload", "remove"));

    @Override
    public boolean isValid(UserDocumentUpdateRequest value, ConstraintValidatorContext context) {
        String action = value.getAction();
        String filename = value.getFilename();

        if (action == null || action.isBlank() || !validActions.contains(action)) {
            return false;
        }
        if (action.equals("upload") && (filename == null || filename.isBlank())) {
            return false;
        }
        if (filename != null && filename.length() > 200) { return false; }

        String docId = value.getDocId();
        return docId != null && !docId.isBlank() && docId.length() <= 10;
    }
}
