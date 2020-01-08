package com.kid.kidswim.events;

import com.kid.kidswim.command.RollBeginTimeShow;

public class RollShowBeginTimeEvent {

    private RollBeginTimeShow rollBeginTimeShow;

    public RollShowBeginTimeEvent(RollBeginTimeShow rollBeginTimeShow) {
        this.rollBeginTimeShow = rollBeginTimeShow;
    }

    public RollBeginTimeShow getRollBeginTimeShow() {
        return rollBeginTimeShow;
    }

    public void setRollBeginTimeShow(RollBeginTimeShow rollBeginTimeShow) {
        this.rollBeginTimeShow = rollBeginTimeShow;
    }
}
