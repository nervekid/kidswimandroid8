package com.kid.kidswim.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GroupDetailsSituationInfo {

    @JsonProperty("command")
    public SituationInfo situationInfo;


    public static class SituationInfo {
        @JsonProperty("unGroupLevelCorrespondCountCommandList")
        public List<GroupDetailsSituationInfo.SituationInfo.GroupLevelCorrespondCountInfo> groupList;

        @JsonProperty("groupDetailsInfos")
        public List<GroupDetailsSituationInfo.SituationInfo.GroupDetailsInfos> groupDetailsInfos;

        public static class GroupDetailsInfos {
            @JsonProperty("code")
            private String code;

            @JsonProperty("codeAndNumShow")
            private String codeAndNumShow;

            @JsonProperty("courseAddress")
            private String courseAddress;

            @JsonProperty("groupBeginTime")
            private String groupBeginTime;

            @JsonProperty("groupBeginTimeStr")
            private String groupBeginTimeStr;

            @JsonProperty("groupLearnBeginTime")
            private String groupLearnBeginTime;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getCodeAndNumShow() {
                return codeAndNumShow;
            }

            public void setCodeAndNumShow(String codeAndNumShow) {
                this.codeAndNumShow = codeAndNumShow;
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

            public String getGroupBeginTimeStr() {
                return groupBeginTimeStr;
            }

            public void setGroupBeginTimeStr(String groupBeginTimeStr) {
                this.groupBeginTimeStr = groupBeginTimeStr;
            }

            public String getGroupLearnBeginTime() {
                return groupLearnBeginTime;
            }

            public void setGroupLearnBeginTime(String groupLearnBeginTime) {
                this.groupLearnBeginTime = groupLearnBeginTime;
            }
        }

        public static class GroupLevelCorrespondCountInfo {
            @JsonProperty("leavel")
            private String leavel;

            @JsonProperty("countNum")
            private String countNum;

            public String getLeavel() {
                return leavel;
            }

            public void setLeavel(String leavel) {
                this.leavel = leavel;
            }

            public String getCountNum() {
                return countNum;
            }

            public void setCountNum(String countNum) {
                this.countNum = countNum;
            }
        }

        public List<GroupLevelCorrespondCountInfo> getGroupList() {
            return groupList;
        }

        public void setGroupList(List<GroupLevelCorrespondCountInfo> groupList) {
            this.groupList = groupList;
        }

        public List<GroupDetailsInfos> getGroupDetailsInfos() {
            return groupDetailsInfos;
        }

        public void setGroupDetailsInfos(List<GroupDetailsInfos> groupDetailsInfos) {
            this.groupDetailsInfos = groupDetailsInfos;
        }
    }

    public SituationInfo getSituationInfo() {
        return situationInfo;
    }

    public void setSituationInfo(SituationInfo situationInfo) {
        this.situationInfo = situationInfo;
    }
}
