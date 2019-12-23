package com.kid.kidswim.command;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserInfo {

    @JsonProperty("status")
    private String status;

    @JsonProperty("user")
    private User user;

    public static class User {
        @JsonProperty("id")
        private String id;

        @JsonProperty("loginName")
        private String loginName;

        @JsonProperty("userName")
        private String userName;

        @JsonProperty("phone")
        private String phone;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
