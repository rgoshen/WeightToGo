package com.example.weighttogo.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalDateTime;

/**
 * Model class representing a daily weight entry.
 * Corresponds to the daily_weights table in the database.
 */
public class WeightEntry {

    private long weightId;
    private long userId;
    private double weightValue;
    @NonNull private String weightUnit;
    @NonNull private LocalDateTime weightDate;
    @Nullable private String notes;
    @NonNull private LocalDateTime createdAt;
    @NonNull private LocalDateTime updatedAt;
    private boolean isDeleted;

    /**
     * Default constructor.
     */
    public WeightEntry() {
    }

    public long getWeightId() {
        return weightId;
    }

    public void setWeightId(long weightId) {
        this.weightId = weightId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(double weightValue) {
        this.weightValue = weightValue;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public LocalDateTime getWeightDate() {
        return weightDate;
    }

    public void setWeightDate(LocalDateTime weightDate) {
        this.weightDate = weightDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "WeightEntry{" +
                "weightId=" + weightId +
                ", userId=" + userId +
                ", weightValue=" + weightValue +
                ", weightUnit='" + weightUnit + '\'' +
                ", weightDate=" + weightDate +
                ", notes='" + notes + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
