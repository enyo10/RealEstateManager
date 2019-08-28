package com.openclassrooms.realestatemanager.models;

public enum Status {

        SOLDED("SOLD"),
        UNSOLDED("UNSOLD");


        private String code;

        Status(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

}
