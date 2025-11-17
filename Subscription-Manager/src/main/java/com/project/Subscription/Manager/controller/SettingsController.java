package com.project.Subscription.Manager.controller;

import com.project.Subscription.Manager.entity.Settings;
import com.project.Subscription.Manager.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    @Autowired
    private SettingsService service;

    @PostMapping
    public ResponseEntity<Settings> saveSettings(@RequestBody Settings settings) {
        Settings saved = service.saveSettings(settings);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Settings> getSettings() {
        Optional<Settings> settings = service.getSettings();
        return settings.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
