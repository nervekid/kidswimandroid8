package com.kid.kidswim.events;

public class JumpToAddGroupFragmentEvent {
    private String addressStr;

    private String groupBeginDateStr;

    private String learnBeginTimeStr;

    public JumpToAddGroupFragmentEvent(String addressStr, String groupBeginDateStr, String learnBeginTimeStr) {
        this.addressStr = addressStr;
        this.groupBeginDateStr = groupBeginDateStr;
        this.learnBeginTimeStr = learnBeginTimeStr;
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
}
