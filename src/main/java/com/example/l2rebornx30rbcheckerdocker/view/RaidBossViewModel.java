package com.example.l2rebornx30rbcheckerdocker.view;

import com.example.l2rebornx30rbcheckerdocker.constants.GlobalConstants;

import java.time.Duration;
import java.time.LocalDateTime;

public class RaidBossViewModel {
    private Long id;
    private String name;
    private Integer level;
    private boolean isAlive;
    private String respawnStart;
    private String respawnEnd;
    private LocalDateTime respawnStartTime;
    private String rebornID;
    private String dropURL;
    private String locationURL;

    public RaidBossViewModel() {
    }

    public Long getId() {
        return id;
    }

    public RaidBossViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RaidBossViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public RaidBossViewModel setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public RaidBossViewModel setAlive(boolean alive) {
        isAlive = alive;
        return this;
    }

    public String getRespawnStart() {
        return respawnStart;
    }

    public RaidBossViewModel setRespawnStart(String respawnStart) {
        this.respawnStart = respawnStart;
        return this;
    }

    public String getRespawnEnd() {
        return respawnEnd;
    }

    public RaidBossViewModel setRespawnEnd(String respawnEnd) {
        this.respawnEnd = respawnEnd;
        return this;
    }

    public LocalDateTime getRespawnStartTime() {
        return respawnStartTime;
    }

    public RaidBossViewModel setRespawnStartTime(LocalDateTime respawnStartTime) {
        this.respawnStartTime = respawnStartTime;
        return this;
    }

    public String getRebornID() {
        return rebornID;
    }

    public RaidBossViewModel setRebornID(String rebornID) {
        this.rebornID = rebornID;
        return this;
    }

    public String getDropURL() {
        return dropURL;
    }

    public RaidBossViewModel setDropURL(String dropURL) {
        this.dropURL = dropURL;
        return this;
    }

    public String getLocationURL() {
        return locationURL;
    }

    public RaidBossViewModel setLocationURL(String locationURL) {
        this.locationURL = locationURL;
        return this;
    }

    public Long getTimeDiffFromNowToRespawnStart(LocalDateTime now) {
        if (respawnStartTime == null) {
            return null;
        }
        Long minutesDifference = Math.abs(Duration.between(respawnStartTime, now).toMinutes());
        return minutesDifference;
    }

    public String getWidthPercentage(LocalDateTime now) {
        Long totalMinutesSpawn = 60L;
        if (name.equals(GlobalConstants.BARAKIEL)) {
            totalMinutesSpawn = 30L;
        }
        Long timeDiff = getTimeDiffFromNowToRespawnStart(now);
        if (timeDiff == null) {
            return null;
        }
        double percentage = 100.0 * timeDiff / totalMinutesSpawn;
        StringBuilder sb = new StringBuilder(String.format("width: %.2f%%", percentage).replace(",", "."));
        return sb.toString();
    }

}
