# Weight-Tracking Mobile Application - Technical Requirements Document

**Project Name**: Weight Tracker  
**Developer**: Rick Goshen  
**Course**: CS 360 - Mobile Architecture & Programming  
**Platform**: Android  
**Development Environment**: Android Studio  
**Primary Language**: Java  
**UI Framework**: XML Layouts  
**Target API Level**: TBD (based on course requirements)  
**Last Updated**: November 2025

---

## 📋 Table of Contents
1. [Executive Summary](#executive-summary)
2. [Functional Requirements](#functional-requirements)
3. [Database Architecture](#database-architecture)
4. [User Interface Requirements](#user-interface-requirements)
5. [Technical Implementation Details](#technical-implementation-details)
6. [Security & Privacy Requirements](#security--privacy-requirements)
7. [User Experience Requirements](#user-experience-requirements)
8. [Testing Requirements](#testing-requirements)
9. [Future Enhancements](#future-enhancements)
10. [Development Checklist](#development-checklist)

---

## Executive Summary

### Purpose
Provide users with a simple, effective tool for monitoring daily weight and progress toward personal health goals through daily tracking, visual progress display, and goal-oriented notifications.

### Target Users
1. **Weight-Loss Seekers**: Individuals trying to lose weight for health or personal reasons
2. **Health Maintenance Monitors**: Users maintaining current weight or monitoring for medical purposes
3. **Family Health Managers**: Parents/caregivers tracking weight for multiple family members

### Core Value Proposition
Streamlined weight tracking without overwhelming complexity - focusing on essential features: secure login, daily weight entry, historical data display, goal setting, and achievement notifications.

---

## Functional Requirements

### FR-1: User Authentication System

#### FR-1.1: User Registration
**Priority**: Critical  
**Description**: Allow new users to create an account

**Acceptance Criteria**:
- User can enter username/email and password
- System validates username is unique
- System validates password meets minimum requirements (TBD based on security requirements)
- System stores credentials securely in database
- System prevents duplicate usernames
- User receives confirmation of successful registration
- System automatically logs user in after successful registration

**Database Impact**: Insert new record into `users` table

**UI Components**:
- Username/email input field
- Password input field
- Confirm password input field
- "Create Account" button
- Link to switch to login screen

#### FR-1.2: User Login
**Priority**: Critical  
**Description**: Allow existing users to access their account

**Acceptance Criteria**:
- User can enter username and password
- System validates credentials against database
- System grants access if credentials are correct
- System displays error message if credentials are incorrect
- System maintains user session until logout
- System prevents unauthorized access to user data

**Database Impact**: Query `users` table for credential verification

**UI Components**:
- Username input field
- Password input field
- "Login" button
- Link to switch to registration screen

#### FR-1.3: User Logout
**Priority**: High  
**Description**: Allow users to securely log out

**Acceptance Criteria**:
- User can logout from within the app
- System clears user session data
- System returns to login screen
- System prevents access to data after logout

**UI Components**:
- Logout button/menu option

---

### FR-2: Daily Weight Management

#### FR-2.1: Add Daily Weight Entry
**Priority**: Critical  
**Description**: Allow users to record their weight for a specific date

**Acceptance Criteria**:
- User can enter weight value (numeric)
- User can select date (default to current date)
- System validates weight is a positive number
- System validates date is not in the future
- System stores weight entry in database linked to user
- System updates weight history display immediately
- System allows only one entry per date (update if exists)
- System displays confirmation after successful save

**Database Impact**: Insert into `daily_weights` table

**UI Components**:
- Weight input field (numeric)
- Date picker (default: today)
- "Save" button
- Confirmation message/toast

#### FR-2.2: View Weight History
**Priority**: Critical  
**Description**: Display all historical weight entries for the logged-in user

**Acceptance Criteria**:
- System displays all weight entries in chronological order (newest first)
- Each entry shows date and weight value
- Display updates automatically when new entry is added
- Display shows "No entries yet" message if no data exists
- System loads data from database for current user only

**Database Impact**: Query `daily_weights` table filtered by user_id

**UI Components**:
- Scrollable grid/list view
- Date column
- Weight column
- Empty state message

#### FR-2.3: Edit Weight Entry (Optional Enhancement)
**Priority**: Medium  
**Description**: Allow users to modify existing weight entries

**Acceptance Criteria**:
- User can select an existing entry
- User can modify the weight value
- System updates database with new value
- System maintains original date
- System displays confirmation

**Database Impact**: Update `daily_weights` table

#### FR-2.4: Delete Weight Entry (Optional Enhancement)
**Priority**: Low  
**Description**: Allow users to remove incorrect entries

**Acceptance Criteria**:
- User can select entry to delete
- System prompts for confirmation
- System removes entry from database
- System updates display

**Database Impact**: Delete from `daily_weights` table

---

### FR-3: Goal Weight Management

#### FR-3.1: Set Goal Weight
**Priority**: Critical  
**Description**: Allow users to set their target weight

**Acceptance Criteria**:
- User can enter goal weight value (numeric)
- System validates goal weight is a positive number
- System stores goal weight in database
- System allows updating goal weight if already set
- System displays current goal weight
- System displays confirmation after save

**Database Impact**: Insert/Update `goal_weights` table

**UI Components**:
- Goal weight input field (numeric)
- "Set Goal" button
- Current goal display
- Confirmation message

#### FR-3.2: View Goal Weight
**Priority**: High  
**Description**: Display current goal weight to user

**Acceptance Criteria**:
- System displays current goal weight prominently
- Display shows "No goal set" if no goal exists
- System loads from database for current user

**Database Impact**: Query `goal_weights` table filtered by user_id

---

### FR-4: Notification System

#### FR-4.1: Goal Achievement Notification
**Priority**: Critical  
**Description**: Notify user when they reach their goal weight

**Acceptance Criteria**:
- System checks if current weight equals or falls below goal weight (for weight loss)
- System checks if current weight equals or exceeds goal weight (for weight gain)
- System sends notification when goal is achieved
- Notification appears as Android system notification
- Notification includes congratulatory message
- System only sends notification once per goal achievement

**Implementation Notes**:
- Trigger check when new weight entry is added
- Compare latest weight against goal weight
- Determine direction (loss vs. gain) based on starting weight vs. goal weight

**UI Components**:
- Android notification
- In-app congratulations screen/dialog (optional enhancement)

---

## Database Architecture

### Database Technology
**SQLite** - Local database storage on Android device

### Database Schema

#### Table 1: `users`
**Purpose**: Store user authentication credentials

| Column Name | Data Type | Constraints | Description |
|------------|-----------|-------------|-------------|
| `user_id` | INTEGER | PRIMARY KEY, AUTOINCREMENT | Unique identifier for each user |
| `username` | TEXT | NOT NULL, UNIQUE | User's login username |
| `password` | TEXT | NOT NULL | User's password (should be hashed) |
| `created_at` | TEXT | DEFAULT CURRENT_TIMESTAMP | Account creation date |

**Indexes**:
- Primary key index on `user_id`
- Unique index on `username`

**Sample SQL**:
```sql
CREATE TABLE users (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    created_at TEXT DEFAULT CURRENT_TIMESTAMP
);
```

#### Table 2: `daily_weights`
**Purpose**: Store daily weight entries for all users

| Column Name | Data Type | Constraints | Description |
|------------|-----------|-------------|-------------|
| `weight_id` | INTEGER | PRIMARY KEY, AUTOINCREMENT | Unique identifier for each weight entry |
| `user_id` | INTEGER | NOT NULL, FOREIGN KEY | Reference to users table |
| `weight_value` | REAL | NOT NULL | Weight value in user's preferred unit |
| `entry_date` | TEXT | NOT NULL | Date of weight entry (YYYY-MM-DD) |
| `created_at` | TEXT | DEFAULT CURRENT_TIMESTAMP | Timestamp when entry was created |

**Indexes**:
- Primary key index on `weight_id`
- Index on `user_id` (for efficient user-specific queries)
- Composite index on `(user_id, entry_date)` (for ensuring one entry per date per user)

**Sample SQL**:
```sql
CREATE TABLE daily_weights (
    weight_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    weight_value REAL NOT NULL,
    entry_date TEXT NOT NULL,
    created_at TEXT DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE(user_id, entry_date)
);

CREATE INDEX idx_user_weights ON daily_weights(user_id);
CREATE INDEX idx_user_date ON daily_weights(user_id, entry_date);
```

#### Table 3: `goal_weights`
**Purpose**: Store goal weight for each user

| Column Name | Data Type | Constraints | Description |
|------------|-----------|-------------|-------------|
| `goal_id` | INTEGER | PRIMARY KEY, AUTOINCREMENT | Unique identifier for goal |
| `user_id` | INTEGER | NOT NULL, FOREIGN KEY, UNIQUE | Reference to users table (one goal per user) |
| `goal_weight` | REAL | NOT NULL | Target weight value |
| `set_date` | TEXT | DEFAULT CURRENT_TIMESTAMP | When goal was set/last updated |

**Indexes**:
- Primary key index on `goal_id`
- Unique index on `user_id` (ensures one goal per user)

**Sample SQL**:
```sql
CREATE TABLE goal_weights (
    goal_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL UNIQUE,
    goal_weight REAL NOT NULL,
    set_date TEXT DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX idx_user_goal ON goal_weights(user_id);
```

### Database Helper Class Structure

```java
public class WeightTrackerDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "weight_tracker.db";
    private static final int DATABASE_VERSION = 1;
    
    // Table names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_DAILY_WEIGHTS = "daily_weights";
    public static final String TABLE_GOAL_WEIGHTS = "goal_weights";
    
    // Common column names
    public static final String COLUMN_USER_ID = "user_id";
    
    // Users table columns
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_CREATED_AT = "created_at";
    
    // Daily weights table columns
    public static final String COLUMN_WEIGHT_ID = "weight_id";
    public static final String COLUMN_WEIGHT_VALUE = "weight_value";
    public static final String COLUMN_ENTRY_DATE = "entry_date";
    
    // Goal weights table columns
    public static final String COLUMN_GOAL_ID = "goal_id";
    public static final String COLUMN_GOAL_WEIGHT = "goal_weight";
    public static final String COLUMN_SET_DATE = "set_date";
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades
    }
}
```

---

## User Interface Requirements

### UI-1: Login/Registration Screen

**Layout**: Single screen with toggle between login and registration modes

**Components**:
- App logo/title (centered at top)
- Username input field
- Password input field
- Confirm password field (registration mode only)
- "Login" or "Create Account" button (primary action)
- "Switch to Register" / "Switch to Login" link/button
- Error message display area (hidden by default)

**Design Considerations**:
- Use Material Design guidelines
- Clear visual hierarchy
- Keyboard-friendly (numeric input for weights)
- Error messages in red, clearly visible
- Focus on simplicity and clarity

### UI-2: Main Dashboard Screen

**Layout**: Central hub displaying weight history and quick actions

**Components**:
- Header with app title and user greeting
- "Add Weight" button (prominent, easy to access)
- Weight history grid/list (scrollable)
  - Date column
  - Weight column
  - Visual indicators (up/down from previous entry)
- Current goal weight display
- Progress indicator (optional: visual progress bar/chart)
- Menu button (for logout, settings)

**Design Considerations**:
- Most recent entries at top
- Clear date formatting (e.g., "Today", "Yesterday", "Nov 8, 2025")
- Visual feedback for trends (up/down arrows or color coding)
- Empty state message if no entries

### UI-3: Add Weight Entry Screen/Dialog

**Layout**: Modal dialog or separate screen

**Components**:
- Weight input field (numeric keyboard)
- Unit display (lbs or kg)
- Date picker (default to today)
- "Save" button
- "Cancel" button

**Design Considerations**:
- Quick entry focus (minimal steps)
- Clear labels and hints
- Validation feedback
- Success confirmation (toast message)

### UI-4: Goal Setting Screen/Dialog

**Layout**: Modal dialog or separate screen

**Components**:
- Current goal display (if exists)
- Goal weight input field (numeric keyboard)
- Unit display (lbs or kg)
- "Set Goal" button
- "Cancel" button

**Design Considerations**:
- Show current goal prominently
- Clear update vs. initial set distinction
- Validation feedback
- Success confirmation

### UI-5: Navigation Structure

```
Login/Register Screen
    ↓ (on successful login)
Main Dashboard
    ├→ Add Weight Entry (dialog/screen)
    ├→ Set Goal Weight (dialog/screen)
    └→ Menu
        ├→ Logout
        └→ Settings (future enhancement)
```

---

## Technical Implementation Details

### Tech-1: Android Components

#### Activities
- **LoginActivity**: Handle login/registration
- **MainActivity**: Main dashboard with weight history
- **AddWeightActivity** (or Dialog): Weight entry interface

#### Fragments (Optional Alternative)
- **LoginFragment**
- **DashboardFragment**
- **WeightHistoryFragment**

#### Services
- **NotificationService**: Background service to check for goal achievement

#### Broadcast Receivers (Optional)
- Daily reminder notifications (future enhancement)

### Tech-2: Data Access Layer

#### Database Manager Class
```java
public class WeightTrackerDAO {
    private WeightTrackerDBHelper dbHelper;
    private SQLiteDatabase database;
    
    // User methods
    public long createUser(String username, String password);
    public User authenticateUser(String username, String password);
    
    // Weight entry methods
    public long addWeightEntry(int userId, double weight, String date);
    public List<WeightEntry> getWeightHistory(int userId);
    public WeightEntry getLatestWeight(int userId);
    public boolean updateWeightEntry(int weightId, double newWeight);
    public boolean deleteWeightEntry(int weightId);
    
    // Goal weight methods
    public long setGoalWeight(int userId, double goalWeight);
    public Double getGoalWeight(int userId);
    public boolean updateGoalWeight(int userId, double newGoalWeight);
}
```

#### Data Models
```java
public class User {
    private int userId;
    private String username;
    private String password;
    private String createdAt;
    // Getters and setters
}

public class WeightEntry {
    private int weightId;
    private int userId;
    private double weightValue;
    private String entryDate;
    private String createdAt;
    // Getters and setters
}

public class GoalWeight {
    private int goalId;
    private int userId;
    private double goalWeight;
    private String setDate;
    // Getters and setters
}
```

### Tech-3: Notification Implementation

#### Notification Manager
```java
public class NotificationManager {
    private static final String CHANNEL_ID = "weight_goal_channel";
    private static final int NOTIFICATION_ID = 1001;
    
    public void createNotificationChannel(Context context);
    public void sendGoalAchievedNotification(Context context, double goalWeight);
    public boolean checkGoalAchievement(double currentWeight, double goalWeight, double startingWeight);
}
```

**Notification Trigger Points**:
- After user saves a new weight entry
- Compare latest weight against goal weight
- Consider direction (loss vs. gain) based on relationship between starting weight and goal

### Tech-4: Input Validation

#### Validation Rules
- **Username**: 
  - Not empty
  - Length: 3-20 characters
  - Alphanumeric characters only
  - Must be unique in database
  
- **Password**: 
  - Not empty
  - Minimum 6 characters (can be adjusted)
  - Must match confirmation password (during registration)
  
- **Weight Value**:
  - Not empty
  - Positive number
  - Reasonable range: 1-1000 lbs or 0.5-500 kg
  - Maximum 2 decimal places
  
- **Date**:
  - Valid date format
  - Not in the future
  - Not before reasonable start date (e.g., not before 1900)

### Tech-5: Data Persistence

#### SharedPreferences (for app settings)
```java
// Store user session
SharedPreferences prefs = getSharedPreferences("WeightTrackerPrefs", MODE_PRIVATE);
prefs.edit()
    .putInt("current_user_id", userId)
    .putString("username", username)
    .putBoolean("is_logged_in", true)
    .apply();
```

#### SQLite Database (for user data)
- All user, weight, and goal data stored in SQLite
- Database persists across app restarts
- Automatic backup recommendations (future enhancement)

---

## Security & Privacy Requirements

### SEC-1: Password Security
**Priority**: Critical

**Requirements**:
- Passwords must be hashed before storage (use BCrypt or similar)
- Never store plain-text passwords
- Implement salting to prevent rainbow table attacks

**Implementation**:
```java
// Use a proper password hashing library
// Example: BCrypt, Argon2, or PBKDF2
public String hashPassword(String plainPassword) {
    // Implementation using secure hashing
}

public boolean verifyPassword(String plainPassword, String hashedPassword) {
    // Verify password against hash
}
```

### SEC-2: User Data Isolation
**Priority**: Critical

**Requirements**:
- Each user can only access their own data
- All database queries must filter by current user_id
- Enforce foreign key constraints
- Use ON DELETE CASCADE to ensure data integrity

### SEC-3: Input Sanitization
**Priority**: High

**Requirements**:
- Sanitize all user inputs before database operations
- Use parameterized queries to prevent SQL injection
- Validate data types and ranges

**Example**:
```java
// CORRECT - Using parameterized queries
String query = "INSERT INTO daily_weights (user_id, weight_value, entry_date) VALUES (?, ?, ?)";
SQLiteStatement statement = db.compileStatement(query);
statement.bindLong(1, userId);
statement.bindDouble(2, weight);
statement.bindString(3, date);
statement.executeInsert();

// WRONG - Never concatenate user input into queries
// String query = "INSERT INTO daily_weights VALUES (" + userId + ", " + weight + ", '" + date + "')";
```

### SEC-4: Session Management
**Priority**: High

**Requirements**:
- Implement proper session timeout
- Clear session data on logout
- Secure session storage using SharedPreferences
- Validate session on app resume

---

## User Experience Requirements

### UX-1: Performance

**Requirements**:
- App launch time: < 2 seconds
- Database query response: < 500ms
- Smooth scrolling in weight history (60 FPS)
- No visible lag when adding entries

**Implementation Strategies**:
- Use background threads for database operations (AsyncTask, Executors, or Coroutines)
- Implement pagination for large weight history (if >100 entries)
- Cache frequently accessed data
- Use RecyclerView for efficient list rendering

### UX-2: Feedback & Confirmation

**Requirements**:
- Show loading indicators for operations > 200ms
- Display success messages (Toast) for completed actions
- Show error messages clearly with actionable guidance
- Provide visual feedback for button presses (ripple effect)

### UX-3: Error Handling

**Requirements**:
- Graceful handling of all errors
- User-friendly error messages (avoid technical jargon)
- Prevent app crashes with try-catch blocks
- Log errors for debugging

**Common Error Scenarios**:
- Database connection failure
- Invalid input data
- Network issues (if future features use network)
- Storage space limitations

### UX-4: Accessibility

**Requirements**:
- All UI elements have content descriptions
- Minimum touch target size: 48dp x 48dp
- Sufficient color contrast (WCAG AA standards)
- Support for screen readers (TalkBack)
- Keyboard navigation support

### UX-5: Responsiveness

**Requirements**:
- Support multiple screen sizes (phone, tablet)
- Layouts adapt to landscape and portrait orientations
- No horizontal scrolling (except where intentional)
- Appropriate text sizes for readability

---

## Testing Requirements

### TEST-1: Unit Testing

**Components to Test**:
- Database CRUD operations
- Input validation logic
- Password hashing/verification
- Goal achievement calculation
- Data model getters/setters

**Framework**: JUnit 4 or JUnit 5

**Sample Test Cases**:
```java
@Test
public void testAddWeightEntry_ValidData_ReturnsId() {
    // Arrange
    WeightTrackerDAO dao = new WeightTrackerDAO(context);
    int userId = 1;
    double weight = 180.5;
    String date = "2025-11-08";
    
    // Act
    long result = dao.addWeightEntry(userId, weight, date);
    
    // Assert
    assertTrue(result > 0);
}

@Test
public void testValidateWeight_NegativeValue_ReturnsFalse() {
    // Test validation logic
}

@Test
public void testGoalAchievement_WeightLoss_CorrectlyDetects() {
    // Test notification trigger logic
}
```

### TEST-2: Integration Testing

**Test Scenarios**:
- Complete user registration flow
- Login → Add weight → View history flow
- Set goal → Add weights → Receive notification flow
- Database persistence across app restarts

**Framework**: Espresso or AndroidJUnit

### TEST-3: UI Testing

**Test Scenarios**:
- All buttons and inputs are clickable/tappable
- Navigation flows work correctly
- Error messages display properly
- Screen rotations preserve data
- Keyboard interactions work correctly

**Framework**: Espresso

**Sample Test**:
```java
@Test
public void testAddWeight_ValidInput_DisplaysInHistory() {
    // Launch app
    // Navigate to add weight
    // Enter weight value
    // Click save
    // Verify weight appears in history list
}
```

### TEST-4: Manual Testing Checklist

**Pre-Release Testing**:
- [ ] Install on physical device
- [ ] Test on multiple Android versions (if possible)
- [ ] Test with various screen sizes
- [ ] Test in portrait and landscape
- [ ] Test with poor/no network (should work fine - local only)
- [ ] Test database with large datasets (100+ entries)
- [ ] Test rapid user interactions (stress testing)
- [ ] Verify notifications appear correctly
- [ ] Test logout and re-login
- [ ] Verify data persists after app closes

---

## Future Enhancements

### Phase 2 Features (After Course Completion)

1. **Data Visualization**
   - Line chart showing weight trends over time
   - BMI calculation and tracking
   - Weight loss/gain rate statistics
   - Progress percentage toward goal

2. **Enhanced Notifications**
   - Daily reminder to weigh in
   - Customizable notification times
   - Milestone celebrations (every 5 lbs, etc.)

3. **Data Management**
   - Export weight history to CSV
   - Import data from other apps
   - Cloud backup and sync
   - Data sharing with healthcare providers

4. **Additional Features**
   - Multiple goal types (weight ranges, maintenance)
   - Notes field for each weight entry
   - Photo progress tracking
   - Body measurements tracking
   - Integration with fitness apps (Google Fit, etc.)

5. **Social Features**
   - Share progress with friends/family
   - Support groups/communities
   - Challenges and competitions

6. **Advanced UI**
   - Dark mode support
   - Customizable themes
   - Widget for home screen
   - Wear OS companion app

---

## Development Checklist

### Phase 1: Project Setup
- [ ] Create new Android Studio project
- [ ] Set up version control (Git)
- [ ] Configure minimum SDK version
- [ ] Add necessary permissions to AndroidManifest.xml
- [ ] Set up project structure (packages, folders)
- [ ] Create README.md with project description

### Phase 2: Database Implementation
- [ ] Create SQLiteOpenHelper class
- [ ] Define database schema (tables, columns)
- [ ] Write SQL CREATE TABLE statements
- [ ] Implement onCreate() method
- [ ] Implement onUpgrade() method
- [ ] Create database manager/DAO class
- [ ] Write CRUD methods for users table
- [ ] Write CRUD methods for daily_weights table
- [ ] Write CRUD methods for goal_weights table
- [ ] Test database operations with unit tests

### Phase 3: Data Models
- [ ] Create User model class
- [ ] Create WeightEntry model class
- [ ] Create GoalWeight model class
- [ ] Implement constructors, getters, setters
- [ ] Add toString() methods for debugging

### Phase 4: Authentication UI
- [ ] Design login/register layout (XML)
- [ ] Create LoginActivity class
- [ ] Implement registration logic
- [ ] Implement login logic
- [ ] Add input validation
- [ ] Implement password hashing
- [ ] Add error message displays
- [ ] Test login/register flows

### Phase 5: Main Dashboard UI
- [ ] Design main dashboard layout (XML)
- [ ] Create MainActivity class
- [ ] Implement navigation from login to dashboard
- [ ] Display user greeting/info
- [ ] Add logout functionality
- [ ] Verify session management

### Phase 6: Weight Entry Feature
- [ ] Design add weight entry layout (XML)
- [ ] Create AddWeightActivity or dialog
- [ ] Implement weight input field
- [ ] Implement date picker
- [ ] Add save button functionality
- [ ] Connect to database
- [ ] Implement validation
- [ ] Add success/error feedback
- [ ] Test weight entry flow

### Phase 7: Weight History Display
- [ ] Design weight history list item layout (XML)
- [ ] Create RecyclerView adapter
- [ ] Implement data binding
- [ ] Query database for user's weights
- [ ] Display weights in chronological order
- [ ] Add empty state message
- [ ] Test with various data amounts
- [ ] Implement pull-to-refresh (optional)

### Phase 8: Goal Weight Feature
- [ ] Design goal setting layout (XML)
- [ ] Create goal setting activity/dialog
- [ ] Implement goal weight input
- [ ] Add save goal functionality
- [ ] Display current goal on dashboard
- [ ] Add goal update capability
- [ ] Test goal setting flow

### Phase 9: Notification System
- [ ] Create notification channel
- [ ] Implement notification manager
- [ ] Write goal achievement check logic
- [ ] Trigger notification on goal reached
- [ ] Design notification content
- [ ] Test notification display
- [ ] Verify notification triggers correctly

### Phase 10: Testing & Refinement
- [ ] Write unit tests for database operations
- [ ] Write unit tests for validation logic
- [ ] Create integration tests
- [ ] Perform manual testing on device
- [ ] Test edge cases (empty data, large datasets)
- [ ] Fix bugs identified during testing
- [ ] Optimize performance
- [ ] Verify accessibility features

### Phase 11: Polish & Documentation
- [ ] Refine UI based on testing feedback
- [ ] Add app icon
- [ ] Create splash screen (optional)
- [ ] Ensure consistent styling
- [ ] Verify error handling throughout
- [ ] Add code comments
- [ ] Create user documentation
- [ ] Prepare demo/presentation materials

### Phase 12: Deployment Preparation
- [ ] Test on multiple devices/emulators
- [ ] Verify all permissions are necessary
- [ ] Create signed APK
- [ ] Test signed APK installation
- [ ] Prepare app store listing (if publishing)
- [ ] Create screenshots for portfolio
- [ ] Document known limitations

---

## Key Metrics & Success Criteria

### Functionality Metrics
- [ ] All required features implemented and working
- [ ] Zero critical bugs
- [ ] < 5 minor bugs
- [ ] All database operations functional
- [ ] Notifications work reliably

### Performance Metrics
- [ ] App launches in < 2 seconds
- [ ] Database queries complete in < 500ms
- [ ] No UI lag or stuttering
- [ ] Smooth scrolling (60 FPS)
- [ ] No memory leaks

### Code Quality Metrics
- [ ] All code properly commented
- [ ] Follows Java naming conventions
- [ ] No unused imports or variables
- [ ] Proper exception handling throughout
- [ ] Code organized logically

### User Experience Metrics
- [ ] Intuitive navigation (no user confusion)
- [ ] Clear feedback for all actions
- [ ] Error messages are helpful
- [ ] Consistent UI design
- [ ] Accessible to all users

---

## Technical Constraints & Considerations

### Platform Constraints
- **Minimum SDK**: Android 6.0 (API 23) or as specified by course
- **Target SDK**: Latest stable version
- **Storage**: Local SQLite only (no cloud requirements)
- **Network**: Not required (offline-first app)

### Development Constraints
- **Timeline**: CS 360 course duration (~8 weeks)
- **Resources**: Individual developer, limited time
- **Tools**: Android Studio, Java, XML
- **Testing**: Limited to emulator and personal devices

### Design Constraints
- **Simplicity**: Keep UI simple and focused
- **Performance**: Must work smoothly on mid-range devices
- **Accessibility**: Follow Android accessibility guidelines
- **Localization**: English only (for initial version)

---

## Appendix

### A. Useful Resources

**Android Development**:
- [Android Developer Documentation](https://developer.android.com/)
- [Material Design Guidelines](https://material.io/design)
- [SQLite Android Documentation](https://developer.android.com/training/data-storage/sqlite)

**Java Resources**:
- [Oracle Java Tutorials](https://docs.oracle.com/javase/tutorial/)
- [Effective Java by Joshua Bloch](https://www.oreilly.com/library/view/effective-java/9780134686097/)

**Course Materials**:
- CS 360 textbook chapters
- Android Studio tutorial resources
- Project guidelines and rubrics

### B. Common Android Permissions Required

```xml
<!-- AndroidManifest.xml -->
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
<!-- Add others as needed -->
```

### C. Sample Package Structure

```
com.rickgoshen.weighttracker/
├── activities/
│   ├── LoginActivity.java
│   ├── MainActivity.java
│   └── AddWeightActivity.java
├── database/
│   ├── WeightTrackerDBHelper.java
│   └── WeightTrackerDAO.java
├── models/
│   ├── User.java
│   ├── WeightEntry.java
│   └── GoalWeight.java
├── adapters/
│   └── WeightHistoryAdapter.java
├── utils/
│   ├── ValidationUtils.java
│   └── NotificationManager.java
└── constants/
    └── AppConstants.java
```

### D. Git Commit Message Conventions

```
feat: Add user authentication system
fix: Correct date picker default value
docs: Update requirements document
test: Add unit tests for database operations
refactor: Reorganize database helper methods
style: Format code per Java conventions
```

---

## Document Revision History

| Version | Date | Author | Changes |
|---------|------|--------|---------|
| 1.0 | Nov 2025 | Rick Goshen | Initial comprehensive requirements document |

---

**End of Requirements Document**

*This document will be updated throughout the development process as requirements are refined and features are implemented.*
