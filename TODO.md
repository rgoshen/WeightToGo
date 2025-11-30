# TODO.md - Weigh to Go! Project Two

## [2025-11-29] Feature: UI Design Implementation

**Objective:**
Build complete UI design in Android Studio (XML layouts only) for CS 360 Project Two submission.

**Approach:**
Create all required XML layouts and resources following the Figma Design Specifications and Project Two Requirements. No functional Java code - UI only.

**Tests:**
- Project builds successfully
- Password field shows dots (inputType="textPassword")
- Delete button present on each grid row
- AndroidManifest includes SEND_SMS permission and telephony feature

**Risks & Tradeoffs:**
- Using PNG icons instead of vector drawables (acceptable for this project)
- Layouts designed for standard phone size; tablet optimization deferred

---

## Current Tasks

### In Progress
- [ ] None

### Phase 1: Resource Files (Completed)
- [x] Create colors.xml palette (2025-11-29)
- [x] Add drawable icons across all densities (2025-11-29)
- [x] Create strings.xml with all UI strings (2025-11-29)
- [x] Create dimens.xml with spacing and sizing values (2025-11-29)
- [x] Update themes.xml for Material Design 3 (2025-11-29)

### Phase 2: Login Screen (25%) - Completed
- [x] Create activity_login.xml layout (2025-11-29)
  - [x] Used existing ic_profile.png for username icon
  - [x] Used existing ic_lock.png for password icon
  - [x] Used existing bg_gradient_primary.xml for header
  - [x] Build header section with app icon, name, and tagline
  - [x] Build auth card with tab toggle (Sign In / Create Account)
  - [x] Build username/password fields with icons
  - [x] Add Sign In button (primary, filled)
  - [x] Add Create Account button (secondary, outlined)
  - [x] Add Forgot Password link
  - [x] Add "or continue with" divider
  - [x] Add terms/privacy footer
  - [x] Verify password field uses inputType="textPassword"
  - [x] Verify focus order matches user flow
  - [x] Validate accessibility (content descriptions, 48dp touch targets)

### Phase 3: Database Grid Screen (25%)
- [ ] Create activity_main.xml layout with grid
- [ ] Create item_weight_entry.xml with delete button

### Phase 4: SMS Notifications Screen (30%)
- [ ] Update AndroidManifest.xml with SMS permissions and telephony feature
- [ ] Create activity_settings.xml for SMS permissions UI

### Phase 5: Validation
- [ ] Build and validate project

### Completed
- [x] Create feature branch (2025-11-29)

---

## Blockers
None