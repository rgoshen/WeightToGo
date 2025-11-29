# 🎉 WEIGH TO GO! - Project Two: UI Design Implementation
## CS 360 Mobile Architecture & Programming - Complete Build Requirements

**Student**: Rick Goshen  
**App Name**: Weigh to Go!  
**Tagline**: "You've got this—pound for pound."  
**Platform**: Android (Java/XML)  
**IDE**: Android Studio  
**Target SDK**: API 34 (Android 14)  
**Minimum SDK**: API 26 (Android 8.0)

---

## 📋 PROJECT OVERVIEW

### Competency Being Demonstrated
**Apply user-centered design principles and industry standards in the development of a mobile application**

### Project Context
The Weigh to Go! app proposal has been approved by Mobile2App. This project requires building the **complete UI design** in Android Studio using XML layouts. The UI will **not be functional yet**—only the visual/layout layer is required. Functional code will be implemented in Project Three.

### Submission Requirements
- **Format**: ZIP file of complete Android Studio project
- **Naming**: `Rick_Goshen_WeightTracker.zip`
- **Contents**: Full Android Studio project folder structure

---

## 🎯 GRADING RUBRIC ALIGNMENT

| Criterion | Weight | Requirements |
|-----------|--------|--------------|
| **Login Screen** | 25% | UI with appropriate design elements for user login |
| **Database Grid Screen** | 25% | UI to display database information as a grid |
| **SMS Notifications** | 30% | UI for SMS permissions and user notification settings |
| **Visual Hierarchy** | 20% | Focus order, grouping, transitions, consistent theme |

**To Achieve "Exceeds Expectations"**: Each criterion must be completed in an "exceptionally clear, insightful, sophisticated, or creative manner"

---

## 📱 REQUIRED SCREENS (Minimum 3)

### Screen 1: Login/Registration Screen (`activity_login.xml`)
### Screen 2: Main Dashboard/Weight Grid Screen (`activity_main.xml`)  
### Screen 3: SMS Settings/Permissions Screen (`activity_settings.xml` or integrated dialog)

**Optional Additional Screens**:
- Weight Entry Screen (`activity_weight_entry.xml`)
- Goal Setting Screen (`activity_goal.xml`)

---

## 🔐 SCREEN 1: LOGIN/REGISTRATION

### Required Elements (Per Rubric)

| Element | Requirement | Implementation |
|---------|-------------|----------------|
| **Username Field** | Text input for username | `EditText` with `inputType="text"` |
| **Password Field** | Text input with obscured text (dots) | `EditText` with `inputType="textPassword"` |
| **Login Button** | Submit credentials | `Button` with onClick handler stub |
| **Create Account Button** | Register new user | `Button` to add username/password to database |
| **Visual Appeal** | Professional, intuitive design | Material Design 3 components |

### Detailed Specifications

#### Layout Structure
```
ConstraintLayout (root)
├── Header Section (gradient background)
│   ├── App Icon (ImageView)
│   ├── App Name TextView ("Weigh to Go!")
│   └── Tagline TextView ("You've got this—pound for pound.")
├── Auth Card (CardView)
│   ├── Tab Toggle (Sign In / Create Account)
│   ├── Username/Email EditText
│   ├── Password EditText (inputType="textPassword")
│   ├── Forgot Password TextView (link)
│   ├── Primary Action Button (Sign In / Create Account)
│   ├── Divider with "or continue with"
│   └── Social Login Buttons (optional)
└── Terms & Privacy Footer
```

#### Color Scheme (Health/Wellness Theme)
| Element | Color | Hex |
|---------|-------|-----|
| Primary Teal | Headers, active states | `#00897B` |
| Primary Dark | Gradient end, pressed | `#00695C` |
| Primary Light | Borders, secondary | `#4DB6AC` |
| Accent Green | Success states | `#4CAF50` |
| Error Red | Error states | `#F44336` |
| Surface White | Cards, inputs | `#FFFFFF` |
| Background | Page background | `#F5F5F5` |
| Text Primary | Headlines | `#212121` |
| Text Secondary | Labels, hints | `#757575` |

#### Typography
| Element | Font | Size | Weight |
|---------|------|------|--------|
| App Name | Poppins | 32sp | Bold |
| Screen Title | Poppins | 18sp | SemiBold |
| Button Text | Poppins | 16sp | SemiBold |
| Input Text | Source Sans Pro | 16sp | Regular |
| Labels | Source Sans Pro | 13sp | Regular |
| Body Text | Source Sans Pro | 14sp | Regular |

#### Component Specifications

**Username Input Field**
```xml
<com.google.android.material.textfield.TextInputLayout
    android:id="@+id/usernameInputLayout"
    style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="@string/hint_username"
    app:startIconDrawable="@drawable/ic_person"
    app:boxCornerRadiusTopStart="14dp"
    app:boxCornerRadiusTopEnd="14dp"
    app:boxCornerRadiusBottomStart="14dp"
    app:boxCornerRadiusBottomEnd="14dp">

    <com.google.android.material.textfield.TextInputEditText
        android:id="@+id/usernameEditText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="text"
        android:maxLines="1" />
</com.google.android.material.textfield.TextInputLayout>
```

**Password Input Field (CRITICAL: Must obscure text)**
```xml
<com.google.android.material.textfield.TextInputLayout
    android:id="@+id/passwordInputLayout"
    style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="@string/hint_password"
    app:startIconDrawable="@drawable/ic_lock"
    app:endIconMode="password_toggle"
    app:boxCornerRadiusTopStart="14dp"
    app:boxCornerRadiusTopEnd="14dp"
    app:boxCornerRadiusBottomStart="14dp"
    app:boxCornerRadiusBottomEnd="14dp">

    <com.google.android.material.textfield.TextInputEditText
        android:id="@+id/passwordEditText"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="textPassword"
        android:maxLines="1" />
</com.google.android.material.textfield.TextInputLayout>
```

**Sign In Button**
```xml
<com.google.android.material.button.MaterialButton
    android:id="@+id/signInButton"
    android:layout_width="match_parent"
    android:layout_height="56dp"
    android:text="@string/btn_sign_in"
    android:textAllCaps="false"
    app:cornerRadius="16dp"
    app:backgroundTint="@color/primary_teal" />
```

**Create Account Button**
```xml
<com.google.android.material.button.MaterialButton
    android:id="@+id/createAccountButton"
    style="@style/Widget.MaterialComponents.Button.OutlinedButton"
    android:layout_width="match_parent"
    android:layout_height="56dp"
    android:text="@string/btn_create_account"
    android:textAllCaps="false"
    app:cornerRadius="16dp"
    app:strokeColor="@color/primary_teal" />
```

---

## 📊 SCREEN 2: DATABASE/WEIGHT GRID DISPLAY

### Required Elements (Per Rubric)

| Element | Requirement | Implementation |
|---------|-------------|----------------|
| **Data Grid** | Display weight entries | `RecyclerView` with grid layout |
| **Logical Labels/Headers** | Column headers for data | Header row with Date, Weight, Actions |
| **Add Data Button** | Add new weight entry | `FloatingActionButton` or `Button` |
| **Delete Button per Row** | Remove individual entries | `ImageButton` on each row item |
| **Data Modification** | Update weight/date values | Edit button or inline editing |
| **Input Fields** | Fields for adding data | Can be on same screen or separate |

### Detailed Specifications

#### Layout Structure
```
CoordinatorLayout (root)
├── AppBarLayout
│   └── MaterialToolbar
│       ├── Navigation Icon (menu)
│       ├── Title ("Weight History")
│       └── Action Icons (settings, notifications)
├── Content Area (NestedScrollView or direct)
│   ├── Progress Summary Card
│   │   ├── Current Weight Display
│   │   ├── Goal Weight Display
│   │   ├── Progress Bar
│   │   └── Quick Stats (Start, Current, Goal)
│   ├── Section Header ("Recent Entries")
│   └── RecyclerView (Weight Grid)
│       └── Weight Entry Items (repeating)
│           ├── Date TextView
│           ├── Weight TextView
│           ├── Change Indicator (trend arrow)
│           ├── Edit ImageButton
│           └── Delete ImageButton
├── FloatingActionButton (Add Entry)
└── BottomNavigationView (optional)
```

#### Grid/RecyclerView Item Layout (`item_weight_entry.xml`)
```xml
<?xml version="1.0" encoding="utf-8"?>
<com.google.android.material.card.MaterialCardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginHorizontal="16dp"
    android:layout_marginVertical="4dp"
    app:cardCornerRadius="12dp"
    app:cardElevation="2dp">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="16dp">

        <!-- Date Column -->
        <TextView
            android:id="@+id/dateTextView"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:text="Nov 25, 2025"
            android:textSize="14sp"
            android:textColor="@color/text_secondary"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintWidth_percent="0.3" />

        <!-- Weight Column -->
        <TextView
            android:id="@+id/weightTextView"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:text="172.5 lbs"
            android:textSize="18sp"
            android:textStyle="bold"
            android:textColor="@color/text_primary"
            app:layout_constraintStart_toEndOf="@id/dateTextView"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintWidth_percent="0.35" />

        <!-- Trend Indicator -->
        <ImageView
            android:id="@+id/trendIndicator"
            android:layout_width="24dp"
            android:layout_height="24dp"
            android:src="@drawable/ic_trending_down"
            android:contentDescription="@string/trend_down"
            app:tint="@color/accent_green"
            app:layout_constraintStart_toEndOf="@id/weightTextView"
            app:layout_constraintTop_toTopOf="parent" />

        <!-- Edit Button -->
        <ImageButton
            android:id="@+id/editButton"
            android:layout_width="40dp"
            android:layout_height="40dp"
            android:src="@drawable/ic_edit"
            android:background="?attr/selectableItemBackgroundBorderless"
            android:contentDescription="@string/edit_entry"
            app:tint="@color/text_secondary"
            app:layout_constraintEnd_toStartOf="@id/deleteButton"
            app:layout_constraintTop_toTopOf="parent" />

        <!-- DELETE BUTTON (REQUIRED) -->
        <ImageButton
            android:id="@+id/deleteButton"
            android:layout_width="40dp"
            android:layout_height="40dp"
            android:src="@drawable/ic_delete"
            android:background="?attr/selectableItemBackgroundBorderless"
            android:contentDescription="@string/delete_entry"
            app:tint="@color/error_red"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

    </androidx.constraintlayout.widget.ConstraintLayout>
</com.google.android.material.card.MaterialCardView>
```

#### Header Row for Grid
```xml
<!-- Grid Header Row -->
<LinearLayout
    android:id="@+id/gridHeader"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:paddingHorizontal="16dp"
    android:paddingVertical="8dp"
    android:background="@color/surface_variant">

    <TextView
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="0.3"
        android:text="@string/header_date"
        android:textStyle="bold"
        android:textSize="12sp"
        android:textColor="@color/text_secondary" />

    <TextView
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="0.35"
        android:text="@string/header_weight"
        android:textStyle="bold"
        android:textSize="12sp"
        android:textColor="@color/text_secondary" />

    <TextView
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="0.15"
        android:text="@string/header_trend"
        android:textStyle="bold"
        android:textSize="12sp"
        android:textColor="@color/text_secondary"
        android:gravity="center" />

    <TextView
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="0.2"
        android:text="@string/header_actions"
        android:textStyle="bold"
        android:textSize="12sp"
        android:textColor="@color/text_secondary"
        android:gravity="center" />
</LinearLayout>
```

#### Add Entry FloatingActionButton
```xml
<com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
    android:id="@+id/addEntryFab"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="bottom|end"
    android:layout_margin="16dp"
    android:text="@string/add_weight"
    app:icon="@drawable/ic_add"
    app:backgroundTint="@color/primary_teal" />
```

---

## 📲 SCREEN 3: SMS NOTIFICATIONS & PERMISSIONS

### Required Elements (Per Rubric)

| Element | Requirement | Implementation |
|---------|-------------|----------------|
| **AndroidManifest** | State telephony feature need | `<uses-feature>` declaration |
| **AndroidManifest** | SEND_SMS permission | `<uses-permission>` declaration |
| **Permission Check UI** | Check SMS permission before sending | Permission status indicator |
| **Permission Request** | Trigger permission request if not granted | Request button/dialog |
| **User Response Display** | Show status based on permission grant/deny | Status cards/indicators |
| **Graceful Degradation** | App functions without SMS if denied | Clear messaging about limited functionality |

### Manifest Requirements

#### AndroidManifest.xml Additions
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <!-- SMS Permission (REQUIRED) -->
    <uses-permission android:name="android.permission.SEND_SMS" />
    
    <!-- Telephony Feature Declaration (REQUIRED) -->
    <uses-feature
        android:name="android.hardware.telephony"
        android:required="false" />
    
    <!-- Additional recommended permissions -->
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
    <uses-permission android:name="android.permission.RECEIVE_SMS" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.WeighToGo">
        
        <!-- Activities here -->
        
    </application>
</manifest>
```

### UI Layout Specifications

#### Layout Structure
```
ConstraintLayout (root)
├── AppBarLayout
│   └── MaterialToolbar ("Notification Settings")
├── ScrollView
│   └── LinearLayout (vertical)
│       ├── SMS Permission Card
│       │   ├── Icon (message icon)
│       │   ├── Title ("SMS Notifications")
│       │   ├── Description (explain purpose)
│       │   ├── Permission Status Indicator
│       │   │   ├── Granted State (green checkmark)
│       │   │   └── Denied State (red X with request button)
│       │   └── Enable/Request Button
│       ├── Notification Types Card
│       │   ├── Goal Achievement Toggle
│       │   ├── Daily Reminder Toggle
│       │   └── Weekly Summary Toggle
│       ├── Phone Number Input Card (if SMS enabled)
│       │   ├── Phone Number EditText
│       │   └── Verify Button
│       └── Permission Denied Warning Card (conditional)
│           ├── Warning Icon
│           ├── Message explaining limited functionality
│           └── "Open Settings" Button
└── Save Button
```

#### SMS Permission Card Layout
```xml
<com.google.android.material.card.MaterialCardView
    android:id="@+id/smsPermissionCard"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="16dp"
    app:cardCornerRadius="16dp"
    app:cardElevation="4dp">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="20dp">

        <!-- SMS Icon -->
        <ImageView
            android:id="@+id/smsIcon"
            android:layout_width="48dp"
            android:layout_height="48dp"
            android:src="@drawable/ic_sms"
            app:tint="@color/primary_teal"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <!-- Title -->
        <TextView
            android:id="@+id/smsTitle"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginStart="16dp"
            android:text="@string/sms_notifications_title"
            android:textSize="18sp"
            android:textStyle="bold"
            android:textColor="@color/text_primary"
            app:layout_constraintStart_toEndOf="@id/smsIcon"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toTopOf="@id/smsIcon" />

        <!-- Description -->
        <TextView
            android:id="@+id/smsDescription"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginStart="16dp"
            android:layout_marginTop="4dp"
            android:text="@string/sms_notifications_description"
            android:textSize="14sp"
            android:textColor="@color/text_secondary"
            app:layout_constraintStart_toEndOf="@id/smsIcon"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toBottomOf="@id/smsTitle" />

        <!-- Permission Status Container -->
        <LinearLayout
            android:id="@+id/permissionStatusContainer"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:orientation="horizontal"
            android:gravity="center_vertical"
            android:padding="12dp"
            android:background="@drawable/bg_status_container"
            app:layout_constraintTop_toBottomOf="@id/smsDescription">

            <!-- Status Icon (changes based on permission) -->
            <ImageView
                android:id="@+id/permissionStatusIcon"
                android:layout_width="24dp"
                android:layout_height="24dp"
                android:src="@drawable/ic_check_circle"
                app:tint="@color/accent_green" />

            <!-- Status Text -->
            <TextView
                android:id="@+id/permissionStatusText"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:layout_marginStart="12dp"
                android:text="@string/permission_granted"
                android:textSize="14sp"
                android:textColor="@color/accent_green" />

        </LinearLayout>

        <!-- Request Permission Button (shown when permission denied) -->
        <com.google.android.material.button.MaterialButton
            android:id="@+id/requestPermissionButton"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:text="@string/enable_sms_notifications"
            android:textAllCaps="false"
            app:cornerRadius="12dp"
            app:layout_constraintTop_toBottomOf="@id/permissionStatusContainer"
            android:visibility="gone" />

    </androidx.constraintlayout.widget.ConstraintLayout>
</com.google.android.material.card.MaterialCardView>
```

#### Permission Denied Warning Card
```xml
<!-- Shown when user denies SMS permission -->
<com.google.android.material.card.MaterialCardView
    android:id="@+id/permissionDeniedCard"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="16dp"
    android:visibility="gone"
    app:cardBackgroundColor="@color/warning_background"
    app:cardCornerRadius="12dp"
    app:strokeColor="@color/warning_orange"
    app:strokeWidth="1dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="16dp">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:gravity="center_vertical">

            <ImageView
                android:layout_width="24dp"
                android:layout_height="24dp"
                android:src="@drawable/ic_warning"
                app:tint="@color/warning_orange" />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="12dp"
                android:text="@string/permission_denied_title"
                android:textStyle="bold"
                android:textColor="@color/warning_orange" />
        </LinearLayout>

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:text="@string/permission_denied_message"
            android:textSize="14sp"
            android:textColor="@color/text_secondary" />

        <com.google.android.material.button.MaterialButton
            android:id="@+id/openSettingsButton"
            style="@style/Widget.MaterialComponents.Button.TextButton"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:text="@string/open_app_settings"
            android:textAllCaps="false" />

    </LinearLayout>
</com.google.android.material.card.MaterialCardView>
```

#### Notification Type Settings
```xml
<!-- Goal Achievement Notification Toggle -->
<com.google.android.material.card.MaterialCardView
    android:id="@+id/notificationTypesCard"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="16dp"
    app:cardCornerRadius="16dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="16dp">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/notification_types"
            android:textSize="16sp"
            android:textStyle="bold"
            android:textColor="@color/text_primary" />

        <!-- Goal Achievement Toggle -->
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:gravity="center_vertical"
            android:paddingVertical="12dp">

            <LinearLayout
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:orientation="vertical">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="@string/goal_achievement"
                    android:textSize="14sp"
                    android:textColor="@color/text_primary" />

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="@string/goal_achievement_desc"
                    android:textSize="12sp"
                    android:textColor="@color/text_secondary" />
            </LinearLayout>

            <com.google.android.material.switchmaterial.SwitchMaterial
                android:id="@+id/goalAchievementSwitch"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:checked="true" />
        </LinearLayout>

        <View
            android:layout_width="match_parent"
            android:layout_height="1dp"
            android:background="@color/divider" />

        <!-- Daily Reminder Toggle -->
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:gravity="center_vertical"
            android:paddingVertical="12dp">

            <LinearLayout
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:orientation="vertical">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="@string/daily_reminder"
                    android:textSize="14sp"
                    android:textColor="@color/text_primary" />

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="@string/daily_reminder_desc"
                    android:textSize="12sp"
                    android:textColor="@color/text_secondary" />
            </LinearLayout>

            <com.google.android.material.switchmaterial.SwitchMaterial
                android:id="@+id/dailyReminderSwitch"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:checked="false" />
        </LinearLayout>

    </LinearLayout>
</com.google.android.material.card.MaterialCardView>
```

---

## 🎨 VISUAL HIERARCHY REQUIREMENTS

### Focus Order (Must Match User Task Flow)
1. **Login Screen**: Username → Password → Sign In Button → Create Account
2. **Dashboard Screen**: Progress Summary → Recent Entries → Add Button
3. **Settings Screen**: Permission Status → Enable Button → Notification Toggles

### Grouping Organization
- **Logical Card Grouping**: Related elements within MaterialCardViews
- **Section Headers**: Clear labels for content sections
- **Visual Separation**: Appropriate spacing and dividers
- **Consistent Padding**: 16dp standard padding, 8dp grid alignment

### Transitions (Consistent Between Screens)
- **Shared Element Transitions**: App logo/branding elements
- **Fade Transitions**: For content loading
- **Slide Transitions**: For navigation between screens

### Theme Consistency
- **Color Palette**: Health/wellness teal theme throughout
- **Typography**: Poppins for headers, Source Sans Pro for body
- **Elevation**: Consistent shadow hierarchy
- **Corner Radius**: 12-16dp for cards, 14-16dp for buttons

---

## 📁 PROJECT STRUCTURE

### Required Directory Structure
```
WeighToGo/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/rickgoshen/weightogo/
│   │   │   │   ├── activities/
│   │   │   │   │   ├── LoginActivity.java
│   │   │   │   │   ├── MainActivity.java
│   │   │   │   │   ├── WeightEntryActivity.java (optional)
│   │   │   │   │   └── SettingsActivity.java
│   │   │   │   ├── adapters/
│   │   │   │   │   └── WeightEntryAdapter.java
│   │   │   │   ├── database/
│   │   │   │   │   └── WeighToGoDBHelper.java
│   │   │   │   ├── models/
│   │   │   │   │   ├── User.java
│   │   │   │   │   ├── WeightEntry.java
│   │   │   │   │   └── GoalWeight.java
│   │   │   │   └── utils/
│   │   │   │       └── PasswordUtils.java
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_login.xml
│   │   │   │   │   ├── activity_main.xml
│   │   │   │   │   ├── activity_settings.xml
│   │   │   │   │   ├── activity_weight_entry.xml (optional)
│   │   │   │   │   ├── item_weight_entry.xml
│   │   │   │   │   └── dialog_add_weight.xml (optional)
│   │   │   │   ├── values/
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   ├── themes.xml
│   │   │   │   │   └── dimens.xml
│   │   │   │   ├── drawable/
│   │   │   │   │   ├── ic_person.xml
│   │   │   │   │   ├── ic_lock.xml
│   │   │   │   │   ├── ic_add.xml
│   │   │   │   │   ├── ic_edit.xml
│   │   │   │   │   ├── ic_delete.xml
│   │   │   │   │   ├── ic_sms.xml
│   │   │   │   │   ├── ic_check_circle.xml
│   │   │   │   │   ├── ic_warning.xml
│   │   │   │   │   ├── ic_trending_up.xml
│   │   │   │   │   ├── ic_trending_down.xml
│   │   │   │   │   ├── bg_gradient_header.xml
│   │   │   │   │   └── bg_status_container.xml
│   │   │   │   ├── mipmap-hdpi/
│   │   │   │   ├── mipmap-mdpi/
│   │   │   │   ├── mipmap-xhdpi/
│   │   │   │   ├── mipmap-xxhdpi/
│   │   │   │   └── mipmap-xxxhdpi/
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── gradle.properties
```

---

## 📝 RESOURCE FILES

### colors.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- Primary Colors -->
    <color name="primary_teal">#00897B</color>
    <color name="primary_dark">#00695C</color>
    <color name="primary_light">#4DB6AC</color>
    
    <!-- Accent Colors -->
    <color name="accent_green">#4CAF50</color>
    <color name="success_light">#E8F5E9</color>
    <color name="warning_orange">#FF9800</color>
    <color name="warning_background">#FFF3E0</color>
    <color name="error_red">#F44336</color>
    
    <!-- Surface Colors -->
    <color name="surface_white">#FFFFFF</color>
    <color name="surface_variant">#E0F2F1</color>
    <color name="background">#F5F5F5</color>
    
    <!-- Text Colors -->
    <color name="text_primary">#212121</color>
    <color name="text_secondary">#757575</color>
    <color name="text_hint">#BDBDBD</color>
    <color name="divider">#E0E0E0</color>
    
    <!-- Status Bar -->
    <color name="status_bar">#00695C</color>
</resources>
```

### strings.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- App Info -->
    <string name="app_name">Weigh to Go!</string>
    <string name="app_tagline">You\'ve got this—pound for pound.</string>
    
    <!-- Login Screen -->
    <string name="tab_sign_in">Sign In</string>
    <string name="tab_create_account">Create Account</string>
    <string name="hint_username">Username or Email</string>
    <string name="hint_password">Password</string>
    <string name="hint_confirm_password">Confirm Password</string>
    <string name="btn_sign_in">Sign In →</string>
    <string name="btn_create_account">Create Account</string>
    <string name="forgot_password">Forgot Password?</string>
    <string name="or_continue_with">or continue with</string>
    <string name="terms_text">By continuing, you agree to our Terms of Service and Privacy Policy</string>
    
    <!-- Dashboard Screen -->
    <string name="greeting_morning">Good morning,</string>
    <string name="greeting_afternoon">Good afternoon,</string>
    <string name="greeting_evening">Good evening,</string>
    <string name="weight_history">Weight History</string>
    <string name="recent_entries">Recent Entries</string>
    <string name="view_all">View All</string>
    <string name="add_weight">Add Weight</string>
    <string name="no_entries">No weight entries yet</string>
    <string name="start_tracking">Start tracking your progress!</string>
    
    <!-- Grid Headers -->
    <string name="header_date">DATE</string>
    <string name="header_weight">WEIGHT</string>
    <string name="header_trend">TREND</string>
    <string name="header_actions">ACTIONS</string>
    
    <!-- Actions -->
    <string name="edit_entry">Edit entry</string>
    <string name="delete_entry">Delete entry</string>
    <string name="trend_up">Weight increased</string>
    <string name="trend_down">Weight decreased</string>
    <string name="trend_same">Weight unchanged</string>
    
    <!-- Progress Card -->
    <string name="your_progress">Your Progress</string>
    <string name="current_weight">Current</string>
    <string name="start_weight">Start</string>
    <string name="goal_weight">Goal</string>
    <string name="lbs_unit">lbs</string>
    <string name="kg_unit">kg</string>
    <string name="progress_format">%d%% to goal</string>
    
    <!-- Weight Entry -->
    <string name="log_weight">Log Weight</string>
    <string name="select_date">Select Date</string>
    <string name="today">Today</string>
    <string name="save_entry">Save Entry</string>
    <string name="last_entry_format">Last entry: %.1f lbs on %s</string>
    
    <!-- SMS/Notifications Screen -->
    <string name="notification_settings">Notification Settings</string>
    <string name="sms_notifications_title">SMS Notifications</string>
    <string name="sms_notifications_description">Receive text message notifications when you reach your weight goals.</string>
    <string name="permission_granted">Permission granted - SMS notifications enabled</string>
    <string name="permission_denied">Permission denied - SMS notifications disabled</string>
    <string name="enable_sms_notifications">Enable SMS Notifications</string>
    <string name="notification_types">Notification Types</string>
    <string name="goal_achievement">Goal Achievement</string>
    <string name="goal_achievement_desc">Get notified when you reach your goal weight</string>
    <string name="daily_reminder">Daily Reminder</string>
    <string name="daily_reminder_desc">Reminder to log your weight each day</string>
    <string name="weekly_summary">Weekly Summary</string>
    <string name="weekly_summary_desc">Receive a weekly progress summary</string>
    <string name="permission_denied_title">SMS Permission Required</string>
    <string name="permission_denied_message">Without SMS permission, you won\'t receive text notifications for goal achievements. The app will continue to work, but notification features will be limited.</string>
    <string name="open_app_settings">Open App Settings</string>
    <string name="phone_number_label">Notification Phone Number</string>
    <string name="phone_number_hint">Enter phone number</string>
    <string name="verify_number">Verify Number</string>
    
    <!-- Dialogs -->
    <string name="confirm_delete_title">Delete Entry?</string>
    <string name="confirm_delete_message">Are you sure you want to delete this weight entry? This action cannot be undone.</string>
    <string name="cancel">Cancel</string>
    <string name="delete">Delete</string>
    <string name="save">Save</string>
    
    <!-- Error Messages -->
    <string name="error_empty_username">Please enter a username</string>
    <string name="error_empty_password">Please enter a password</string>
    <string name="error_password_mismatch">Passwords do not match</string>
    <string name="error_invalid_weight">Please enter a valid weight</string>
    <string name="error_login_failed">Invalid username or password</string>
    
    <!-- Success Messages -->
    <string name="success_account_created">Account created successfully!</string>
    <string name="success_weight_saved">Weight entry saved!</string>
    <string name="success_weight_deleted">Entry deleted</string>
    
    <!-- Accessibility -->
    <string name="accessibility_settings">Accessibility Settings</string>
</resources>
```

### dimens.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- Spacing (8dp grid) -->
    <dimen name="spacing_micro">4dp</dimen>
    <dimen name="spacing_small">8dp</dimen>
    <dimen name="spacing_medium">12dp</dimen>
    <dimen name="spacing_standard">16dp</dimen>
    <dimen name="spacing_large">20dp</dimen>
    <dimen name="spacing_xl">24dp</dimen>
    <dimen name="spacing_xxl">32dp</dimen>
    
    <!-- Corner Radius -->
    <dimen name="corner_radius_small">8dp</dimen>
    <dimen name="corner_radius_medium">12dp</dimen>
    <dimen name="corner_radius_large">16dp</dimen>
    <dimen name="corner_radius_xl">24dp</dimen>
    <dimen name="corner_radius_card">16dp</dimen>
    <dimen name="corner_radius_button">16dp</dimen>
    <dimen name="corner_radius_input">14dp</dimen>
    
    <!-- Button Heights -->
    <dimen name="button_height_standard">56dp</dimen>
    <dimen name="button_height_large">60dp</dimen>
    <dimen name="touch_target_min">48dp</dimen>
    
    <!-- Icon Sizes -->
    <dimen name="icon_size_small">18dp</dimen>
    <dimen name="icon_size_medium">24dp</dimen>
    <dimen name="icon_size_large">40dp</dimen>
    <dimen name="icon_size_xl">48dp</dimen>
    
    <!-- Text Sizes -->
    <dimen name="text_size_micro">10sp</dimen>
    <dimen name="text_size_small">12sp</dimen>
    <dimen name="text_size_body">14sp</dimen>
    <dimen name="text_size_input">16sp</dimen>
    <dimen name="text_size_title">18sp</dimen>
    <dimen name="text_size_headline">24sp</dimen>
    <dimen name="text_size_display">32sp</dimen>
    <dimen name="text_size_large_display">64sp</dimen>
    
    <!-- Card Elevation -->
    <dimen name="elevation_card">2dp</dimen>
    <dimen name="elevation_raised">4dp</dimen>
    <dimen name="elevation_fab">6dp</dimen>
    
    <!-- Input Field -->
    <dimen name="input_height">52dp</dimen>
</resources>
```

---

## 🧩 ACTIVITY JAVA FILES (Stub Code)

### LoginActivity.java
```java
package com.rickgoshen.weightogo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.rickgoshen.weightogo.R;

/**
 * Login/Registration Activity for Weigh to Go! app.
 * Handles user authentication and account creation.
 * 
 * @author Rick Goshen
 * @course CS 360 - Mobile Architecture & Programming
 */
public class LoginActivity extends AppCompatActivity {

    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private Button signInButton;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.signInButton);
        createAccountButton = findViewById(R.id.createAccountButton);
    }

    private void setupClickListeners() {
        signInButton.setOnClickListener(v -> attemptLogin());
        createAccountButton.setOnClickListener(v -> attemptCreateAccount());
    }

    private void attemptLogin() {
        // TODO: Implement login logic in Project Three
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        
        if (validateInput(username, password)) {
            // Placeholder - navigate to main activity
            navigateToMain();
        }
    }

    private void attemptCreateAccount() {
        // TODO: Implement account creation logic in Project Three
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        
        if (validateInput(username, password)) {
            Toast.makeText(this, R.string.success_account_created, Toast.LENGTH_SHORT).show();
            navigateToMain();
        }
    }

    private boolean validateInput(String username, String password) {
        if (username.isEmpty()) {
            usernameEditText.setError(getString(R.string.error_empty_username));
            return false;
        }
        if (password.isEmpty()) {
            passwordEditText.setError(getString(R.string.error_empty_password));
            return false;
        }
        return true;
    }

    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
```

### MainActivity.java
```java
package com.rickgoshen.weightogo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.rickgoshen.weightogo.R;
import com.rickgoshen.weightogo.adapters.WeightEntryAdapter;

/**
 * Main Dashboard Activity for Weigh to Go! app.
 * Displays weight history grid and progress summary.
 * 
 * @author Rick Goshen
 * @course CS 360 - Mobile Architecture & Programming
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView weightRecyclerView;
    private WeightEntryAdapter adapter;
    private ExtendedFloatingActionButton addEntryFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initializeViews();
        setupRecyclerView();
        setupClickListeners();
    }

    private void initializeViews() {
        weightRecyclerView = findViewById(R.id.weightRecyclerView);
        addEntryFab = findViewById(R.id.addEntryFab);
    }

    private void setupRecyclerView() {
        weightRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // TODO: Initialize adapter with data in Project Three
        // adapter = new WeightEntryAdapter(weightEntries);
        // weightRecyclerView.setAdapter(adapter);
    }

    private void setupClickListeners() {
        addEntryFab.setOnClickListener(v -> openWeightEntry());
    }

    private void openWeightEntry() {
        // TODO: Navigate to weight entry screen or show dialog
        // Intent intent = new Intent(this, WeightEntryActivity.class);
        // startActivity(intent);
    }

    private void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
```

### SettingsActivity.java
```java
package com.rickgoshen.weightogo.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.rickgoshen.weightogo.R;

/**
 * Settings Activity for Weigh to Go! app.
 * Handles SMS permission requests and notification settings.
 * 
 * @author Rick Goshen
 * @course CS 360 - Mobile Architecture & Programming
 */
public class SettingsActivity extends AppCompatActivity {

    private ImageView permissionStatusIcon;
    private TextView permissionStatusText;
    private MaterialButton requestPermissionButton;
    private MaterialCardView permissionDeniedCard;
    private MaterialButton openSettingsButton;
    private SwitchMaterial goalAchievementSwitch;
    private SwitchMaterial dailyReminderSwitch;

    // Permission request launcher
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                updatePermissionUI(isGranted);
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        initializeViews();
        setupClickListeners();
        checkPermissionStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Re-check permission when returning from settings
        checkPermissionStatus();
    }

    private void initializeViews() {
        permissionStatusIcon = findViewById(R.id.permissionStatusIcon);
        permissionStatusText = findViewById(R.id.permissionStatusText);
        requestPermissionButton = findViewById(R.id.requestPermissionButton);
        permissionDeniedCard = findViewById(R.id.permissionDeniedCard);
        openSettingsButton = findViewById(R.id.openSettingsButton);
        goalAchievementSwitch = findViewById(R.id.goalAchievementSwitch);
        dailyReminderSwitch = findViewById(R.id.dailyReminderSwitch);
    }

    private void setupClickListeners() {
        requestPermissionButton.setOnClickListener(v -> requestSmsPermission());
        openSettingsButton.setOnClickListener(v -> openAppSettings());
    }

    private void checkPermissionStatus() {
        boolean hasPermission = ContextCompat.checkSelfPermission(this, 
                Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
        updatePermissionUI(hasPermission);
    }

    private void updatePermissionUI(boolean hasPermission) {
        if (hasPermission) {
            // Permission granted state
            permissionStatusIcon.setImageResource(R.drawable.ic_check_circle);
            permissionStatusIcon.setColorFilter(ContextCompat.getColor(this, R.color.accent_green));
            permissionStatusText.setText(R.string.permission_granted);
            permissionStatusText.setTextColor(ContextCompat.getColor(this, R.color.accent_green));
            requestPermissionButton.setVisibility(View.GONE);
            permissionDeniedCard.setVisibility(View.GONE);
            
            // Enable notification toggles
            goalAchievementSwitch.setEnabled(true);
            dailyReminderSwitch.setEnabled(true);
        } else {
            // Permission denied state
            permissionStatusIcon.setImageResource(R.drawable.ic_warning);
            permissionStatusIcon.setColorFilter(ContextCompat.getColor(this, R.color.error_red));
            permissionStatusText.setText(R.string.permission_denied);
            permissionStatusText.setTextColor(ContextCompat.getColor(this, R.color.error_red));
            requestPermissionButton.setVisibility(View.VISIBLE);
            permissionDeniedCard.setVisibility(View.VISIBLE);
            
            // Disable notification toggles
            goalAchievementSwitch.setEnabled(false);
            goalAchievementSwitch.setChecked(false);
            dailyReminderSwitch.setEnabled(false);
            dailyReminderSwitch.setChecked(false);
        }
    }

    private void requestSmsPermission() {
        requestPermissionLauncher.launch(Manifest.permission.SEND_SMS);
    }

    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}
```

---

## ✅ IMPLEMENTATION CHECKLIST

### Phase 1: Project Setup
- [ ] Create new Android Studio project named "WeighToGo"
- [ ] Set package name: `com.rickgoshen.weightogo`
- [ ] Set minimum SDK: API 26 (Android 8.0)
- [ ] Set target SDK: API 34 (Android 14)
- [ ] Configure build.gradle with Material Design dependencies
- [ ] Set up proper package structure

### Phase 2: Resource Files
- [ ] Create `colors.xml` with complete color palette
- [ ] Create `strings.xml` with all string resources
- [ ] Create `dimens.xml` with spacing and sizing values
- [ ] Create `themes.xml` with app theme
- [ ] Add all required drawable icons (vector assets)
- [ ] Create gradient background drawable

### Phase 3: Login Screen (25%)
- [ ] Create `activity_login.xml` layout
- [ ] Add username EditText with proper inputType
- [ ] Add password EditText with `inputType="textPassword"` (CRITICAL)
- [ ] Add Sign In button
- [ ] Add Create Account button
- [ ] Apply gradient header background
- [ ] Add app branding (icon, name, tagline)
- [ ] Implement visual hierarchy and grouping
- [ ] Create `LoginActivity.java` with stub code

### Phase 4: Database Grid Screen (25%)
- [ ] Create `activity_main.xml` layout
- [ ] Add grid header row with column labels
- [ ] Create `item_weight_entry.xml` for RecyclerView items
- [ ] Add delete button on each row (REQUIRED)
- [ ] Add edit button on each row
- [ ] Add trend indicator
- [ ] Add FloatingActionButton for adding entries
- [ ] Add progress summary card
- [ ] Create `MainActivity.java` with stub code
- [ ] Create `WeightEntryAdapter.java` stub

### Phase 5: SMS Permissions Screen (30%)
- [ ] Update `AndroidManifest.xml` with SEND_SMS permission
- [ ] Add `<uses-feature>` for telephony
- [ ] Create `activity_settings.xml` layout
- [ ] Add SMS permission status card
- [ ] Add permission request button
- [ ] Add permission denied warning card
- [ ] Add "Open App Settings" button
- [ ] Add notification type toggles
- [ ] Create `SettingsActivity.java` with permission handling
- [ ] Test permission flow on device/emulator

### Phase 6: Visual Hierarchy (20%)
- [ ] Verify focus order matches task flow on all screens
- [ ] Confirm logical grouping of related elements
- [ ] Apply consistent theme (colors, typography, spacing)
- [ ] Ensure touch targets meet 48dp minimum
- [ ] Add appropriate elevation/shadows
- [ ] Verify transitions are consistent

### Phase 7: Final Testing
- [ ] Test all screens on emulator
- [ ] Verify password field shows dots (not text)
- [ ] Verify SMS permission request flow
- [ ] Check responsive layout on different screen sizes
- [ ] Validate all strings display correctly
- [ ] Confirm no hardcoded values in layouts

### Phase 8: Submission Preparation
- [ ] Clean project (Build > Clean Project)
- [ ] Remove unnecessary files
- [ ] Create ZIP file of entire project folder
- [ ] Name file: `Rick_Goshen_WeightTracker.zip`
- [ ] Verify ZIP contains all necessary files
- [ ] Test ZIP extraction and project opening

---

## 🎯 SUCCESS CRITERIA

### For "Exceeds Expectations" Rating:
1. **Exceptionally Clear**: All UI elements are intuitively labeled and positioned
2. **Insightful**: Design demonstrates understanding of user needs and Android patterns
3. **Sophisticated**: Professional polish with attention to detail (shadows, spacing, colors)
4. **Creative**: Unique visual identity while maintaining Material Design compliance

### Key Technical Validations:
- ✅ Password field uses `inputType="textPassword"` (shows dots)
- ✅ Delete button present on each grid row
- ✅ AndroidManifest includes `SEND_SMS` permission
- ✅ AndroidManifest includes `<uses-feature>` for telephony
- ✅ Permission check and request UI implemented
- ✅ App functions without SMS if permission denied
- ✅ Focus order matches user task flow
- ✅ Consistent theme across all screens

---

## 📚 REFERENCES

- **Material Design 3**: https://m3.material.io/
- **Android Developer Guide**: https://developer.android.com/guide
- **Android Design Guidelines**: https://developer.android.com/design
- **Accessibility Guidelines**: https://material.io/design/usability/accessibility.html
- **Course Materials**: CS 360 Chapters 2-4

---

**END OF PROJECT TWO REQUIREMENTS**

*Weigh to Go! — You've got this—pound for pound.* 🎉
