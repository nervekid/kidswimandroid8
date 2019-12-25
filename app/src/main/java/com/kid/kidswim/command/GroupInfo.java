package com.kid.kidswim.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GroupInfo {

    @JsonProperty("groupList")
    public List<GroupInfos> groupList;

    public static class GroupInfos {

        @JsonProperty("id")
        private String id;

        @JsonProperty("code")
        private String code ;

        @JsonProperty("coathId")
        private String coathId;

        @JsonProperty("courseAddress")
        private String courseAddress;

        @JsonProperty("groupBeginTime")
        private String groupBeginTime;

        @JsonProperty("groupEndTimeTime")
        private String groupEndTimeTime;

        @JsonProperty("groupLearnBeginTime")
        private String groupLearnBeginTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCoathId() {
            return coathId;
        }

        public void setCoathId(String coathId) {
            this.coathId = coathId;
        }

        public String getCourseAddress() {
            return courseAddress;
        }

        public void setCourseAddress(String courseAddress) {
            this.courseAddress = courseAddress;
        }

        public String getGroupBeginTime() {
            return groupBeginTime;
        }

        public void setGroupBeginTime(String groupBeginTime) {
            this.groupBeginTime = groupBeginTime;
        }

        public String getGroupEndTimeTime() {
            return groupEndTimeTime;
        }

        public void setGroupEndTimeTime(String groupEndTimeTime) {
            this.groupEndTimeTime = groupEndTimeTime;
        }

        public String getGroupLearnBeginTime() {
            return groupLearnBeginTime;
        }

        public void setGroupLearnBeginTime(String groupLearnBeginTime) {
            this.groupLearnBeginTime = groupLearnBeginTime;
        }
    }

    public List<GroupInfos> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<GroupInfos> groupList) {
        this.groupList = groupList;
    }
}
