package com.kid.kidswim.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GroupDetailsSituationInfo {

    @JsonProperty("command")
    public SituationInfo situationInfo;


    public static class SituationInfo {
        @JsonProperty("unGroupLevelCorrespondCountCommandList")
        public List<GroupDetailsSituationInfo.SituationInfo.GroupLevelCorrespondCountInfo> groupList;

        @JsonProperty("codes")
        public List<String> codes;

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

        public List<String> getCodes() {
            return codes;
        }

        public void setCodes(List<String> codes) {
            this.codes = codes;
        }
    }

    public SituationInfo getSituationInfo() {
        return situationInfo;
    }

    public void setSituationInfo(SituationInfo situationInfo) {
        this.situationInfo = situationInfo;
    }
}
