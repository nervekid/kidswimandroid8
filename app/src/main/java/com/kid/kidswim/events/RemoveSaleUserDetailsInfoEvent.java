package com.kid.kidswim.events;

public class RemoveSaleUserDetailsInfoEvent {

    private String id;

    public RemoveSaleUserDetailsInfoEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
