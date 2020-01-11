package com.kid.kidswim.events;

import com.kid.kidswim.command.CourseBeginTimeInfo;

public class ShowCourseBeginTimeEvent {

    private CourseBeginTimeInfo courseBeginTimeInfo;

    public ShowCourseBeginTimeEvent(CourseBeginTimeInfo courseBeginTimeInfo) {
        this.courseBeginTimeInfo = courseBeginTimeInfo;
    }

    public CourseBeginTimeInfo getCourseBeginTimeInfo() {
        return courseBeginTimeInfo;
    }

    public void setCourseBeginTimeInfo(CourseBeginTimeInfo courseBeginTimeInfo) {
        this.courseBeginTimeInfo = courseBeginTimeInfo;
    }
}
