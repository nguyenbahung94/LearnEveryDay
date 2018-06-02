package com.ekakashi.greenhouse.database.model_server_request;

public class MemberListRequest {
    private int fieldId;

    public MemberListRequest(int fieldId) {
        this.fieldId = fieldId;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }
}
