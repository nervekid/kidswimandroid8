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
}
