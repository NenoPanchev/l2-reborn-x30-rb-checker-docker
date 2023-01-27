package com.example.l2rebornx30rbcheckerdocker.web;

import com.example.l2rebornx30rbcheckerdocker.model.view.RaidBossViewModel;
import com.example.l2rebornx30rbcheckerdocker.service.RaidBossService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class HomeController {
    private final RaidBossService raidBossService;

    public HomeController(RaidBossService raidBossService) {
        this.raidBossService = raidBossService;
    }

    @GetMapping
    public String index(Model model) throws IOException {
        raidBossService.update();
        List<RaidBossViewModel> allRaidBosses = raidBossService.getAllRaidBosses();
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("allBosses", allRaidBosses);
        model.addAttribute("alive", allRaidBosses.stream().anyMatch(RaidBossViewModel::isAlive));
        model.addAttribute("now", now);
        return "index";
    }
}
