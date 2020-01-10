package com.kid.kidswim.enums;
/**
 * @author lyb
 * @Description: 泳会系统APP枚举类
 * @date 2019-12-23
 */
public class KidswimAttEnum {

    public enum successOrFail {

        成功("1"),
        失败("0");

        private String name;

        private successOrFail(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum rollCallStatusFlag {

        未点名("0"),
        出席("1"),
        缺席("2"),
        请假("3"),
        事故("4");

        private String name;

        private rollCallStatusFlag(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public enum kidswimFlag {

        課程地址("course_addrese_flag"),
        遇溺地點("drowned_address_flag"),
        收費標準("cost_standard_flag"),
        付款方式("pay_type"),
        課程對應級別("course_level"),
        點名狀態("rollCall_status_flag"),
        性別("sex_flag"),
        證書類別("certificate_categor"),
        點名類別("roll_call_status_flag");
        private String name;

        private kidswimFlag(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
