package com.example.weighttogo.utils;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Unit tests for DateTimeConverter utility class.
 * Tests LocalDateTime and LocalDate conversions to/from SQLite-compatible strings.
 *
 * Following strict TDD: one failing test at a time.
 */
public class DateTimeConverterTest {

    /**
     * Test 1: toTimestamp with valid LocalDateTime returns ISO-8601 formatted string
     * Expected format: "yyyy-MM-dd HH:mm:ss"
     */
    @Test
    public void test_toTimestamp_withValidLocalDateTime_returnsISO8601String() {
        // ARRANGE
        LocalDateTime dateTime = LocalDateTime.of(2025, 12, 10, 14, 30, 45);
        String expected = "2025-12-10 14:30:45";

        // ACT
        String actual = DateTimeConverter.toTimestamp(dateTime);

        // ASSERT
        assertEquals("Timestamp should be in yyyy-MM-dd HH:mm:ss format", expected, actual);
    }

    /**
     * Test 2: fromTimestamp with valid string returns LocalDateTime
     */
    @Test
    public void test_fromTimestamp_withValidString_returnsLocalDateTime() {
        // ARRANGE
        String timestamp = "2025-12-10 14:30:45";
        LocalDateTime expected = LocalDateTime.of(2025, 12, 10, 14, 30, 45);

        // ACT
        LocalDateTime actual = DateTimeConverter.fromTimestamp(timestamp);

        // ASSERT
        assertEquals("Should parse timestamp string to LocalDateTime", expected, actual);
    }

    /**
     * Test 3: toDateString with valid LocalDate returns ISO-8601 formatted string
     * Expected format: "yyyy-MM-dd"
     */
    @Test
    public void test_toDateString_withValidLocalDate_returnsISO8601String() {
        // ARRANGE
        LocalDate date = LocalDate.of(2025, 12, 10);
        String expected = "2025-12-10";

        // ACT
        String actual = DateTimeConverter.toDateString(date);

        // ASSERT
        assertEquals("Date should be in yyyy-MM-dd format", expected, actual);
    }

    /**
     * Test 4: fromDateString with valid string returns LocalDate
     */
    @Test
    public void test_fromDateString_withValidString_returnsLocalDate() {
        // ARRANGE
        String dateString = "2025-12-10";
        LocalDate expected = LocalDate.of(2025, 12, 10);

        // ACT
        LocalDate actual = DateTimeConverter.fromDateString(dateString);

        // ASSERT
        assertEquals("Should parse date string to LocalDate", expected, actual);
    }

    /**
     * Test 5: Round-trip conversion preserves DateTime values
     * Tests that converting LocalDateTime -> String -> LocalDateTime returns the same value
     */
    @Test
    public void test_roundTrip_preservesDateTime() {
        // ARRANGE
        LocalDateTime original = LocalDateTime.of(2025, 12, 10, 14, 30, 45);

        // ACT
        String timestamp = DateTimeConverter.toTimestamp(original);
        LocalDateTime result = DateTimeConverter.fromTimestamp(timestamp);

        // ASSERT
        assertEquals("Round-trip conversion should preserve LocalDateTime", original, result);
    }
}