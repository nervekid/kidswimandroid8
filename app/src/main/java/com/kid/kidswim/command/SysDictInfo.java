package com.kid.kidswim.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SysDictInfo {

    @JsonProperty("dictList")
    List<dictList> dictLists;

    public static class dictList {
        @JsonProperty("id")
        public String id;

        @JsonProperty("value")
        public String value;

        @JsonProperty("label")
        public String label;

        @JsonProperty("type")
        public String type;

        @JsonProperty("description")
        public String description;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public List<dictList> getDictLists() {
        return dictLists;
    }

    public void setDictLists(List<dictList> dictLists) {
        this.dictLists = dictLists;
    }
}
