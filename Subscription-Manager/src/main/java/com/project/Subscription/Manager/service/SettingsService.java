package com.project.Subscription.Manager.service;

import com.project.Subscription.Manager.entity.Settings;
import com.project.Subscription.Manager.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SettingsService {
    @Autowired
    private SettingsRepository repository;

    public Settings saveSettings(Settings settings) {

        List<Settings> current = repository.findAll();
        if (!current.isEmpty()) {
            Settings s = current.get(0);
            s.setMonthlyBudget(settings.getMonthlyBudget());
            s.setNotificationStart(settings.getNotificationStart());
            s.setNotificationEnd(settings.getNotificationEnd());
            return repository.save(s);
        }
        return repository.save(settings);
    }

    public Optional<Settings> getSettings() {
        List<Settings> current = repository.findAll();
        return current.isEmpty() ? Optional.empty() : Optional.of(current.get(0));
    }
}
