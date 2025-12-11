package com.example.weighttogo.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalDateTime;

/**
 * Model class representing a weight goal.
 * Corresponds to the goal_weights table in the database.
 */
public class GoalWeight {

    private long goalId;
    private long userId;
    private double goalWeight;
    @NonNull private String goalUnit;
    private double startWeight;
    @Nullable private LocalDateTime targetDate;
    private boolean isAchieved;
    @Nullable private LocalDateTime achievedDate;
    @NonNull private LocalDateTime createdAt;
    @NonNull private LocalDateTime updatedAt;
    private boolean isActive;

    /**
     * Default constructor.
     */
    public GoalWeight() {
    }

    public long getGoalId() {
        return goalId;
    }

    public void setGoalId(long goalId) {
        this.goalId = goalId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getGoalWeight() {
        return goalWeight;
    }

    public void setGoalWeight(double goalWeight) {
        this.goalWeight = goalWeight;
    }

    public String getGoalUnit() {
        return goalUnit;
    }

    public void setGoalUnit(String goalUnit) {
        this.goalUnit = goalUnit;
    }

    public double getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(double startWeight) {
        this.startWeight = startWeight;
    }

    public LocalDateTime getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDateTime targetDate) {
        this.targetDate = targetDate;
    }

    public boolean getIsAchieved() {
        return isAchieved;
    }

    public void setIsAchieved(boolean isAchieved) {
        this.isAchieved = isAchieved;
    }

    public LocalDateTime getAchievedDate() {
        return achievedDate;
    }

    public void setAchievedDate(LocalDateTime achievedDate) {
        this.achievedDate = achievedDate;
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

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "GoalWeight{" +
                "goalId=" + goalId +
                ", userId=" + userId +
                ", goalWeight=" + goalWeight +
                ", goalUnit='" + goalUnit + '\'' +
                ", startWeight=" + startWeight +
                ", targetDate=" + targetDate +
                ", isAchieved=" + isAchieved +
                ", achievedDate=" + achievedDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isActive=" + isActive +
                '}';
    }
}
