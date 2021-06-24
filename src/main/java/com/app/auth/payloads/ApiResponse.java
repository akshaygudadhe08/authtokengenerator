package com.app.auth.payloads;

public class ApiResponse {
    private Boolean success;
    private String message;

    public ApiResponse(){}
    public ApiResponse(Boolean success, String message){
        this.success = success;
        this.message = message;
    }
    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
