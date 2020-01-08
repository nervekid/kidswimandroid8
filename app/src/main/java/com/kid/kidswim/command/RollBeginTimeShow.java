package com.kid.kidswim.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RollBeginTimeShow {

    @JsonProperty("beginTimeList")
    private List<BeginTimeListInfo> beginTimeList;

    public static class BeginTimeListInfo {
        @JsonProperty("showTime")
        private String showTime;

        @JsonProperty("selectTime")
        private String selectTime;

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public String getSelectTime() {
            return selectTime;
        }

        public void setSelectTime(String selectTime) {
            this.selectTime = selectTime;
        }
    }

    public List<BeginTimeListInfo> getBeginTimeList() {
        return beginTimeList;
    }

    public void setBeginTimeList(List<BeginTimeListInfo> beginTimeList) {
        this.beginTimeList = beginTimeList;
    }
}
