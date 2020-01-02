package com.kid.kidswim.events;

public class JumpToAddGroupFragmentEvent {
    private String addressStr;

    private String groupBeginDateStr;

    private String learnBeginTimeStr;

    private String addressName;

    private String learnBeginTimeStrShow;

    public JumpToAddGroupFragmentEvent(String addressStr, String groupBeginDateStr, String learnBeginTimeStr, String addressName, String learnBeginTimeStrShow) {
        this.addressStr = addressStr;
        this.groupBeginDateStr = groupBeginDateStr;
        this.learnBeginTimeStr = learnBeginTimeStr;
        this.addressName = addressName;
        this.learnBeginTimeStrShow = learnBeginTimeStrShow;
    }

    public String getAddressStr() {
        return addressStr;
    }

    public void setAddressStr(String addressStr) {
        this.addressStr = addressStr;
    }

    public String getGroupBeginDateStr() {
        return groupBeginDateStr;
    }

    public void setGroupBeginDateStr(String groupBeginDateStr) {
        this.groupBeginDateStr = groupBeginDateStr;
    }

    public String getLearnBeginTimeStr() {
        return learnBeginTimeStr;
    }

    public void setLearnBeginTimeStr(String learnBeginTimeStr) {
        this.learnBeginTimeStr = learnBeginTimeStr;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getLearnBeginTimeStrShow() {
        return learnBeginTimeStrShow;
    }

    public void setLearnBeginTimeStrShow(String learnBeginTimeStrShow) {
        this.learnBeginTimeStrShow = learnBeginTimeStrShow;
    }
}
