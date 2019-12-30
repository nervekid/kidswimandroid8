package com.kid.kidswim.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SysSaleUserInfo {

    @JsonProperty("tpcSaleStudentCommandList")
    private List<SysSaleUserDetailsInfo> tpcSaleStudentCommandList;

    public static class SysSaleUserDetailsInfo {

        @JsonProperty("saleId")
        private String saleId;

        @JsonProperty("stuId")
        private String stuId;

        @JsonProperty("stuCode")
        private String stuCode;

        @JsonProperty("stuName")
        private String stuName;

        @JsonProperty("sexName")
        private String sexName;

        @JsonProperty("courseLevel")
        private String courseLevel;

        @JsonProperty("courseLevelValue")
        private String courseLevelValue;

        public String getSaleId() {
            return saleId;
        }

        public void setSaleId(String saleId) {
            this.saleId = saleId;
        }

        public String getStuId() {
            return stuId;
        }

        public void setStuId(String stuId) {
            this.stuId = stuId;
        }

        public String getStuCode() {
            return stuCode;
        }

        public void setStuCode(String stuCode) {
            this.stuCode = stuCode;
        }

        public String getStuName() {
            return stuName;
        }

        public void setStuName(String stuName) {
            this.stuName = stuName;
        }

        public String getSexName() {
            return sexName;
        }

        public void setSexName(String sexName) {
            this.sexName = sexName;
        }

        public String getCourseLevel() {
            return courseLevel;
        }

        public void setCourseLevel(String courseLevel) {
            this.courseLevel = courseLevel;
        }

        public String getCourseLevelValue() {
            return courseLevelValue;
        }

        public void setCourseLevelValue(String courseLevelValue) {
            this.courseLevelValue = courseLevelValue;
        }
    }

    public List<SysSaleUserDetailsInfo> getTpcSaleStudentCommandList() {
        return tpcSaleStudentCommandList;
    }

    public void setTpcSaleStudentCommandList(List<SysSaleUserDetailsInfo> tpcSaleStudentCommandList) {
        this.tpcSaleStudentCommandList = tpcSaleStudentCommandList;
    }
}
