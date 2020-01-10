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

        @JsonProperty("rollCallStatusFlag")
        private String rollCallStatusFlag;

        @JsonProperty("rollCallStatusName")
        private String rollCallStatusName;

        public String getCourseDetailsId() {
            return courseDetailsId;
        }

        public void setCourseDetailsId(String courseDetailsId) {
            this.courseDetailsId = courseDetailsId;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getCourseLevel() {
            return courseLevel;
        }

        public void setCourseLevel(String courseLevel) {
            this.courseLevel = courseLevel;
        }

        public String getRollCallStatusFlag() {
            return rollCallStatusFlag;
        }

        public void setRollCallStatusFlag(String rollCallStatusFlag) {
            this.rollCallStatusFlag = rollCallStatusFlag;
        }

        public String getRollCallStatusName() {
            return rollCallStatusName;
        }

        public void setRollCallStatusName(String rollCallStatusName) {
            this.rollCallStatusName = rollCallStatusName;
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
