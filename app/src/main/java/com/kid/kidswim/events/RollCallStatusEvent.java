package com.kid.kidswim.events;

import android.widget.Button;

import com.kid.kidswim.command.RollCallShowSaleInfo;
import com.kid.kidswim.command.SysDictInfo;

public class RollCallStatusEvent {

    private RollCallShowSaleInfo.RollCallShowInfo rollCallShowInfo;

    private SysDictInfo sysDictInfo;

    private Button button;

    public RollCallStatusEvent(RollCallShowSaleInfo.RollCallShowInfo rollCallShowInfo, SysDictInfo sysDictInfo, Button button) {
        this.rollCallShowInfo = rollCallShowInfo;
        this.sysDictInfo = sysDictInfo;
        this.button = button;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public SysDictInfo getSysDictInfo() {
        return sysDictInfo;
    }

    public void setSysDictInfo(SysDictInfo sysDictInfo) {
        this.sysDictInfo = sysDictInfo;
    }

    public RollCallShowSaleInfo.RollCallShowInfo getRollCallShowInfo() {
        return rollCallShowInfo;
    }

    public void setRollCallShowInfo(RollCallShowSaleInfo.RollCallShowInfo rollCallShowInfo) {
        this.rollCallShowInfo = rollCallShowInfo;
    }
}
