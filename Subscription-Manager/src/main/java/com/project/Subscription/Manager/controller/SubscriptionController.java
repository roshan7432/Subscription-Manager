package com.project.Subscription.Manager.controller;

import com.project.Subscription.Manager.entity.Subscription;
import com.project.Subscription.Manager.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    @Autowired
    private SubscriptionService service;

    @PostMapping
    public ResponseEntity<Subscription> createSubscription(@RequestBody Subscription subscription) {
        Subscription created = service.createSubscription(subscription);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        List<Subscription> subscriptions = service.getAllSubscriptions();
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        service.deleteSubscription(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subscription> updateSubscription(@PathVariable Long id, @RequestBody Subscription subscription) {
        try {
            Subscription updated = service.updateSubscription(id, subscription);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/analytics/monthly")
    public ResponseEntity<Map<Integer, Double>> getMonthlySpending() {
        Map<Integer, Double> monthlySpending = service.getMonthlySpending();
        return new ResponseEntity<>(monthlySpending, HttpStatus.OK);
    }
    @GetMapping("/notifications/upcoming")
    public ResponseEntity<List<Subscription>> getUpcomingPayments(@RequestParam(defaultValue = "7") int daysAhead) {
        List<Subscription> upcoming = service.getUpcomingPayments(daysAhead);
        return new ResponseEntity<>(upcoming, HttpStatus.OK);
    }


    @GetMapping("/export/csv")
    public ResponseEntity<byte[]> exportCsv() throws IOException {
        List<Subscription> subscriptions = service.getAllSubscriptions();
        StringBuilder sb = new StringBuilder();
        sb.append("Name,Amount,BillingDate\n");
        for (Subscription s : subscriptions) {
            sb.append(s.getName()).append(",")
                    .append(s.getAmount()).append(",")
                    .append(s.getBillingDate()).append("\n");
        }
        byte[] csvBytes = sb.toString().getBytes(StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=subscriptions.csv");
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }


}
