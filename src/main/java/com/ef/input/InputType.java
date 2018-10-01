package com.ef.input;

public enum InputType {

    ACCESS_LOG("--accesslog="), START_DATE("--startDate="), DURATION("--duration="), THRESHOLD("--threshold=");

    private String inputName;

    InputType(String inputName){
        this.inputName = inputName;
    }

    public String getInputName() {
        return inputName;
    }
}
