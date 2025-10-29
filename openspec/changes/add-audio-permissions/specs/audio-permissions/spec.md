# Spec: Audio Recording Permissions

## ADDED Requirements

### Requirement: APP-PERM-001 - Manifest Permission Declaration
The application MUST declare RECORD_AUDIO permission in AndroidManifest.xml.

#### Scenario: Permission declared in manifest
**GIVEN** the AndroidManifest.xml file
**WHEN** the file is parsed
**THEN** it MUST contain `<uses-permission android:name="android.permission.RECORD_AUDIO" />`

---

### Requirement: APP-PERM-002 - Runtime Permission Check
The application MUST check for RECORD_AUDIO permission at runtime before attempting audio recording.

#### Scenario: Check permission before recording
**GIVEN** user initiates voice input
**WHEN** the voice recording is about to start
**THEN** the system MUST check if RECORD_AUDIO permission is granted
**AND** if not granted, MUST request permission before proceeding

#### Scenario: Handle missing permission gracefully
**GIVEN** RECORD_AUDIO permission is not granted
**WHEN** user attempts voice input
**THEN** the system MUST display permission request dialog
**AND** MUST NOT attempt to record audio
**AND** MUST NOT crash or error

---

### Requirement: APP-PERM-003 - Permission Request Flow
The application MUST implement a user-friendly permission request flow with clear explanation.

#### Scenario: Display permission rationale
**GIVEN** user has not granted RECORD_AUDIO permission
**WHEN** permission request is triggered
**THEN** system MUST show explanation dialog stating why permission is needed
**AND** dialog MUST mention "voice-to-text transcription via Gemini AI"

#### Scenario: Handle permission granted
**GIVEN** permission request dialog is shown
**WHEN** user grants RECORD_AUDIO permission
**THEN** system MUST proceed with voice recording
**AND** MUST save permission state for future checks

#### Scenario: Handle permission denied
**GIVEN** permission request dialog is shown
**WHEN** user denies RECORD_AUDIO permission
**THEN** system MUST display informative message
**AND** MUST NOT proceed with voice recording
**AND** MUST fall back to text input only

---

### Requirement: APP-PERM-004 - Permanent Denial Handling
The application MUST handle the "Don't ask again" scenario appropriately.

#### Scenario: Detect permanent denial
**GIVEN** user has selected "Don't ask again" and denied permission
**WHEN** permission check is performed
**THEN** system MUST detect permanent denial state
**AND** MUST NOT show permission request dialog again

#### Scenario: Redirect to settings
**GIVEN** permission is permanently denied
**WHEN** user attempts voice input
**THEN** system MUST show dialog with "Open Settings" button
**AND** button MUST redirect to app's permission settings
**AND** dialog MUST explain manual permission grant process

---

### Requirement: APP-PERM-005 - Permission Manager API
The application MUST provide a centralized PermissionManager for all permission operations.

#### Scenario: Check permission status
**GIVEN** PermissionManager instance
**WHEN** `checkRecordAudioPermission(Context)` is called
**THEN** it MUST return boolean indicating permission status
**AND** MUST use Android's checkSelfPermission API

#### Scenario: Request permission
**GIVEN** PermissionManager instance
**WHEN** `requestRecordAudioPermission(Activity)` is called
**THEN** it MUST trigger Android's permission request dialog
**AND** MUST handle callback via listener interface

#### Scenario: Check rationale display requirement
**GIVEN** PermissionManager instance
**WHEN** `shouldShowPermissionRationale(Activity)` is called
**THEN** it MUST return whether rationale should be shown
**AND** MUST use Android's shouldShowRequestPermissionRationale API
