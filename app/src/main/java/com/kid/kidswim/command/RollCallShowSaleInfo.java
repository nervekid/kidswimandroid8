package com.kid.kidswim.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RollCallShowSaleInfo {

    @JsonProperty("size")
    private String size;

    @JsonProperty("status")
    private String status;

    @JsonProperty("rollCallShowList")
    private List<RollCallShowInfo> rollCallShowList;

    public static class RollCallShowInfo {

        @JsonProperty("courseDetailsId")
        private String courseDetailsId;

        @JsonProperty("studentId")
        private String studentId;

        @JsonProperty("studentName")
        private String studentName;

        @JsonProperty("courseLevel")
        private String courseLevel;

        public String getCourseDetailsId() {
            return courseDetailsId;
        }

        public String getStudentId() {
            return studentId;
        }

        public String getStudentName() {
            return studentName;
        }

        public String getCourseLevel() {
            return courseLevel;
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

    public List<RollCallShowInfo> getRollCallShowList() {
        return rollCallShowList;
    }

    public void setRollCallShowList(List<RollCallShowInfo> rollCallShowList) {
        this.rollCallShowList = rollCallShowList;
    }
}
