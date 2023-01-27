package com.example.l2rebornx30rbcheckerdocker.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "raidbosses")
public class RaidBoss extends BaseEntity{
    private String name;
    private Integer level;
    private boolean isAlive;
    private LocalDateTime respawnStart;
    private LocalDateTime respawnEnd;
    private String rebornID;
    private String dropURL;
    private String locationURL;

    public RaidBoss() {
    }

    @Column
    public String getName() {
        return name;
    }

    public RaidBoss setName(String name) {
        this.name = name;
        return this;
    }

    @Column
    public Integer getLevel() {
        return level;
    }

    public RaidBoss setLevel(Integer level) {
        this.level = level;
        return this;
    }

    @Column
    public boolean isAlive() {
        return isAlive;
    }

    public RaidBoss setAlive(boolean alive) {
        isAlive = alive;
        return this;
    }

    @Column
    public LocalDateTime getRespawnStart() {
        return respawnStart;
    }

    public RaidBoss setRespawnStart(LocalDateTime respawnStart) {
        this.respawnStart = respawnStart;
        return this;
    }

    @Column
    public LocalDateTime getRespawnEnd() {
        return respawnEnd;
    }

    public RaidBoss setRespawnEnd(LocalDateTime respawnEnd) {
        this.respawnEnd = respawnEnd;
        return this;
    }

    public String getRebornID() {
        return rebornID;
    }

    public RaidBoss setRebornID(String rebornID) {
        this.rebornID = rebornID;
        return this;
    }

    public String getDropURL() {
        return dropURL;
    }

    public RaidBoss setDropURL(String dropURL) {
        this.dropURL = dropURL;
        return this;
    }

    public String getLocationURL() {
        return locationURL;
    }

    public RaidBoss setLocationURL(String locationURL) {
        this.locationURL = locationURL;
        return this;
    }
}
