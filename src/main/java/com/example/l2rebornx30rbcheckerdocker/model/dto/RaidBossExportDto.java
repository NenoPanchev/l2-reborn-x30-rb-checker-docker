package com.example.l2rebornx30rbcheckerdocker.model.dto;

import com.google.gson.annotations.Expose;

public class RaidBossExportDto {
    @Expose
    private String name;
    @Expose
    private Integer level;
    @Expose
    private String rebornId;

    public RaidBossExportDto() {
    }

    public String getName() {
        return name;
    }

    public RaidBossExportDto setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public RaidBossExportDto setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public String getRebornId() {
        return rebornId;
    }

    public RaidBossExportDto setRebornId(String rebornId) {
        this.rebornId = rebornId;
        return this;
    }
}
