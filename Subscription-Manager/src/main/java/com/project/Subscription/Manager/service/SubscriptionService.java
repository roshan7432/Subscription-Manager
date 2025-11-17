package com.project.Subscription.Manager.service;

import com.project.Subscription.Manager.entity.Subscription;
import com.project.Subscription.Manager.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository repository;

    public Subscription createSubscription(Subscription subscription) {
        return repository.save(subscription);
    }

    public List<Subscription> getAllSubscriptions() {
        return repository.findAll();
    }

    public Optional<Subscription> getSubscriptionById(Long id) {
        return repository.findById(id);
    }

    public void deleteSubscription(Long id) {
        repository.deleteById(id);
    }

    public Subscription updateSubscription(Long id, Subscription newSubscription) {
        return repository.findById(id).map(subscription -> {
            subscription.setName(newSubscription.getName());
            subscription.setAmount(newSubscription.getAmount());
            subscription.setBillingDate(newSubscription.getBillingDate());
            return repository.save(subscription);
        }).orElseThrow(() -> new RuntimeException("Subscription not found"));
    }
    public Map<Integer, Double> getMonthlySpending() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(
                        s -> s.getBillingDate().getMonthValue(),
                        Collectors.summingDouble(Subscription::getAmount)
                ));
    }


    public List<Subscription> getUpcomingPayments(int daysAhead)
    {
        LocalDate today = LocalDate.now();
        LocalDate limitDate = today.plusDays(daysAhead);
        return repository.findAll().stream()
                .filter(s -> {
                    LocalDate billing = s.getBillingDate();
                    return (billing.isAfter(today.minusDays(1)) && billing.isBefore(limitDate.plusDays(1)));
                }).collect(Collectors.toList());
    }

}
