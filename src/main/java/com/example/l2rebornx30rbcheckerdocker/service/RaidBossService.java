package com.example.l2rebornx30rbcheckerdocker.service;

import com.example.l2rebornx30rbcheckerdocker.model.view.RaidBossViewModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface RaidBossService {
    List<RaidBossViewModel> getAllRaidBosses();

    void exportRaidBosses() throws IOException;

    void initialSeedRaidBosses() throws FileNotFoundException;

    void update() throws IOException;
}
