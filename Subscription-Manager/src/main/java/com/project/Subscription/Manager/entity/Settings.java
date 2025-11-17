package com.project.Subscription.Manager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double monthlyBudget;
    private String notificationStart; // e.g., "22:00"
    private String notificationEnd;   // e.g., "07:00"

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getMonthlyBudget() { return monthlyBudget; }
    public void setMonthlyBudget(Double monthlyBudget) { this.monthlyBudget = monthlyBudget; }

    public String getNotificationStart() { return notificationStart; }
    public void setNotificationStart(String notificationStart) { this.notificationStart = notificationStart; }

    public String getNotificationEnd() { return notificationEnd; }
    public void setNotificationEnd(String notificationEnd) { this.notificationEnd = notificationEnd; }
}
