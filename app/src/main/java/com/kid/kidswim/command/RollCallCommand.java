package com.kid.kidswim.command;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RollCallCommand {

    public RollCallCommand(String courseDetailsId, String studentId, String status) {
        this.courseDetailsId = courseDetailsId;
        this.studentId = studentId;
        this.status = status;
    }

    @JsonProperty("courseDetailsId")
    private String courseDetailsId;

    @JsonProperty("studentId")
    private String studentId;

    @JsonProperty("rollCallStatusFlag")
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
