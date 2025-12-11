package com.example.weighttogo.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Model class representing a daily weight entry.
 * Corresponds to the daily_weights table in the database.
 */
public class WeightEntry {

    /** Primary key - unique identifier for weight entry */
    private long weightId;

    /** Foreign key reference to users table */
    private long userId;

    /** Weight measurement value (numeric) */
    private double weightValue;

    /** Unit of measurement: 'lbs' or 'kg' */
    @NonNull private String weightUnit;

    /** Date of weight entry (date only, no time component) */
    @NonNull private LocalDate weightDate;

    /** Optional user notes for this entry */
    @Nullable private String notes;

    /** Timestamp when entry was created */
    @NonNull private LocalDateTime createdAt;

    /** Timestamp when entry was last updated */
    @NonNull private LocalDateTime updatedAt;

    /** Soft delete flag - true if deleted, false if active */
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

    public LocalDate getWeightDate() {
        return weightDate;
    }

    public void setWeightDate(LocalDate weightDate) {
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

    /**
     * Equality based on weightId (primary key).
     * Note: This implementation assumes WeightEntry will not be subclassed.
     * Two entries are equal if they have the same non-zero weightId.
     * Uninitialized entries (weightId=0) are never equal to prevent false matches.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeightEntry)) return false;
        WeightEntry that = (WeightEntry) o;
        return weightId != 0 && weightId == that.weightId;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(weightId);
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
