package com.example.l2rebornx30rbcheckerdocker.service;

import java.time.LocalDateTime;

public class RaidBossServiceModel {
    private String rebornID;
    private boolean isAlive;
    private LocalDateTime respawnStart;
    private LocalDateTime respawnEnd;

    public RaidBossServiceModel() {
    }

    public String getRebornID() {
        return rebornID;
    }

    public RaidBossServiceModel setRebornID(String rebornID) {
        this.rebornID = rebornID;
        return this;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public RaidBossServiceModel setAlive(boolean alive) {
        isAlive = alive;
        return this;
    }

    public LocalDateTime getRespawnStart() {
        return respawnStart;
    }

    public RaidBossServiceModel setRespawnStart(LocalDateTime respawnStart) {
        this.respawnStart = respawnStart;
        return this;
    }

    public LocalDateTime getRespawnEnd() {
        return respawnEnd;
    }

    public RaidBossServiceModel setRespawnEnd(LocalDateTime respawnEnd) {
        this.respawnEnd = respawnEnd;
        return this;
    }
}
