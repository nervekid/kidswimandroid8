package com.kid.kidswim.events;

import com.kid.kidswim.command.GroupDetailsSituationInfo;
import com.kid.kidswim.command.GroupInfo;
import com.kid.kidswim.command.SysDictInfo;

public class GroupDetailsShowEvent {

    public GroupDetailsSituationInfo groupDetailsSituationInfo;

    public GroupDetailsShowEvent(GroupDetailsSituationInfo groupDetailsSituationInfo) {
        this.groupDetailsSituationInfo = groupDetailsSituationInfo;
    }

    public GroupDetailsSituationInfo getGroupDetailsSituationInfo() {
        return groupDetailsSituationInfo;
    }

    public void setGroupDetailsSituationInfo(GroupDetailsSituationInfo groupDetailsSituationInfo) {
        this.groupDetailsSituationInfo = groupDetailsSituationInfo;
    }
}
