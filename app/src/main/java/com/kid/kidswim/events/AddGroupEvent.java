package com.kid.kidswim.events;

import com.kid.kidswim.command.SysCoachListInfo;

public class AddGroupEvent {
    public SysCoachListInfo sysCoachListInfo;

    public AddGroupEvent(SysCoachListInfo sysCoachListInfo) {
        this.sysCoachListInfo = sysCoachListInfo;
    }

    public SysCoachListInfo getSysCoachListInfo() {
        return sysCoachListInfo;
    }

    public void setSysCoachListInfo(SysCoachListInfo sysCoachListInfo) {
        this.sysCoachListInfo = sysCoachListInfo;
    }
}
