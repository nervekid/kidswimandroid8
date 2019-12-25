package com.kid.kidswim.events;

import com.kid.kidswim.command.SysCoachListInfo;
import com.kid.kidswim.command.SysDictInfo;

public class AddGroupAddressEvent {

    public SysDictInfo sysDictInfo;

    public AddGroupAddressEvent(SysDictInfo sysDictInfo) {
        this.sysDictInfo = sysDictInfo;
    }

    public SysDictInfo getSysDictInfo() {
        return sysDictInfo;
    }

    public void setSysDictInfo(SysDictInfo sysDictInfo) {
        this.sysDictInfo = sysDictInfo;
    }
}
