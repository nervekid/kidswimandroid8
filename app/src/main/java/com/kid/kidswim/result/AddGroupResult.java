package com.kid.kidswim.result;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddGroupResult {

    @JsonProperty("code")
    private String code;

    @JsonProperty("status")
    private String status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
