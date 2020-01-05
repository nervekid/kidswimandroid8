package com.kid.kidswim.events;

import com.kid.kidswim.command.RollCallShowSaleInfo;

public class RollCallShowEvent {
    private RollCallShowSaleInfo rollCallShowSaleInfo;

    public RollCallShowEvent(RollCallShowSaleInfo rollCallShowSaleInfo) {
        this.rollCallShowSaleInfo = rollCallShowSaleInfo;
    }

    public RollCallShowSaleInfo getRollCallShowSaleInfo() {
        return rollCallShowSaleInfo;
    }

    public void setRollCallShowSaleInfo(RollCallShowSaleInfo rollCallShowSaleInfo) {
        this.rollCallShowSaleInfo = rollCallShowSaleInfo;
    }
}
