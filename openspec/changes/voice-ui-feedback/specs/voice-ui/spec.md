# Spec: Voice Input UI Feedback

## ADDED Requirements

### Requirement: VOICE-UI-001 - Recording State Display
The system MUST display clear visual feedback during audio recording.

#### Scenario: Show recording dialog
**GIVEN** VoiceInputCommand starts recording
**WHEN** `onRecordingStarted()` callback fires
**THEN** VoiceInputDialog MUST display
**AND** dialog MUST show microphone icon
**AND** dialog MUST show "Listening..." text
**AND** dialog MUST show "Tap to stop" hint
**AND** dialog MUST show cancel button

#### Scenario: Display audio level visualization
**GIVEN** recording is active
**WHEN** `onAudioLevelChanged(int level)` callback fires
**THEN** audio level indicator MUST update
**AND** indicator MUST reflect level value (0-100)
**AND** animation MUST be smooth (no jitter)
**AND** update rate MUST be ~10 FPS

#### Scenario: Animate microphone icon
**GIVEN** recording dialog is visible
**WHEN** recording is active
**THEN** microphone icon SHOULD pulse
**AND** pulse rate SHOULD be ~1 Hz
**AND** animation MUST not affect performance

---

### Requirement: VOICE-UI-002 - Processing State Display
The system MUST display clear feedback during transcription processing.

#### Scenario: Transition to processing view
**GIVEN** recording has stopped
**WHEN** `onProcessingStarted()` callback fires
**THEN** dialog MUST transition to processing view
**AND** view MUST show loading spinner
**AND** view MUST show "Processing..." text
**AND** view MUST show "Transcribing your speech" subtitle
**AND** cancel button MUST remain available

#### Scenario: Smooth state transition
**GIVEN** transitioning from recording to processing
**WHEN** view changes
**THEN** transition SHOULD be animated
**AND** animation duration SHOULD be 200-300ms
**AND** transition MUST not flicker

---

### Requirement: VOICE-UI-003 - Success State Display
The system MUST display success feedback when transcription completes.

#### Scenario: Show success result
**GIVEN** transcription completed successfully
**WHEN** `onTranscriptionReceived(String)` callback fires
**THEN** dialog MUST show success view
**AND** view MUST show checkmark icon
**AND** view MUST show transcribed text preview (first 50 chars)
**AND** view MUST show "Inserted" message

#### Scenario: Auto-dismiss success dialog
**GIVEN** success view is displayed
**WHEN** 2 seconds have elapsed
**THEN** dialog MUST dismiss automatically
**AND** keyboard MUST return to normal state

---

### Requirement: VOICE-UI-004 - Error State Display
The system MUST display clear error feedback when failures occur.

#### Scenario: Show error view
**GIVEN** voice input command fails
**WHEN** `onError(String)` callback fires
**THEN** dialog MUST show error view
**AND** view MUST show error icon
**AND** view MUST show error message text
**AND** view MUST show "Retry" button
**AND** view MUST show "Cancel" button

#### Scenario: Display specific error messages
**GIVEN** different error types occur
**WHEN** error view is shown
**THEN** message MUST be specific:
- Permission denied → "Microphone permission required"
- Network error → "Network error. Please try again"
- Empty transcription → "No speech detected"
- API quota → "API quota exceeded. Check API key"
- Unknown error → "An error occurred. Please try again"

---

### Requirement: VOICE-UI-005 - User Interactions
The system MUST support user interactions with voice feedback UI.

#### Scenario: Tap to stop recording
**GIVEN** recording dialog is visible
**WHEN** user taps anywhere on dialog (except cancel)
**THEN** recording MUST stop
**AND** processing MUST begin

#### Scenario: Cancel recording
**GIVEN** recording dialog is visible
**WHEN** user taps "Cancel" button
**THEN** recording MUST stop
**AND** command MUST be cancelled
**AND** dialog MUST dismiss
**AND** no text MUST be inserted

#### Scenario: Cancel during processing
**GIVEN** processing dialog is visible
**WHEN** user taps "Cancel" button
**THEN** transcription request MUST be cancelled
**AND** dialog MUST dismiss
**AND** no text MUST be inserted

#### Scenario: Retry after error
**GIVEN** error dialog is visible
**WHEN** user taps "Retry" button
**THEN** dialog MUST dismiss
**AND** new VoiceInputCommand MUST start
**AND** recording dialog MUST appear

#### Scenario: Handle back button
**GIVEN** any voice dialog is visible
**WHEN** user presses back button
**THEN** dialog MUST dismiss
**AND** command MUST be cancelled if in progress

---

### Requirement: VOICE-UI-006 - Haptic Feedback
The system MUST provide haptic feedback for voice input events.

#### Scenario: Haptic on recording start
**GIVEN** voice recording begins
**WHEN** recording dialog appears
**THEN** device SHOULD vibrate briefly (50ms)
**AND** vibration pattern SHOULD be single pulse

#### Scenario: Haptic on recording stop
**GIVEN** recording stops
**WHEN** transitioning to processing
**THEN** device SHOULD vibrate briefly (50ms)
**AND** vibration pattern SHOULD be single pulse

#### Scenario: Haptic on success
**GIVEN** transcription succeeds
**WHEN** success view appears
**THEN** device SHOULD vibrate briefly (30ms)
**AND** vibration pattern SHOULD be single short pulse

#### Scenario: Haptic on error
**GIVEN** error occurs
**WHEN** error view appears
**THEN** device SHOULD vibrate with pattern (100ms, 50ms pause, 100ms)
**AND** pattern SHOULD indicate error

---

### Requirement: VOICE-UI-007 - Dialog Positioning
The system MUST position voice feedback dialogs appropriately.

#### Scenario: Position above keyboard
**GIVEN** keyboard is visible
**WHEN** voice dialog is displayed
**THEN** dialog MUST appear above keyboard
**AND** dialog MUST not overlap keyboard
**AND** dialog MUST be centered horizontally

#### Scenario: Calculate keyboard height
**GIVEN** dialog positioning is needed
**WHEN** keyboard height is determined
**THEN** system MUST get keyboard height from InputMethodService
**OR** system SHOULD use default height if unavailable
**AND** positioning MUST account for screen insets

#### Scenario: Handle screen rotation
**GIVEN** voice dialog is visible
**WHEN** screen orientation changes
**THEN** dialog MUST reposition correctly
**AND** dialog MUST not overlap keyboard
**AND** dialog state MUST be preserved

---

### Requirement: VOICE-UI-008 - Accessibility Support
The system MUST support accessibility features for voice feedback UI.

#### Scenario: Content descriptions
**GIVEN** voice dialog contains icons
**WHEN** screen reader is active
**THEN** all icons MUST have content descriptions
**AND** descriptions MUST be meaningful:
- Microphone icon → "Recording voice input"
- Checkmark → "Transcription successful"
- Error icon → "Transcription failed"

#### Scenario: TalkBack announcements
**GIVEN** screen reader is active
**WHEN** dialog state changes
**THEN** state change MUST be announced
**AND** announcements MUST be clear:
- Recording starts → "Recording voice input. Tap to stop"
- Processing starts → "Processing voice input"
- Success → "Transcription inserted: [preview]"
- Error → "[Error message]"

#### Scenario: Touch target sizes
**GIVEN** dialog contains buttons
**WHEN** dialog is displayed
**THEN** all buttons MUST be minimum 48dp touch targets
**AND** buttons MUST have sufficient spacing

#### Scenario: Text contrast
**GIVEN** dialog displays text
**WHEN** evaluated for accessibility
**THEN** text contrast MUST meet WCAG AA standards (4.5:1)
**AND** contrast MUST be sufficient in both light and dark modes

---

### Requirement: VOICE-UI-009 - Theme Support
The system MUST support light and dark themes for voice feedback UI.

#### Scenario: Dark theme support
**GIVEN** device is in dark mode
**WHEN** voice dialog is displayed
**THEN** dialog background MUST be dark
**AND** text MUST be light colored
**AND** icons MUST use light color
**AND** contrast MUST be sufficient

#### Scenario: Light theme support
**GIVEN** device is in light mode
**WHEN** voice dialog is displayed
**THEN** dialog background MUST be light
**AND** text MUST be dark colored
**AND** icons MUST use dark color
**AND** contrast MUST be sufficient

---

### Requirement: VOICE-UI-010 - Dialog Lifecycle
The system MUST properly manage voice dialog lifecycle.

#### Scenario: Show dialog on command start
**GIVEN** VoiceInputCommand starts
**WHEN** recording begins
**THEN** dialog MUST be created if not exists
**AND** dialog MUST be shown
**AND** dialog MUST be focused

#### Scenario: Dismiss dialog on completion
**GIVEN** voice input completes successfully
**WHEN** auto-dismiss timeout expires
**THEN** dialog MUST dismiss
**AND** dialog resources MUST be released

#### Scenario: Dismiss dialog on cancellation
**GIVEN** command is cancelled
**WHEN** cancellation completes
**THEN** dialog MUST dismiss immediately
**AND** dialog resources MUST be released

#### Scenario: Handle activity lifecycle
**GIVEN** voice dialog is visible
**WHEN** app goes to background
**THEN** dialog SHOULD dismiss
**AND** command SHOULD be cancelled
**AND** resources MUST be released
