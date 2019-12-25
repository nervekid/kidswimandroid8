package com.kid.kidswim.events;

import com.kid.kidswim.command.SysDictInfo;

public class AddGroupLevelEvent {

    public SysDictInfo sysDictInfo;

    public AddGroupLevelEvent(SysDictInfo sysDictInfo) {
        this.sysDictInfo = sysDictInfo;
    }

    public SysDictInfo getSysDictInfo() {
        return sysDictInfo;
    }

    public void setSysDictInfo(SysDictInfo sysDictInfo) {
        this.sysDictInfo = sysDictInfo;
    }
}
