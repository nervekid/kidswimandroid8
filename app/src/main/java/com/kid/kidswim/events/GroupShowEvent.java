package com.kid.kidswim.events;

import com.kid.kidswim.command.GroupInfo;
import com.kid.kidswim.command.SysDictInfo;

public class GroupShowEvent {

    public GroupInfo groupInfo;

    public GroupShowEvent(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
    }

    public GroupInfo getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
    }
}
