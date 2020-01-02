package com.kid.kidswim.events;

public class InsertRemoveSaleUserDetailsInfoEvent {

    private String id;

    public InsertRemoveSaleUserDetailsInfoEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
