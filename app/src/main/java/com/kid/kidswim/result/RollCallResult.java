package com.kid.kidswim.result;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RollCallResult {
    @JsonProperty("rollCallNum")
    private String rollCallNum;

    @JsonProperty("status")
    private String status;

    public String getRollCallNum() {
        return rollCallNum;
    }

    public void setRollCallNum(String rollCallNum) {
        this.rollCallNum = rollCallNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
