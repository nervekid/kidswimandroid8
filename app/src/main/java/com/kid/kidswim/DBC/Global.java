package com.kid.kidswim.DBC;

public class Global {

    private static String server = "http://120.79.137.103:10080/kidswim";
    private static String local = "http://192.168.1.100:8080/ROOT";

    public enum GlobalConfiguration {

        服务器链接(local);

        private String name;

        private GlobalConfiguration(String name) {
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
