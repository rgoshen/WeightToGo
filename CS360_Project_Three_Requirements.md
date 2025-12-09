# 🚀 WEIGH TO GO! - Project Three: Full Implementation & Launch
## CS 360 Mobile Architecture & Programming - Complete Functional Build

**Student**: Rick Goshen  
**App Name**: Weigh to Go!  
**Tagline**: "You've got this—pound for pound."  
**Platform**: Android (Java/XML)  
**IDE**: Android Studio  
**Target SDK**: API 34 (Android 14)  
**Minimum SDK**: API 26 (Android 8.0)  
**Project Type**: Capstone - Full Functional Application + Launch Plan

---

## 📋 PROJECT OVERVIEW

### Competency Being Demonstrated
**Develop and launch a fully functional mobile application**

### Project Context
Building on the approved proposal (Project One) and complete UI design (Project Two), this final project requires:
1. **Full functional implementation** of all features with working code
2. **Database integration** using SQLite for data persistence
3. **Authentication system** with secure login/registration
4. **SMS notification system** with runtime permissions
5. **Comprehensive launch plan** preparing the app for market deployment

### What Changes from Project Two?
- **Project Two**: UI only (visual layer, stub code)
- **Project Three**: Full functionality (working database, authentication, SMS, business logic)

### Submission Requirements

#### 1. App Code Design
- **Format**: ZIP file of complete Android Studio project folder
- **Naming**: `Rick_Goshen_WeightToGo_Final.zip`
- **Contents**: Full Android Studio project with functional code
- **Optional**: APK file to demonstrate functionality

#### 2. App Launch Plan
- **Format**: Microsoft Word document
- **Length**: 2-3 pages
- **Font**: 12-point Times New Roman
- **Spacing**: Double-spaced
- **Margins**: 1-inch all around
- **Naming**: `Rick_Goshen_WeightToGo_LaunchPlan.docx`

---

## 🎯 GRADING RUBRIC ALIGNMENT

| Criterion | Weight | Requirements |
|-----------|--------|--------------|
| **Login** | 20% | Functional login system with database authentication |
| **Database** | 20% | Working database with CRUD operations and grid display |
| **SMS Notifications** | 20% | Runtime permission handling and SMS functionality |
| **Coding Best Practices** | 15% | Industry-standard code quality and documentation |
| **Launch Plan** | 20% | Comprehensive plan addressing all launch components |
| **Clear Communication** | 5% | Organized, effective documentation |

**To Achieve "Exceeds Expectations"**: Complete requirements in an "exceptionally clear, insightful, sophisticated, or creative manner"

---

## 🔐 REQUIREMENT 1: LOGIN SYSTEM (20 Points)

### Functional Requirements

#### User Login Flow
```
1. User enters username and password
2. App queries database for matching credentials
3. If match found → Grant access to main dashboard
4. If no match → Display error message
5. Keep user logged in (SharedPreferences)
```

#### New User Registration Flow
```
1. User clicks "Create Account" button
2. User enters desired username and password
3. App validates input (not empty, username not taken)
4. App hashes password for security
5. App inserts new user into database
6. Display success message and auto-login
```

### Database Schema

#### Users Table
```sql
CREATE TABLE users (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP
);
```

### Implementation Specifications

#### LoginActivity.java - Key Methods
```java
/**
 * Validates user credentials against database
 * @param username User's username
 * @param password User's password (plain text)
 * @return User object if valid, null if invalid
 */
private User validateCredentials(String username, String password)

/**
 * Creates new user account in database
 * @param username Desired username
 * @param password Desired password (will be hashed)
 * @return true if successful, false if username taken
 */
private boolean createNewUser(String username, String password)

/**
 * Saves user session to SharedPreferences
 * @param userId User's database ID
 */
private void saveUserSession(int userId)

/**
 * Validates input fields before processing
 * @return true if valid, false if errors present
 */
private boolean validateInput()
```

#### Security Requirements
- ✅ **Password Hashing**: Use BCrypt or SHA-256 (never store plain text)
- ✅ **Input Validation**: Check for empty fields, SQL injection prevention
- ✅ **Session Management**: Store user_id in SharedPreferences
- ✅ **Auto-Login**: Check for existing session on app launch
- ✅ **Logout Function**: Clear SharedPreferences and return to login

### Success Criteria
- [ ] User can create new account with username and password
- [ ] Credentials are saved to database
- [ ] User can log in with saved credentials
- [ ] Invalid credentials show appropriate error message
- [ ] Passwords are hashed (not stored as plain text)
- [ ] User remains logged in after closing and reopening app
- [ ] Duplicate usernames are prevented
- [ ] Empty fields are validated before submission

---

## 💾 REQUIREMENT 2: DATABASE IMPLEMENTATION (20 Points)

### Database Architecture

#### Complete Schema Design
```sql
-- Users table (from Login requirement)
CREATE TABLE users (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP
);

-- Weight entries table
CREATE TABLE weight_entries (
    entry_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    weight_value REAL NOT NULL,
    date_recorded DATE NOT NULL,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Goal weights table
CREATE TABLE goal_weights (
    goal_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    target_weight REAL NOT NULL,
    target_date DATE,
    is_active INTEGER DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
```

### CRUD Operations Required

#### Create (Insert)
- Add new weight entry
- Add new user (from login)
- Set new goal weight

#### Read (Query)
- Get all weight entries for current user
- Get most recent weight entry
- Get current goal weight
- Get weight entries within date range

#### Update (Modify)
- Edit existing weight entry
- Update goal weight
- Update user's last login timestamp

#### Delete (Remove)
- Delete weight entry
- Delete user account (cascade deletes all user data)

### Grid Display Specifications

#### RecyclerView Implementation
```java
// Display columns in grid
- Date (formatted: MM/DD/YYYY)
- Weight (lbs with 1 decimal place)
- Difference from previous entry (+/- format)
- Notes preview (first 30 characters)
- Edit button
- Delete button (REQUIRED by rubric)
```

#### Data Loading Pattern
```java
/**
 * Loads all weight entries for current user from database
 * Sorts by date descending (newest first)
 * Updates RecyclerView adapter
 */
private void loadWeightEntries()

/**
 * Deletes weight entry from database
 * Refreshes RecyclerView display
 * @param entryId Database ID of entry to delete
 */
private void deleteWeightEntry(int entryId)

/**
 * Calculates weight difference from previous entry
 * @param currentWeight Current entry's weight
 * @param previousWeight Previous entry's weight
 * @return Formatted string with +/- indicator
 */
private String calculateWeightDifference(double currentWeight, double previousWeight)
```

### Database Helper Class

#### WeighToGoDBHelper.java Structure
```java
public class WeighToGoDBHelper extends SQLiteOpenHelper {
    
    // Database info
    private static final String DATABASE_NAME = "WeighToGo.db";
    private static final int DATABASE_VERSION = 1;
    
    // Table names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_WEIGHT_ENTRIES = "weight_entries";
    private static final String TABLE_GOAL_WEIGHTS = "goal_weights";
    
    // Required methods
    @Override
    public void onCreate(SQLiteDatabase db)
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    
    // User operations
    public long insertUser(String username, String passwordHash)
    public User getUser(String username)
    public boolean userExists(String username)
    public void updateLastLogin(int userId)
    
    // Weight entry operations
    public long insertWeightEntry(int userId, double weight, String date, String notes)
    public List<WeightEntry> getAllWeightEntries(int userId)
    public WeightEntry getWeightEntry(int entryId)
    public int updateWeightEntry(int entryId, double weight, String notes)
    public int deleteWeightEntry(int entryId)
    
    // Goal weight operations
    public long insertGoalWeight(int userId, double targetWeight, String targetDate)
    public GoalWeight getActiveGoal(int userId)
    public int updateGoalWeight(int goalId, double targetWeight, String targetDate)
}
```

### Success Criteria
- [ ] Database creates successfully on first app launch
- [ ] All three tables are created with proper schema
- [ ] Weight entries can be inserted into database
- [ ] Weight entries display in grid on main screen
- [ ] User can delete weight entries (required delete button works)
- [ ] User can edit existing weight entries
- [ ] Grid shows data only for currently logged-in user
- [ ] Database queries are performed off main thread (AsyncTask or Room)
- [ ] Foreign key constraints prevent orphaned data

---

## 📱 REQUIREMENT 3: SMS NOTIFICATIONS (20 Points)

### Functional Requirements

#### Permission Flow Implementation
```
1. App checks if SMS permission granted
2. If NOT granted → Display permission request UI
3. User clicks "Enable SMS Notifications" button
4. Android system shows permission dialog
5. User selects "Allow" or "Deny"
6. App updates UI based on response
7. If denied → Show alternative options (app continues without SMS)
8. If granted → Enable SMS notification features
```

#### SMS Sending Triggers
```java
// Trigger 1: Goal Achievement
if (currentWeight <= goalWeight) {
    sendSMS("Congratulations! You've reached your goal weight!");
}

// Trigger 2: Daily Reminder (if enabled)
// Use AlarmManager or WorkManager for scheduled notifications
scheduleDaily reminder at user-specified time

// Trigger 3: Milestone Achievement (optional)
if (totalWeightLost % 5 == 0) {  // Every 5 lbs
    sendSMS("Great progress! You've lost " + totalWeightLost + " pounds!");
}
```

### Android Manifest Configuration

#### Required Permissions
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    
    <!-- SMS Permission (dangerous permission - requires runtime request) -->
    <uses-permission android:name="android.permission.SEND_SMS" />
    
    <!-- Telephony feature (indicates SMS capability) -->
    <uses-feature 
        android:name="android.hardware.telephony" 
        android:required="false" />
    
    <!-- Note: android:required="false" allows app to work on devices without SMS -->
</manifest>
```

### Runtime Permission Handling

#### SettingsActivity.java - Permission Methods
```java
// Permission request launcher (ActivityResultContracts)
private final ActivityResultLauncher<String> requestPermissionLauncher =
    registerForActivityResult(new ActivityResultContracts.RequestPermission(), 
        isGranted -> {
            updatePermissionUI(isGranted);
            savePermissionPreference(isGranted);
        }
    );

/**
 * Checks current SMS permission status
 * @return true if permission granted, false otherwise
 */
private boolean checkSmsPermission() {
    return ContextCompat.checkSelfPermission(this, 
        Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
}

/**
 * Requests SMS permission from user
 * Shows system permission dialog
 */
private void requestSmsPermission() {
    if (shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {
        // Show explanation dialog first
        showPermissionRationaleDialog();
    } else {
        // Direct permission request
        requestPermissionLauncher.launch(Manifest.permission.SEND_SMS);
    }
}

/**
 * Updates UI elements based on permission status
 * @param hasPermission Current permission state
 */
private void updatePermissionUI(boolean hasPermission) {
    if (hasPermission) {
        // Show granted state
        permissionStatusIcon.setImageResource(R.drawable.ic_check_circle);
        permissionStatusText.setText("SMS Notifications Enabled");
        requestButton.setVisibility(View.GONE);
        notificationToggles.setEnabled(true);
    } else {
        // Show denied state
        permissionStatusIcon.setImageResource(R.drawable.ic_warning);
        permissionStatusText.setText("SMS Notifications Disabled");
        requestButton.setVisibility(View.VISIBLE);
        deniedWarning.setVisibility(View.VISIBLE);
        notificationToggles.setEnabled(false);
    }
}

/**
 * Sends SMS message to user's phone number
 * @param message Text message content
 */
private void sendSMS(String message) {
    if (!checkSmsPermission()) {
        Log.w("SMS", "Permission not granted - skipping SMS");
        return;
    }
    
    String phoneNumber = getPhoneNumber();  // From SharedPreferences
    
    try {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Log.i("SMS", "Message sent successfully");
    } catch (Exception e) {
        Log.e("SMS", "Failed to send message", e);
        Toast.makeText(this, "Failed to send SMS", Toast.LENGTH_SHORT).show();
    }
}
```

### App Functionality Without Permission

#### Critical Requirement: App Must Function Without SMS
```java
// Example: Weight entry still works without SMS
public void saveWeightEntry(double weight) {
    // 1. Save to database (always works)
    long entryId = dbHelper.insertWeightEntry(userId, weight, date, notes);
    
    // 2. Check goal achievement
    if (weight <= goalWeight) {
        // 3. Try to send SMS notification
        if (checkSmsPermission()) {
            sendSMS("Goal achieved!");
        }
        
        // 4. Show in-app notification (works with or without SMS)
        showInAppNotification("Goal achieved!");
    }
    
    // 5. Update UI (always works)
    refreshWeightList();
}
```

### User Settings Implementation

#### Notification Preferences
```java
// SharedPreferences keys
private static final String PREF_PHONE_NUMBER = "phone_number";
private static final String PREF_GOAL_NOTIFICATIONS = "goal_notifications_enabled";
private static final String PREF_DAILY_REMINDER = "daily_reminder_enabled";
private static final String PREF_REMINDER_TIME = "reminder_time";

/**
 * Saves user's notification preferences
 */
private void saveNotificationSettings() {
    SharedPreferences prefs = getSharedPreferences("WeighToGo", MODE_PRIVATE);
    prefs.edit()
        .putString(PREF_PHONE_NUMBER, phoneNumberInput.getText().toString())
        .putBoolean(PREF_GOAL_NOTIFICATIONS, goalNotificationSwitch.isChecked())
        .putBoolean(PREF_DAILY_REMINDER, dailyReminderSwitch.isChecked())
        .putString(PREF_REMINDER_TIME, selectedTime)
        .apply();
}
```

### Success Criteria
- [ ] App requests SMS permission at appropriate time (not on first launch)
- [ ] Permission request uses ActivityResultContracts (modern Android)
- [ ] AndroidManifest.xml includes SEND_SMS permission
- [ ] AndroidManifest.xml includes telephony feature (required="false")
- [ ] UI updates based on permission granted/denied status
- [ ] App continues to function if user denies permission
- [ ] SMS notifications do NOT send if permission denied
- [ ] User can enable/disable notification types in settings
- [ ] SMS sends successfully when goal is achieved (if permitted)
- [ ] "Open App Settings" button works if user permanently denied permission

---

## 💎 REQUIREMENT 4: CODING BEST PRACTICES (15 Points)

### Code Quality Standards

#### 1. In-Line Comments
```java
// ✅ GOOD - Explains WHY and complex logic
/**
 * Calculates BMI and determines if user has reached healthy weight range.
 * Uses CDC formula: weight (lb) / [height (in)]² × 703
 * 
 * @param weight Current weight in pounds
 * @param height Height in inches
 * @return BMI value rounded to 1 decimal place
 */
private double calculateBMI(double weight, double height) {
    // Prevent division by zero
    if (height <= 0) {
        return 0.0;
    }
    
    // CDC BMI formula for imperial units
    double bmi = (weight / (height * height)) * 703;
    
    // Round to 1 decimal place for display
    return Math.round(bmi * 10.0) / 10.0;
}

// ❌ BAD - States the obvious
private double calculateBMI(double weight, double height) {
    // Calculate BMI
    return (weight / (height * height)) * 703;  // Return BMI
}
```

#### 2. Naming Conventions

##### Java Class Names (PascalCase)
```java
✅ LoginActivity
✅ WeightEntryAdapter
✅ WeighToGoDBHelper
✅ SMSNotificationManager

❌ loginActivity
❌ weight_entry_adapter
❌ db_helper
```

##### Method Names (camelCase - verbs)
```java
✅ validateCredentials()
✅ insertWeightEntry()
✅ sendSMSNotification()
✅ updatePermissionUI()

❌ ValidateCredentials()
❌ insert_weight_entry()
❌ SMS_notification()
```

##### Variable Names (camelCase - nouns)
```java
✅ currentWeight
✅ goalWeight
✅ userPhoneNumber
✅ permissionGranted

❌ CurrentWeight
❌ goal_weight
❌ phoneNum
❌ x
```

##### Constants (UPPER_SNAKE_CASE)
```java
✅ private static final String DATABASE_NAME = "WeighToGo.db";
✅ private static final int DATABASE_VERSION = 1;
✅ private static final String TABLE_USERS = "users";

❌ private static final String databaseName = "WeighToGo.db";
```

##### XML Resource IDs (snake_case)
```xml
✅ android:id="@+id/username_input"
✅ android:id="@+id/sign_in_button"
✅ android:id="@+id/weight_recycler_view"

❌ android:id="@+id/usernameInput"
❌ android:id="@+id/SignInButton"
```

#### 3. Class Organization

##### Recommended Structure
```java
public class MainActivity extends AppCompatActivity {
    
    // 1. Constants
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_ADD_WEIGHT = 100;
    
    // 2. Member variables (grouped logically)
    // UI Components
    private RecyclerView weightRecyclerView;
    private FloatingActionButton addWeightFab;
    private TextView goalWeightText;
    
    // Data
    private WeighToGoDBHelper dbHelper;
    private WeightEntryAdapter adapter;
    private List<WeightEntry> weightEntries;
    
    // User session
    private int currentUserId;
    
    // 3. Lifecycle methods (in order)
    @Override
    protected void onCreate(Bundle savedInstanceState) { }
    
    @Override
    protected void onResume() { }
    
    @Override
    protected void onPause() { }
    
    @Override
    protected void onDestroy() { }
    
    // 4. Initialization methods
    private void initializeViews() { }
    private void setupRecyclerView() { }
    private void setupClickListeners() { }
    
    // 5. Business logic methods
    private void loadWeightEntries() { }
    private void addWeightEntry() { }
    private void deleteWeightEntry(int entryId) { }
    
    // 6. Helper methods
    private String formatDate(Date date) { }
    private boolean validateInput() { }
    
    // 7. Inner classes
    private class WeightEntryViewHolder extends RecyclerView.ViewHolder { }
}
```

#### 4. Keep Classes Concise

##### Single Responsibility Principle
```java
// ✅ GOOD - Each class has one clear purpose
LoginActivity.java          // Handles login/registration UI
WeighToGoDBHelper.java      // Database operations only
SMSNotificationManager.java // SMS functionality only
PasswordUtils.java          // Password hashing utilities

// ❌ BAD - God class doing everything
MainActivity.java  // Login + Database + SMS + UI + Everything
```

##### Recommended Class Sizes
- Activities: 200-400 lines
- Database Helper: 300-500 lines
- Adapters: 100-200 lines
- Utility Classes: 50-150 lines

**If class exceeds these limits, consider refactoring into separate classes**

#### 5. Consistent Style

##### Indentation and Spacing
```java
// ✅ GOOD - Consistent 4-space indentation
public void saveWeight(double weight) {
    if (weight > 0) {
        dbHelper.insertWeightEntry(userId, weight, date, notes);
        refreshList();
    }
}

// ❌ BAD - Inconsistent spacing
public void saveWeight(double weight){
  if(weight>0){
        dbHelper.insertWeightEntry(userId,weight,date,notes);
      refreshList();
    }
}
```

##### Bracket Style (K&R Style)
```java
// ✅ GOOD - Opening brace on same line
if (condition) {
    doSomething();
}

// ❌ BAD - Opening brace on new line (not Java convention)
if (condition) 
{
    doSomething();
}
```

### Code Review Checklist

#### Before Submission, Verify:
- [ ] All classes have descriptive names
- [ ] All methods have descriptive names (verb-based)
- [ ] All variables have meaningful names (no single letters except loops)
- [ ] Complex logic includes explanatory comments
- [ ] Each class has a Javadoc comment explaining its purpose
- [ ] Public methods have Javadoc comments with @param and @return
- [ ] Constants are properly named in UPPER_SNAKE_CASE
- [ ] No commented-out code blocks (remove dead code)
- [ ] Consistent indentation throughout (4 spaces)
- [ ] No magic numbers (use named constants instead)
- [ ] Error handling is present (try-catch where appropriate)
- [ ] No System.out.println() (use Log.d/i/e instead)

### Success Criteria
- [ ] All classes are concise (under recommended line counts)
- [ ] Naming conventions are consistent throughout codebase
- [ ] Style is consistent (spacing, brackets, indentation)
- [ ] In-line comments explain WHY, not WHAT
- [ ] Code follows SOLID principles where applicable
- [ ] No duplicate code (DRY principle)
- [ ] Methods are focused and do one thing well

---

## 📄 REQUIREMENT 5: APP LAUNCH PLAN (20 Points)

### Document Structure

#### Required Sections

##### 1. App Store Description & Icon
**What to Include:**
- Full app description (250-4000 characters)
- Short description/subtitle (80 characters)
- Icon design rationale
- Screenshots strategy
- Feature graphics
- Promotional content

##### 2. Android Version Compatibility
**What to Include:**
- Minimum SDK version with justification
- Target SDK version
- Device compatibility considerations
- Features requiring newer Android versions
- Fallback strategies for older versions

##### 3. Permissions Strategy
**What to Include:**
- Complete list of requested permissions
- Justification for each permission
- User communication strategy
- Optional vs. required permissions
- Privacy policy implications

##### 4. Monetization Strategy
**What to Include:**
- Revenue model selection
- Pricing justification
- Ad implementation (if applicable)
- In-app purchases (if applicable)
- Freemium vs. paid strategy

### Detailed Content Requirements

#### Section 1: App Store Presence

##### App Description Template
```markdown
[Opening Hook - 1 sentence describing main benefit]

[Problem Statement - 2-3 sentences about user pain points]

[Solution Overview - 2-3 sentences about how app solves problem]

KEY FEATURES:
• Feature 1 with specific benefit
• Feature 2 with specific benefit
• Feature 3 with specific benefit
• Feature 4 with specific benefit
• Feature 5 with specific benefit

[Privacy/Security Statement - 1-2 sentences]

[Call to Action - 1 sentence encouraging download]
```

##### Icon Design Requirements
- **Size**: 512x512 pixels (Google Play requirement)
- **Format**: 32-bit PNG with alpha channel
- **Design**: Simple, recognizable at small sizes
- **Branding**: Matches app theme and colors
- **Testing**: View at 48x48, 96x96, 192x192 to ensure clarity

##### Icon Rationale Questions to Answer
1. What visual metaphor represents weight tracking?
2. How does the icon reflect the "Weigh to Go!" brand?
3. Why these colors specifically?
4. How does it stand out in the app store?
5. Is it recognizable at thumbnail size?

##### Screenshot Strategy (4-8 screenshots required)
1. **Login Screen**: Shows clean, professional entry point
2. **Main Dashboard**: Displays weight tracking grid with data
3. **Add Entry**: Shows easy data input process
4. **Goal Achievement**: Demonstrates notification/celebration
5. **SMS Settings**: Shows permission handling
6. **Progress Chart** (if implemented): Visual tracking
7. **Multiple Entries**: Shows app with realistic data
8. **Empty State**: Shows new user experience

#### Section 2: Android Compatibility

##### Version Selection Framework
```markdown
MINIMUM SDK (Recommended: API 26 - Android 8.0)

Rationale:
- Market Share: Covers 95%+ of active devices
- Feature Support: All required features available
- Maintenance: No outdated API workarounds needed
- Security: Modern security features available

Features Requiring API 26+:
- Notification Channels (API 26+)
- Autofill Framework (API 26+)
- Background Execution Limits (API 26+)

Devices Excluded:
- Android 7.x and older (5% market share)
- Primarily older devices unlikely to use health apps
```

##### Target SDK (Recommended: API 34 - Android 14)
```markdown
TARGET SDK: API 34 (Android 14)

Rationale:
- Google Play Requirement: Must target recent API
- Latest Features: Access to newest Android capabilities
- Security: Latest security improvements
- Performance: Optimized for current devices

New Features in Android 14:
- Predictive back gesture
- Health Connect integration
- Enhanced privacy controls
- Improved notification management
```

##### Compatibility Testing Plan
```markdown
DEVICES TO TEST:
1. Pixel 6 (Android 14)
2. Samsung Galaxy S21 (Android 13)
3. OnePlus 9 (Android 12)
4. Budget device with Android 10

SCREEN SIZES TO TEST:
- Small (480x800)
- Normal (720x1280)
- Large (1080x1920)
- Extra Large (1440x2560)

ORIENTATION TESTING:
- Portrait mode (primary)
- Landscape mode (ensure graceful handling)
```

#### Section 3: Permissions Strategy

##### Permission Audit Template
```markdown
REQUESTED PERMISSIONS:

1. SEND_SMS (Dangerous - Requires runtime request)
   - Purpose: Send weight goal achievement notifications
   - Required: NO - App functions without this
   - Request Timing: When user enables notifications in settings
   - Fallback: In-app notifications only

2. INTERNET (Normal - Auto-granted)
   - Purpose: Future feature - sync data to cloud
   - Required: NO - Currently not used
   - Request Timing: Granted at install
   - Fallback: Local storage only

3. VIBRATE (Normal - Auto-granted)
   - Purpose: Notification feedback
   - Required: NO - Silent notifications still work
   - Request Timing: Granted at install
   - Fallback: No vibration

PERMISSIONS NOT REQUESTED:
- ACCESS_FINE_LOCATION - Not needed
- CAMERA - Not needed
- READ_CONTACTS - Not needed
- RECORD_AUDIO - Not needed
```

##### Permission Communication Strategy
```markdown
HOW WE COMMUNICATE PERMISSIONS TO USERS:

Before App Install:
- Clear description in app store listing
- "Requires SMS permission for optional notifications"
- Privacy policy link provided

First Launch:
- No permissions requested immediately
- App is fully functional without permissions
- Users explore app without barriers

When Enabling Notifications:
- Clear explanation screen BEFORE system dialog
- "We need SMS permission to send you notifications when you reach your goal"
- Option to skip and use in-app notifications instead

After Permission Denial:
- Graceful degradation - feature still works differently
- No repeated permission requests (respect user choice)
- Option to enable later in settings
```

##### Privacy Policy Considerations
```markdown
DATA COLLECTED:
- Username (stored locally)
- Password (hashed, stored locally)
- Weight entries (stored locally)
- Phone number (if user provides for SMS)

DATA NOT COLLECTED:
- Location
- Contacts
- Personal information beyond username
- Usage analytics
- Advertising identifiers

DATA SHARING:
- None - all data stays on device
- No third-party services
- No cloud backup (currently)

USER RIGHTS:
- Delete account and all data
- Export data (future feature)
- No data sold or shared
```

#### Section 4: Monetization Strategy

##### Revenue Model Options

**Option 1: Free with Ads**
```markdown
MODEL: Ad-supported (Free app with banner/interstitial ads)

Implementation:
- Google AdMob integration
- Banner ad on main dashboard (bottom)
- Interstitial ad every 5 weight entries
- Rewarded video ad to unlock premium features

Projected Revenue:
- $0.50-2.00 per 1000 impressions
- 1000 daily active users = $15-60/month

Pros:
- Lower barrier to entry
- Larger user base
- Passive revenue stream

Cons:
- User experience degradation
- Privacy concerns with ad networks
- Requires ongoing ad management
```

**Option 2: Freemium**
```markdown
MODEL: Freemium (Free basic app + paid premium features)

Free Features:
- Weight tracking (unlimited entries)
- Basic goal setting
- Manual data entry
- Local data storage

Premium Features ($2.99/month or $19.99/year):
- SMS notifications
- Weight trend charts and analytics
- Photo progress tracking
- Data export to CSV
- Cloud backup and sync
- Multiple goal tracking
- Custom reminders

Projected Revenue:
- 5% conversion rate assumed
- 1000 users → 50 premium → $150/month

Pros:
- Sustainable long-term revenue
- Better user experience (no ads)
- Encourages engagement for conversion

Cons:
- Requires feature development
- Lower initial user base
- Need subscription management
```

**Option 3: Paid App (One-time purchase)**
```markdown
MODEL: Paid Download ($1.99-4.99 one-time purchase)

Features:
- All features included
- No ads
- No subscription
- Lifetime access

Projected Revenue:
- 100 downloads/month at $2.99 = $299/month
- Google Play takes 30% = $209 net

Pros:
- Simple revenue model
- No ongoing billing
- Users own the app

Cons:
- Much lower download numbers
- No recurring revenue
- Difficult to compete with free apps
```

**Option 4: Completely Free**
```markdown
MODEL: Free (No monetization)

Rationale:
- Portfolio/learning project
- Build user base first
- Monetize later based on user feedback
- Focus on quality over revenue

Future Monetization:
- Add premium features after 1000+ users
- Introduce ads after establishing user base
- Sell to larger health app company

Pros:
- Maximum user growth
- No compromise on features
- No privacy concerns from ads

Cons:
- No revenue
- Costs for hosting (if added)
- Opportunity cost
```

##### Recommended Strategy for Weigh to Go!
```markdown
PHASE 1 (Months 1-3): Completely Free
- Focus on building user base
- Gather user feedback
- Fix bugs and improve features
- Build reputation and reviews

PHASE 2 (Months 4-6): Introduce Premium
- Keep core features free
- Add premium tier at $2.99/month
- Premium includes: SMS, charts, cloud sync
- Grandfather early users with discount

PHASE 3 (Months 7-12): Optimize
- Analyze conversion rates
- A/B test pricing
- Add requested premium features
- Consider ads in free tier if premium adoption low

LONG-TERM:
- Build sustainable freemium model
- Aim for 5-10% conversion rate
- 10,000 users → 500 premium → $1500/month revenue
```

### Launch Plan Document Outline

```markdown
# Weigh to Go! - App Launch Plan
## By Rick Goshen

### Executive Summary
[1 paragraph overview of launch strategy]

### 1. App Store Presence
#### 1.1 Description
[Full app description]

#### 1.2 Short Description
[80-character subtitle]

#### 1.3 Icon Design
[Icon image + design rationale]

#### 1.4 Screenshots
[List of 6-8 screenshots with captions]

#### 1.5 Feature Graphic
[Description of store banner image]

### 2. Technical Specifications
#### 2.1 Android Version Compatibility
- Minimum SDK: API 26 (Android 8.0)
- Target SDK: API 34 (Android 14)
- [Rationale for each]

#### 2.2 Device Compatibility
[Phones, tablets, screen sizes supported]

#### 2.3 Testing Plan
[Devices and scenarios tested]

### 3. Permissions & Privacy
#### 3.1 Requested Permissions
[Table of permissions with justifications]

#### 3.2 Permission Strategy
[How and when permissions are requested]

#### 3.3 Privacy Policy
[Link to policy + key privacy points]

### 4. Monetization Strategy
#### 4.1 Revenue Model
[Selected model with full justification]

#### 4.2 Pricing Strategy
[Specific pricing with competitive analysis]

#### 4.3 Revenue Projections
[Conservative estimates based on market research]

### 5. Marketing & User Acquisition
#### 5.1 Target Audience
[Demographics and user personas]

#### 5.2 Marketing Channels
[How users will discover the app]

#### 5.3 Launch Timeline
[Pre-launch, launch day, post-launch activities]

### 6. Post-Launch Plan
#### 6.1 User Support
[How users get help]

#### 6.2 Update Schedule
[Planned feature releases]

#### 6.3 Success Metrics
[KPIs to track: downloads, retention, ratings]

### Conclusion
[Final summary and next steps]
```

### Success Criteria
- [ ] App description is compelling and clearly explains value
- [ ] Icon is professional and recognizable
- [ ] Minimum SDK is justified with market share data
- [ ] Target SDK is current (API 34)
- [ ] All permissions are listed with clear justifications
- [ ] Only necessary permissions are requested
- [ ] Monetization strategy is clearly explained
- [ ] Revenue projections are realistic
- [ ] Document is 2-3 pages, double-spaced, Times New Roman 12pt
- [ ] Writing is clear, professional, and well-organized

---

## ✅ COMPREHENSIVE IMPLEMENTATION CHECKLIST

### Phase 1: Project Setup & Database Foundation
- [ ] Open Project Two in Android Studio
- [ ] Create new branch: `project-three-development`
- [ ] Update build.gradle dependencies (Room if using)
- [ ] Create database package structure
- [ ] Implement WeighToGoDBHelper.java
- [ ] Create User.java model class
- [ ] Create WeightEntry.java model class
- [ ] Create GoalWeight.java model class
- [ ] Test database creation in emulator
- [ ] Verify tables created correctly using Database Inspector

### Phase 2: Login Functionality
- [ ] Implement password hashing utility (BCrypt/SHA-256)
- [ ] Add database methods for user authentication
- [ ] Update LoginActivity.java with functional code
- [ ] Implement validateCredentials() method
- [ ] Implement createNewUser() method
- [ ] Add SharedPreferences for session management
- [ ] Implement auto-login on app launch
- [ ] Add logout functionality
- [ ] Test new user registration flow
- [ ] Test returning user login flow
- [ ] Test invalid credentials handling
- [ ] Test duplicate username prevention

### Phase 3: Weight Tracking CRUD Operations
- [ ] Implement getAllWeightEntries() in database helper
- [ ] Implement insertWeightEntry() in database helper
- [ ] Implement updateWeightEntry() in database helper
- [ ] Implement deleteWeightEntry() in database helper
- [ ] Update MainActivity.java with functional code
- [ ] Implement RecyclerView adapter (WeightEntryAdapter)
- [ ] Connect adapter to database queries
- [ ] Implement "Add Weight" functionality
- [ ] Implement "Delete Weight" functionality (required!)
- [ ] Implement "Edit Weight" functionality
- [ ] Add date picker for weight entries
- [ ] Calculate weight difference from previous entry
- [ ] Test adding multiple weight entries
- [ ] Test editing existing entry
- [ ] Test deleting entry
- [ ] Verify only current user's data shows

### Phase 4: Goal Weight Feature
- [ ] Create goal weight UI (MainActivity or separate activity)
- [ ] Implement goal setting in database
- [ ] Implement goal update functionality
- [ ] Display current goal on dashboard
- [ ] Calculate progress percentage
- [ ] Add visual progress indicator
- [ ] Test goal setting flow
- [ ] Test goal achievement detection

### Phase 5: SMS Notifications
- [ ] Verify AndroidManifest.xml has SEND_SMS permission
- [ ] Verify AndroidManifest.xml has telephony feature
- [ ] Create SMSNotificationManager.java utility class
- [ ] Implement checkSmsPermission() method
- [ ] Implement requestSmsPermission() using ActivityResultContracts
- [ ] Update SettingsActivity.java with permission handling
- [ ] Implement sendSMS() method
- [ ] Add phone number input/storage
- [ ] Implement notification preference toggles
- [ ] Connect goal achievement to SMS trigger
- [ ] Add "Open App Settings" for permanently denied permission
- [ ] Test permission request flow
- [ ] Test SMS sending when permitted
- [ ] Test app functionality when permission denied
- [ ] Verify no SMS sent when permission not granted

### Phase 6: Code Quality & Best Practices
- [ ] Add Javadoc comments to all public classes
- [ ] Add Javadoc comments to all public methods
- [ ] Add inline comments for complex logic
- [ ] Verify all naming conventions are consistent
- [ ] Check for magic numbers - replace with constants
- [ ] Remove all System.out.println() - use Log instead
- [ ] Remove all commented-out dead code
- [ ] Verify consistent indentation (4 spaces)
- [ ] Ensure each class has single responsibility
- [ ] Check class sizes - refactor if too large
- [ ] Run Android Lint - fix all warnings
- [ ] Test error handling (null checks, try-catch)
- [ ] Add input validation on all forms

### Phase 7: Testing & Debugging
- [ ] Test on emulator (Pixel 6, API 34)
- [ ] Test on physical device (if available)
- [ ] Test landscape orientation
- [ ] Test different screen sizes
- [ ] Test with multiple users (login/logout flow)
- [ ] Test database with 50+ weight entries
- [ ] Test SMS permission scenarios:
    - [ ] Grant permission
    - [ ] Deny permission
    - [ ] Deny and select "Don't ask again"
- [ ] Test app restart (data persistence)
- [ ] Test edge cases:
    - [ ] Empty database
    - [ ] Very long username/password
    - [ ] Special characters in input
    - [ ] Future dates
    - [ ] Negative weights
- [ ] Fix all crashes
- [ ] Fix all UI issues
- [ ] Verify no console errors

### Phase 8: App Launch Plan Document
- [ ] Research competing weight tracking apps
- [ ] Analyze market pricing strategies
- [ ] Draft app store description
- [ ] Create icon mockup/design
- [ ] Plan screenshot sequence
- [ ] Research Android version distribution stats
- [ ] Document all requested permissions
- [ ] Write permission justifications
- [ ] Select monetization strategy
- [ ] Calculate revenue projections
- [ ] Write complete launch plan (2-3 pages)
- [ ] Proofread for grammar/spelling
- [ ] Format: 12pt Times New Roman, double-spaced
- [ ] Save as: Rick_Goshen_WeightToGo_LaunchPlan.docx

### Phase 9: Final Submission Preparation
- [ ] Run final tests on all features
- [ ] Clean project (Build → Clean Project)
- [ ] Remove any debug code
- [ ] Remove unused resources
- [ ] Generate signed APK (optional but recommended)
- [ ] Test APK installation on device
- [ ] Create ZIP of entire project folder
- [ ] Name ZIP: Rick_Goshen_WeightToGo_Final.zip
- [ ] Verify ZIP contains all necessary files
- [ ] Test ZIP extraction and project opening
- [ ] Upload ZIP file to submission portal
- [ ] Upload Launch Plan document to submission portal
- [ ] Submit before deadline!

---

## 📊 SELF-ASSESSMENT RUBRIC

### Before submitting, grade yourself honestly:

#### Login (20 points)
- [ ] **Exceeds (20pts)**: Secure authentication with hashing, session management, input validation, auto-login, professional error handling
- [ ] **Meets (17pts)**: Users can create accounts and login, passwords stored in database, basic error handling
- [ ] **Partial (11pts)**: Login/registration attempted but with issues (plain text passwords, no validation, etc.)
- [ ] **Does Not Meet (0pts)**: Login not functional

**My Score: _____/20**

#### Database (20 points)
- [ ] **Exceeds (20pts)**: Full CRUD operations, efficient queries, proper relationships, user-specific data, delete button works perfectly
- [ ] **Meets (17pts)**: Database created, data displays in grid, users can add/remove items, basic functionality works
- [ ] **Partial (11pts)**: Database exists but operations incomplete or delete button missing
- [ ] **Does Not Meet (0pts)**: Database not functional

**My Score: _____/20**

#### SMS Notifications (20 points)
- [ ] **Exceeds (20pts)**: Runtime permissions modern implementation, graceful permission denial, notification types configurable, app fully functional without SMS
- [ ] **Meets (17pts)**: Permission request works, SMS sends when granted, app continues if denied
- [ ] **Partial (11pts)**: SMS attempted but issues with permission flow or app breaks when denied
- [ ] **Does Not Meet (0pts)**: SMS not implemented

**My Score: _____/20**

#### Coding Best Practices (15 points)
- [ ] **Exceeds (15pts)**: Excellent comments, perfect naming conventions, consistent style, concise classes, professional code organization
- [ ] **Meets (13pts)**: Adequate comments, consistent naming, reasonable organization
- [ ] **Partial (8pts)**: Some comments/naming but inconsistent or confusing
- [ ] **Does Not Meet (0pts)**: Poor code quality

**My Score: _____/15**

#### Launch Plan (20 points)
- [ ] **Exceeds (20pts)**: Comprehensive, well-researched plan with competitive analysis, clear strategy, professional presentation
- [ ] **Meets (17pts)**: Addresses all required components (description, icon, versions, permissions, monetization)
- [ ] **Partial (11pts)**: Launch plan incomplete or missing key components
- [ ] **Does Not Meet (0pts)**: Launch plan not submitted

**My Score: _____/20**

#### Clear Communication (5 points)
- [ ] **Exceeds (5pts)**: Exceptionally clear documentation, well-organized code, professional communication
- [ ] **Meets (4pts)**: Consistently clear and organized
- [ ] **Partial (3pts)**: Some organization issues
- [ ] **Does Not Meet (0pts)**: Disorganized or unclear

**My Score: _____/5**

### TOTAL SCORE: _____/100

---

## 🎯 SUCCESS METRICS

### Minimum Requirements for Submission:
1. ✅ App compiles without errors
2. ✅ App installs on emulator/device
3. ✅ All three core features functional (Login, Database, SMS)
4. ✅ Launch plan document completed and formatted correctly
5. ✅ Project ZIP file named correctly

### "Exceeds Expectations" Indicators:
1. 🌟 Code is well-organized and professionally documented
2. 🌟 Error handling is comprehensive (no crashes)
3. 🌟 UI is polished and responsive
4. 🌟 Security best practices followed (password hashing)
5. 🌟 Modern Android patterns used (ActivityResultContracts, Room, etc.)
6. 🌟 Launch plan shows thorough market research
7. 🌟 App demonstrates attention to detail and quality

### Common Pitfalls to Avoid:
- ❌ Storing passwords as plain text in database
- ❌ Delete button on grid rows not functional
- ❌ App crashes when SMS permission denied
- ❌ AndroidManifest missing required permissions
- ❌ Poor or no code comments
- ❌ Inconsistent naming conventions
- ❌ Launch plan under 2 pages or not properly formatted
- ❌ Requesting unnecessary permissions
- ❌ Not testing on actual device/emulator

---

## 📚 ESSENTIAL RESOURCES

### Android Developer Documentation
- **SQLite Database**: https://developer.android.com/training/data-storage/sqlite
- **Room Persistence Library**: https://developer.android.com/training/data-storage/room
- **App Permissions**: https://developer.android.com/guide/topics/permissions/overview
- **Runtime Permissions**: https://developer.android.com/training/permissions/requesting
- **SMS Manager**: https://developer.android.com/reference/android/telephony/SmsManager
- **SharedPreferences**: https://developer.android.com/training/data-storage/shared-preferences
- **RecyclerView**: https://developer.android.com/guide/topics/ui/layout/recyclerview

### Security Best Practices
- **Password Hashing**: Use BCrypt or Argon2
- **Input Validation**: Prevent SQL injection
- **Secure Storage**: Never log sensitive data

### Google Play Requirements
- **Target API Level**: Must target API 33+ for new apps
- **Privacy Policy**: Required if app handles personal data
- **App Signing**: Must be signed for distribution

### Course Materials
- **CS 360 Chapter 1**: Introduction to Android
- **CS 360 Chapter 2**: Layouts and Widgets
- **CS 360 Chapter 3**: Activities and Intents
- **CS 360 Chapter 4**: Menus, Dialogs, and Touch

---

## 🤝 SUPPORT & TROUBLESHOOTING

### Common Issues & Solutions

#### Issue: Database not creating
**Solution**: Check logcat for SQL errors, verify onCreate() is called, use Database Inspector tool

#### Issue: SMS not sending
**Solution**: Verify permission granted, check phone number format, test with SmsManager logs, verify device has telephony feature

#### Issue: App crashes on permission denial
**Solution**: Add null checks, verify UI updates don't assume permission granted, test denial flow

#### Issue: Can't find Database Inspector
**Solution**: View → Tool Windows → App Inspection → Database Inspector (requires device/emulator running)

#### Issue: Password hashing seems complex
**Solution**: Use simple SHA-256 with salt, or import BCrypt library: `implementation 'org.mindrot:jbcrypt:0.4'`

#### Issue: RecyclerView not showing data
**Solution**: Verify adapter is set, check database has data, add logs to adapter, ensure notifyDataSetChanged() is called

### When to Ask for Help
- [ ] Tried debugging for 30+ minutes
- [ ] Searched StackOverflow and Android documentation
- [ ] Checked course materials
- [ ] Reviewed error logs in Logcat
- [ ] Attempted at least 2 different solutions

### What to Include When Asking for Help
1. Specific error message or behavior
2. Code snippet (relevant portion only)
3. What you've already tried
4. Android Studio version and SDK level
5. Device/emulator being used

---

## 🎓 LEARNING OUTCOMES

### By completing this project, you will have demonstrated:

1. **Mobile Application Development**
   - Full Android app lifecycle from concept to launch-ready code
   - UI design translated to functional implementation
   - Multi-screen navigation and data flow

2. **Database Management**
   - SQLite database design and implementation
   - CRUD operations (Create, Read, Update, Delete)
   - Data relationships and foreign keys
   - User-specific data isolation

3. **Android Security**
   - Runtime permission handling
   - Secure password storage (hashing)
   - Input validation and sanitization
   - User privacy considerations

4. **Professional Development Practices**
   - Clean code principles
   - Consistent naming conventions
   - Comprehensive documentation
   - Code organization and architecture

5. **Business & Launch Planning**
   - Market research and competitive analysis
   - Monetization strategy development
   - Privacy and compliance considerations
   - Go-to-market planning

---

## 🏆 PORTFOLIO PREPARATION

### Showcasing This Project

This project is an excellent portfolio piece. Consider:

1. **GitHub Repository**
   - Create public repo with professional README
   - Include screenshots of all screens
   - Document features and tech stack
   - Add MIT license for sharing

2. **Demo Video**
   - Screen recording showing all features
   - Narrate key functionality
   - Upload to YouTube (unlisted if preferred)
   - Add link to resume/portfolio

3. **Blog Post**
   - Write about development process
   - Discuss challenges and solutions
   - Share lessons learned
   - Post on Medium or personal blog

4. **Resume Bullet Points**
   - "Developed Android weight tracking app using Java, XML, and SQLite"
   - "Implemented secure authentication with password hashing and session management"
   - "Integrated SMS notifications with runtime permission handling"
   - "Designed and executed comprehensive app launch strategy"

---

## 📝 FINAL NOTES

### Remember:
- Start early - this is a substantial project
- Test frequently - don't wait until the end
- Commit regularly - use version control
- Ask questions - instructors are here to help
- Be proud - this is a complete, functional app!

### Time Estimates:
- **Database Implementation**: 6-8 hours
- **Login System**: 4-6 hours
- **CRUD Operations**: 6-8 hours
- **SMS Notifications**: 4-6 hours
- **Code Quality & Documentation**: 4-6 hours
- **Testing & Debugging**: 6-8 hours
- **Launch Plan Document**: 4-6 hours
- **Total**: 34-48 hours

**Recommended Schedule**: Start 2 weeks before due date, work 3-4 hours per day

---

## ✅ PRE-SUBMISSION CHECKLIST

### Code Submission:
- [ ] Project compiles without errors
- [ ] All activities launch correctly
- [ ] Login/registration works
- [ ] Database CRUD operations work
- [ ] Delete button functional on grid rows
- [ ] SMS permission request works
- [ ] App functions without SMS permission
- [ ] No crashes during testing
- [ ] Code follows naming conventions
- [ ] Code includes appropriate comments
- [ ] AndroidManifest includes all permissions
- [ ] Project folder is complete
- [ ] ZIP file created: `Rick_Goshen_WeightToGo_Final.zip`
- [ ] APK generated (optional)

### Launch Plan Document:
- [ ] 2-3 pages in length
- [ ] Double-spaced
- [ ] 12-point Times New Roman font
- [ ] 1-inch margins
- [ ] App description complete
- [ ] Icon design discussed
- [ ] Android versions justified
- [ ] All permissions listed and justified
- [ ] Monetization strategy explained
- [ ] Professional writing quality
- [ ] Saved as: `Rick_Goshen_WeightToGo_LaunchPlan.docx`

### Ready to Submit!
- [ ] Both files prepared and named correctly
- [ ] Verified ZIP extracts properly
- [ ] Tested project opens in Android Studio
- [ ] Read through launch plan document one final time
- [ ] Submitted before deadline

---

**Good luck, Rick! This is the culmination of everything you've learned in CS 360. You've got this—pound for pound! 🎉**

---

**END OF PROJECT THREE REQUIREMENTS**

*Weigh to Go! - You've got this—pound for pound.* 🏋️‍♂️📱
