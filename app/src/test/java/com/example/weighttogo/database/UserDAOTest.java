package com.example.weighttogo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.weighttogo.models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Unit tests for UserDAO following strict TDD.
 * Tests CRUD operations for User model.
 *
 * TDD Approach: Write ONE failing test at a time, implement minimal code to pass,
 * then move to next test.
 */
@RunWith(RobolectricTestRunner.class)
public class UserDAOTest {

    private Context context;
    private WeighToGoDBHelper dbHelper;
    private UserDAO userDAO;
    private SQLiteDatabase db;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.getApplication();
        dbHelper = WeighToGoDBHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
        userDAO = new UserDAO(dbHelper);
    }

    @After
    public void tearDown() {
        try {
            if (db != null && db.isOpen()) {
                db.close();
            }
            if (dbHelper != null) {
                dbHelper.close();
            }
        } finally {
            dbHelper = null;
            userDAO = null;
            context.deleteDatabase("weigh_to_go.db");
            WeighToGoDBHelper.resetInstance();
        }
    }

    /**
     * Test 1: insertUser() should insert a valid user and return user ID > 0
     */
    @Test
    public void test_insertUser_withValidData_returnsUserId() {
        // ARRANGE
        User user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("hashed_password_123");
        user.setSalt("random_salt_456");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setIsActive(true);

        // ACT
        long userId = userDAO.insertUser(user);

        // ASSERT
        assertTrue("User ID should be greater than 0", userId > 0);
        user.setUserId(userId);

        // Verify user was actually inserted by querying database
        User retrievedUser = userDAO.getUserById(userId);
        assertNotNull("Retrieved user should not be null", retrievedUser);
        assertEquals("Username should match", "testuser", retrievedUser.getUsername());
    }

    /**
     * Test 2: getUserByUsername() should retrieve user by username
     */
    @Test
    public void test_getUserByUsername_withExistingUser_returnsUser() {
        // ARRANGE - Insert a user first
        User user = new User();
        user.setUsername("johndoe");
        user.setPasswordHash("hashed_pass");
        user.setSalt("salt123");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setIsActive(true);
        long userId = userDAO.insertUser(user);
        user.setUserId(userId);

        // ACT
        User retrievedUser = userDAO.getUserByUsername("johndoe");

        // ASSERT
        assertNotNull("User should be found", retrievedUser);
        assertEquals("User ID should match", userId, retrievedUser.getUserId());
        assertEquals("Username should match", "johndoe", retrievedUser.getUsername());
    }

    /**
     * Test 3: getUserByUsername() returns null for non-existent username
     */
    @Test
    public void test_getUserByUsername_withNonExistentUser_returnsNull() {
        // ACT
        User user = userDAO.getUserByUsername("nonexistent");

        // ASSERT
        assertNull("Should return null for non-existent user", user);
    }

    /**
     * Test 4: usernameExists() returns true for existing username
     */
    @Test
    public void test_usernameExists_withExistingUsername_returnsTrue() {
        // ARRANGE
        User user = new User();
        user.setUsername("existinguser");
        user.setPasswordHash("hash");
        user.setSalt("salt");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setIsActive(true);
        userDAO.insertUser(user);

        // ACT
        boolean exists = userDAO.usernameExists("existinguser");

        // ASSERT
        assertTrue("Username should exist", exists);
    }

    /**
     * Test 5: usernameExists() returns false for non-existent username
     */
    @Test
    public void test_usernameExists_withNonExistentUsername_returnsFalse() {
        // ACT
        boolean exists = userDAO.usernameExists("nonexistent");

        // ASSERT
        assertFalse("Username should not exist", exists);
    }

    /**
     * Test 6: updateLastLogin() updates the last_login timestamp
     */
    @Test
    public void test_updateLastLogin_updatesTimestamp() {
        // ARRANGE - Insert a user
        User user = new User();
        user.setUsername("loginuser");
        user.setPasswordHash("hash");
        user.setSalt("salt");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setIsActive(true);
        long userId = userDAO.insertUser(user);

        LocalDateTime loginTime = LocalDateTime.now();

        // ACT
        int rowsAffected = userDAO.updateLastLogin(userId, loginTime);

        // ASSERT
        assertEquals("Should update 1 row", 1, rowsAffected);

        // Verify the update
        User updatedUser = userDAO.getUserById(userId);
        assertNotNull("Updated user should exist", updatedUser);
        assertNotNull("Last login should be set", updatedUser.getLastLogin());
    }

    /**
     * Test 7: deleteUser() removes user from database
     */
    @Test
    public void test_deleteUser_removesUser() {
        // ARRANGE
        User user = new User();
        user.setUsername("deletetest");
        user.setPasswordHash("hash");
        user.setSalt("salt");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setIsActive(true);
        long userId = userDAO.insertUser(user);

        // ACT
        int rowsDeleted = userDAO.deleteUser(userId);

        // ASSERT
        assertEquals("Should delete 1 row", 1, rowsDeleted);

        // Verify user no longer exists
        User deletedUser = userDAO.getUserById(userId);
        assertNull("User should be deleted", deletedUser);
    }
}
