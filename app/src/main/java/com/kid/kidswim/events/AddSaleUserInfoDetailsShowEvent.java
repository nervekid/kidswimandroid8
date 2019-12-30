package com.kid.kidswim.events;

import com.kid.kidswim.command.IdAndName;

public class AddSaleUserInfoDetailsShowEvent {

    private IdAndName idAndName;

    public IdAndName getIdAndName() {
        return idAndName;
    }

    public void setIdAndName(IdAndName idAndName) {
        this.idAndName = idAndName;
    }
}
