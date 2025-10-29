# Spec: Voice Button Hook

## ADDED Requirements

### Requirement: VOICE-HOOK-001 - Voice Button Detection
The system MUST detect when keyboard's voice input button is pressed.

#### Scenario: Detect voice Intent launch
**GIVEN** keyboard app attempts to start voice input
**WHEN** startActivity() is called with RecognizerIntent.ACTION_RECOGNIZE_SPEECH
**THEN** hook MUST intercept the Intent
**AND** MUST prevent Intent from proceeding to system
**AND** MUST trigger VoiceInputCommand instead

#### Scenario: Detect alternative voice Intents
**GIVEN** keyboard uses alternative voice Intent
**WHEN** Intent action is RecognizerIntent.ACTION_VOICE_SEARCH_HANDS_FREE
**OR** Intent action is RecognizerIntent.ACTION_WEB_SEARCH
**OR** Intent contains "voice" in action string
**THEN** hook MUST intercept
**AND** MUST trigger VoiceInputCommand

---

### Requirement: VOICE-HOOK-002 - Hook Registration
The system MUST register voice button hooks during keyboard initialization.

#### Scenario: Register Activity hook
**GIVEN** MainHook is initializing keyboard
**WHEN** hookKeyboard() is called
**THEN** VoiceButtonHook MUST be registered
**AND** Activity.startActivity() MUST be hooked
**AND** Context.startActivity() MUST be hooked
**AND** hook MUST apply to keyboard process only

#### Scenario: Handle hook registration failure
**GIVEN** hook registration is attempted
**WHEN** Xposed hook fails
**THEN** system MUST log error
**AND** MUST continue without voice button feature
**AND** MUST NOT crash keyboard app

---

### Requirement: VOICE-HOOK-003 - Intent Interception
The system MUST properly intercept and handle voice Intents.

#### Scenario: Intercept before system handling
**GIVEN** voice button hook is active
**WHEN** keyboard calls startActivity with voice Intent
**THEN** hook MUST execute before system
**AND** MUST prevent original Intent from continuing
**AND** MUST use XC_MethodHook.beforeHookedMethod()
**AND** MUST call setResult(null) to block Intent

#### Scenario: Extract Intent information
**GIVEN** voice Intent is intercepted
**WHEN** Intent is analyzed
**THEN** system MAY extract extras (language, prompts, etc.)
**AND** extras MAY be passed to VoiceInputCommand
**AND** default values MUST be used if extras missing

---

### Requirement: VOICE-HOOK-004 - Command Execution
The system MUST trigger VoiceInputCommand when voice button is pressed.

#### Scenario: Execute voice command on button press
**GIVEN** voice Intent intercepted successfully
**WHEN** hook processing continues
**THEN** VoiceInputCommand MUST be created
**AND** command MUST be executed asynchronously
**AND** execution MUST not block keyboard UI
**AND** any errors MUST be handled gracefully

#### Scenario: Prevent duplicate executions
**GIVEN** VoiceInputCommand is already running
**WHEN** voice button pressed again
**THEN** new command MUST NOT start
**AND** user MAY be notified command in progress
**AND** button press MUST be ignored

---

### Requirement: VOICE-HOOK-005 - Feature Toggle
The system MUST allow enabling/disabling voice button interception.

#### Scenario: Check feature enabled
**GIVEN** voice button pressed
**WHEN** hook is triggered
**THEN** system MUST check settings
**AND** if "Enable Voice Button Interception" is OFF
**THEN** Intent MUST proceed to system normally
**AND** VoiceInputCommand MUST NOT execute

#### Scenario: Settings preference storage
**GIVEN** settings dialog
**WHEN** "Enable Voice Button Interception" is changed
**THEN** preference MUST be saved to SharedPreferences
**AND** preference key MUST be "other_setting.EnableVoiceButtonInterception"
**AND** default value MUST be true

---

### Requirement: VOICE-HOOK-006 - Keyboard Compatibility
The system MUST support voice button interception across multiple keyboard apps.

#### Scenario: Gboard compatibility
**GIVEN** keyboard app is Google Gboard
**WHEN** voice button is pressed
**THEN** hook MUST intercept successfully
**AND** MUST trigger VoiceInputCommand

#### Scenario: SwiftKey compatibility
**GIVEN** keyboard app is Microsoft SwiftKey
**WHEN** voice button is pressed
**THEN** hook MUST intercept successfully
**AND** MUST trigger VoiceInputCommand

#### Scenario: Unknown keyboard fallback
**GIVEN** keyboard app is not explicitly supported
**WHEN** voice button is pressed
**THEN** hook SHOULD attempt Intent interception
**AND** if unsuccessful, MUST allow system voice input
**AND** MUST log keyboard app name for future support

---

### Requirement: VOICE-HOOK-007 - Error Handling
The system MUST handle all hook-related errors gracefully.

#### Scenario: Handle permission denied
**GIVEN** voice button pressed
**AND** RECORD_AUDIO permission not granted
**WHEN** VoiceInputCommand would execute
**THEN** system MUST not intercept Intent
**AND** MUST allow system voice input to proceed
**OR** MUST show permission request first

#### Scenario: Handle command execution failure
**GIVEN** voice button pressed
**AND** Intent intercepted
**WHEN** VoiceInputCommand fails to execute
**THEN** system MUST log error
**AND** MUST show error toast to user
**AND** MUST NOT leave keyboard in broken state

#### Scenario: Handle hook failure
**GIVEN** VoiceButtonHook registered
**WHEN** hook throws exception
**THEN** exception MUST be caught
**AND** MUST log to Xposed logs
**AND** system voice input SHOULD proceed
**AND** keyboard MUST remain functional

---

### Requirement: VOICE-HOOK-008 - User Feedback
The system MUST provide feedback when voice button is intercepted.

#### Scenario: Visual feedback on interception
**GIVEN** voice button pressed
**WHEN** Intent intercepted successfully
**THEN** system MAY show brief toast "Voice input starting..."
**OR** system MAY trigger vibration feedback
**AND** feedback MUST be non-blocking

#### Scenario: Indicate when feature disabled
**GIVEN** voice button interception is disabled
**WHEN** voice button is pressed
**THEN** system MUST allow normal system voice input
**AND** MUST NOT show any KeyboardGPT feedback

---

### Requirement: VOICE-HOOK-009 - Hook Lifecycle
The system MUST properly manage hook lifecycle.

#### Scenario: Initialize hooks on keyboard start
**GIVEN** keyboard app is starting
**WHEN** InputMethodService onCreate() is called
**THEN** VoiceButtonHook MUST be initialized
**AND** hooks MUST be registered
**AND** hook state MUST be ready

#### Scenario: Clean up hooks on keyboard destroy
**GIVEN** keyboard app is destroying
**WHEN** InputMethodService onDestroy() is called
**THEN** VoiceButtonHook SHOULD be cleaned up
**AND** pending commands SHOULD be cancelled
**AND** resources MUST be released

---

### Requirement: VOICE-HOOK-010 - Debugging Support
The system MUST provide debugging capabilities for hook development.

#### Scenario: Log voice Intent details
**GIVEN** voice Intent intercepted
**WHEN** logging is enabled
**THEN** system MUST log Intent action
**AND** MUST log Intent package
**AND** MUST log Intent extras
**AND** logs MUST appear in Xposed/LSPosed logs

#### Scenario: Log hook success/failure
**GIVEN** hook is triggered
**WHEN** interception occurs
**THEN** system MUST log whether hook succeeded
**AND** if failed, MUST log reason
**AND** MUST log keyboard package name
**AND** MUST log Android version
