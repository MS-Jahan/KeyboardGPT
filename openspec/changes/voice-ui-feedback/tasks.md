# Tasks: Voice UI Feedback

## Implementation Tasks

### 1. Design Voice Recording View Layout
- [ ] Create `app/src/main/res/layout/dialog_voice_recording.xml`
- [ ] Add microphone icon (animated pulse)
- [ ] Add "Listening..." text
- [ ] Add audio level visualization (simple bar or circle)
- [ ] Add "Tap to stop" hint text
- [ ] Add cancel button
- [ ] Design for both light and dark themes

### 2. Design Voice Processing View Layout
- [ ] Create `app/src/main/res/layout/dialog_voice_processing.xml`
- [ ] Add loading spinner
- [ ] Add "Processing..." text
- [ ] Add "Transcribing your speech" subtitle
- [ ] Add cancel button
- [ ] Keep layout simple and minimal

### 3. Design Voice Result View Layout
- [ ] Create `app/src/main/res/layout/dialog_voice_result.xml`
- [ ] Add success checkmark icon
- [ ] Add transcribed text preview
- [ ] Add "Inserted" message
- [ ] Auto-dismiss after 2 seconds

### 4. Design Voice Error View Layout
- [ ] Create `app/src/main/res/layout/dialog_voice_error.xml`
- [ ] Add error icon
- [ ] Add error message text (dynamic)
- [ ] Add "Retry" button
- [ ] Add "Cancel" button

### 5. Create VoiceInputDialog Class
- [ ] Create `tn.amin.keyboard_gpt.external.dialog.VoiceInputDialog.java`
- [ ] Implement dialog management
- [ ] Handle state transitions (recording → processing → result/error)
- [ ] Implement view switching logic
- [ ] Handle dialog lifecycle

### 6. Implement Audio Level Visualization
- [ ] Create custom View for audio levels
- [ ] Update visual based on `onAudioLevelChanged()` callback
- [ ] Animate smoothly (avoid jank)
- [ ] Use simple bar or circular progress
- [ ] Update at 10 FPS (smooth enough, efficient)

### 7. Implement State Transitions
- [ ] Show recording view when recording starts
- [ ] Update audio levels during recording
- [ ] Transition to processing view when recording stops
- [ ] Show result view on success
- [ ] Show error view on failure
- [ ] Handle user cancellation

### 8. Integrate with VoiceInputCommand
- [ ] Implement VoiceInputListener in VoiceInputDialog
- [ ] Subscribe to command callbacks
- [ ] Update UI on state changes
- [ ] Handle all callback methods
- [ ] Unsubscribe on dialog dismiss

### 9. Implement User Interactions
- [ ] Handle tap to stop recording
- [ ] Handle cancel button (stop command)
- [ ] Handle retry button (restart command)
- [ ] Handle back button press
- [ ] Dismiss dialog on completion

### 10. Haptic Feedback
- [ ] Vibrate on recording start (short pulse)
- [ ] Vibrate on recording stop (short pulse)
- [ ] Vibrate on error (pattern: short-short)
- [ ] Vibrate on success (pattern: short)
- [ ] Use HapticFeedbackConstants for standard patterns

### 11. Dialog Positioning
- [ ] Position dialog above keyboard
- [ ] Calculate keyboard height dynamically
- [ ] Handle screen rotation
- [ ] Handle different screen sizes
- [ ] Ensure dialog doesn't overlap keyboard

### 12. Accessibility
- [ ] Add content descriptions for icons
- [ ] Announce state changes via TalkBack
- [ ] Ensure sufficient text contrast
- [ ] Support large text sizes
- [ ] Make buttons touch-target sized (48dp min)

### 13. Testing
- [ ] Test all state transitions
- [ ] Test on different screen sizes
- [ ] Test in landscape and portrait
- [ ] Test with dark mode
- [ ] Test with accessibility features enabled
- [ ] Test rapid state changes
- [ ] Test cancellation scenarios
- [ ] Test error displays

### 14. Resources
- [ ] Add string resources for all text
- [ ] Add icons (mic, spinner, checkmark, error)
- [ ] Add colors for themes
- [ ] Add dimensions for consistent sizing
- [ ] Add animations (pulse, fade)

## Validation Criteria
- Clear visual feedback at all stages
- Audio level visualization works smoothly
- State transitions are instant and clear
- Error messages are helpful
- Dialog doesn't interfere with keyboard use
- Haptic feedback is appropriate
- Accessible to screen reader users

## Estimated Effort
8-10 hours
