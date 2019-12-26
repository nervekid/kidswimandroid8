package com.kid.kidswim.events;

public class JumpToGalleryFragmentEvent {

    private String code;

    public JumpToGalleryFragmentEvent(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
