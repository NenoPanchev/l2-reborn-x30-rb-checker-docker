package com.example.l2rebornx30rbcheckerdocker.service;

import com.example.l2rebornx30rbcheckerdocker.model.dto.RaidBossExportDto;
import com.example.l2rebornx30rbcheckerdocker.model.dto.RaidBossUpdateDto;
import com.example.l2rebornx30rbcheckerdocker.model.entity.RaidBoss;
import com.example.l2rebornx30rbcheckerdocker.model.view.RaidBossViewModel;
import com.example.l2rebornx30rbcheckerdocker.repository.RaidBossRepository;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.l2rebornx30rbcheckerdocker.constants.GlobalConstants.*;

@Service
public class RaidBossServiceImpl implements RaidBossService {
    private final RaidBossRepository raidBossRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public RaidBossServiceImpl(RaidBossRepository raidBossRepository, ModelMapper modelMapper, Gson gson) {
        this.raidBossRepository = raidBossRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public List<RaidBossViewModel> getAllRaidBosses() {
        return raidBossRepository
                .findAllByOrderByRespawnEnd()
                .stream()
                .map(entity -> modelMapper.map(entity, RaidBossViewModel.class)
                        .setRespawnStart(entity.getRespawnStart() == null ? "" : getTimeFrom(entity.getRespawnStart()))
                        .setRespawnEnd(entity.getRespawnEnd() == null ? "" : Arrays.stream(getTimeFrom(entity.getRespawnEnd()).split(" - ")).skip(1).findFirst().orElse(""))
                        .setRespawnStartTime(entity.getRespawnStart() == null ? null : entity.getRespawnStart()))
                .collect(Collectors.toList());
    }

    private String getTimeFrom(LocalDateTime localDateTime) {
        String[] str = localDateTime.toString().split("T");
        String hour = str[1].substring(0, 5);
        String[] date = str[0].split("-");
        String day = date[2];
        String month = date[1];
        return String.format("%s.%s  -  %s", day, month, hour);
    }

    @Override
    public void exportRaidBosses() throws IOException {
        Files.write(Paths.get(RAID_BOSSES),
                Collections.singleton(gson.toJson(raidBossRepository.findAll()
                        .stream()
                        .map(view -> modelMapper.map(view, RaidBossExportDto.class))
                        .collect(Collectors.toList()))),
                StandardCharsets.UTF_8);
    }

    @Override
    public void initialSeedRaidBosses() throws FileNotFoundException {
        if (raidBossRepository.count() == 0) {
            RaidBossExportDto[] dtos = gson.fromJson(new FileReader(RAID_BOSSES), RaidBossExportDto[].class);
            Arrays.stream(dtos)
                    .map(dto -> modelMapper.map(dto, RaidBoss.class)
                            .setAlive(true)
                            .setDropURL(String.format("https://interlude.wiki/db/npc/%s.html", dto.getRebornId()))
                            .setLocationURL(String.format("https://interlude.wiki/db/loc/%s.html", dto.getRebornId())))
                    .forEach(raidBossRepository::save);
        }
    }

    @Override
    public void update() throws IOException {
        List<RaidBoss> raidBosses = raidBossRepository.findAll();
        Instant now = Instant.now();
        long epochSeconds = now.getEpochSecond();
        URL url = new URL(JSON_URL + "?" + epochSeconds);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        int responseCode = http.getResponseCode();
        http.disconnect();

        if (responseCode != 200) {
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


        List<RaidBossUpdateDto> dtos = Arrays.stream(gson
                .fromJson(new InputStreamReader(url.openStream()), RaidBossUpdateDto[].class))
                .collect(Collectors.toList());

        List<RaidBossServiceModel> serviceModels = dtos
                .stream()
                .map(dto -> {
                    String startString = Arrays.stream(dto.getDate().split(" - ")).findFirst().orElse("");
                    LocalDateTime respawnStartUCT = LocalDateTime.parse(startString, formatter);
                    LocalDateTime respawnLocal = respawnStartUCT.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.of("Europe/Sofia")).toLocalDateTime();
                    LocalDateTime respawnEnd = respawnLocal.plusHours(1);
                    return modelMapper.map(dto, RaidBossServiceModel.class)
                            .setAlive(dto.getStatus().equals("1"))
                            .setRespawnStart(respawnLocal)
                            .setRespawnEnd(respawnEnd);
                })
                .collect(Collectors.toList());

        for (RaidBoss rb : raidBosses) {
            RaidBossServiceModel serviceModel = serviceModels.stream()
                    .filter(sm -> sm.getRebornID().equals(rb.getRebornID()))
                    .findAny()
                    .orElseThrow();
            if (rb.getName().equals(BARAKIEL)) {
                serviceModel.setRespawnEnd(serviceModel.getRespawnStart().plusMinutes(30));
            }

            if (!rb.isAlive() && serviceModel.isAlive()) {
                rb.setRespawnStart(null)
                        .setRespawnEnd(null)
                        .setAlive(true);
                raidBossRepository.save(rb);
                continue;
            }

            if (rb.isAlive() && !serviceModel.isAlive()) {
                rb.setRespawnStart(serviceModel.getRespawnStart())
                        .setAlive(false)
                        .setRespawnEnd(serviceModel.getRespawnEnd());
                raidBossRepository.save(rb);

                continue;
            }

            if (!rb.isAlive() && !serviceModel.isAlive()) {
                long hoursDifference = Math.abs(Duration.between(rb.getRespawnStart(), serviceModel.getRespawnStart()).toHours());
                if (hoursDifference != 0) {
                    rb.setRespawnStart(serviceModel.getRespawnStart());
                    rb.setRespawnEnd(serviceModel.getRespawnEnd());
                    rb.setAlive(false);
                    raidBossRepository.save(rb);
                }
            }
        }
    }
}
