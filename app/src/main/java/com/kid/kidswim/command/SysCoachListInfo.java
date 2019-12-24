package com.kid.kidswim.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class SysCoachListInfo {

    @JsonProperty("sysBaseCoachs")
    public List<SysBaseCoachs> sysBaseCoachslist;

    public static class SysBaseCoachs {
        @JsonProperty("id")
        private String id;

        @JsonProperty("code")
        private String code;

        @JsonProperty("nameCn")
        private String nameCn;

        @JsonProperty("nameEn")
        private String nameEn;

        @JsonProperty("sex")
        private String sex;

        @JsonProperty("phone")
        private String phone;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getNameCn() {
            return nameCn;
        }

        public void setNameCn(String nameCn) {
            this.nameCn = nameCn;
        }

        public String getNameEn() {
            return nameEn;
        }

        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public List<SysBaseCoachs> getSysBaseCoachslist() {
        return sysBaseCoachslist;
    }

    public void setSysBaseCoachslist(List<SysBaseCoachs> sysBaseCoachslist) {
        this.sysBaseCoachslist = sysBaseCoachslist;
    }
}
