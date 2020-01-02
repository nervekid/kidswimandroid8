package com.kid.kidswim.events;

import com.kid.kidswim.command.SysSaleUserInfo;

public class InsertSaleUserDetailsShowEvent {
    private com.kid.kidswim.command.SysSaleUserInfo SysSaleUserInfo;

    public InsertSaleUserDetailsShowEvent(com.kid.kidswim.command.SysSaleUserInfo sysSaleUserInfo) {
        SysSaleUserInfo = sysSaleUserInfo;
    }

    public com.kid.kidswim.command.SysSaleUserInfo getSysSaleUserInfo() {
        return SysSaleUserInfo;
    }

    public void setSysSaleUserInfo(com.kid.kidswim.command.SysSaleUserInfo sysSaleUserInfo) {
        SysSaleUserInfo = sysSaleUserInfo;
    }
}
