package com.example;

public class ServiceImpl implements Service {
    private String version;

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String getVersion() {
        return version;
    }
}
