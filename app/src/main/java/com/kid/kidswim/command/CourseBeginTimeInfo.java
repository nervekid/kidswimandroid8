package com.kid.kidswim.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CourseBeginTimeInfo {

    @JsonProperty("size")
    private String size;

    @JsonProperty("status")
    private String status;

    @JsonProperty("infoList")
    private List<CourseBeginTimeDetailsInfo> infoList ;

    public static class CourseBeginTimeDetailsInfo {

        @JsonProperty("beginTimeStr")
        private String beginTimeStr;

        private String showBeginTimeStr;

        public String getBeginTimeStr() {
            return beginTimeStr;
        }

        public void setBeginTimeStr(String beginTimeStr) {
            this.beginTimeStr = beginTimeStr;
        }

        public String getShowBeginTimeStr() {
            return showBeginTimeStr;
        }

        public void setShowBeginTimeStr(String showBeginTimeStr) {
            this.showBeginTimeStr = showBeginTimeStr;
        }
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CourseBeginTimeDetailsInfo> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<CourseBeginTimeDetailsInfo> infoList) {
        this.infoList = infoList;
    }
}
