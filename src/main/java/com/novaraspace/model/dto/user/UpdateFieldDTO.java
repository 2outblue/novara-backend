package com.novaraspace.model.dto.user;

public class UpdateFieldDTO {
    String fieldName;
    Object oldValue;
    Object newValue;

    public UpdateFieldDTO() {
    }

    public String getFieldName() {
        return fieldName;
    }

    public UpdateFieldDTO setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public UpdateFieldDTO setOldValue(Object oldValue) {
        this.oldValue = oldValue;
        return this;
    }

    public Object getNewValue() {
        return newValue;
    }

    public UpdateFieldDTO setNewValue(Object newValue) {
        this.newValue = newValue;
        return this;
    }
}
