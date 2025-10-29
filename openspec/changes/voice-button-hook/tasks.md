# Tasks: Voice Button Hook

## Implementation Tasks

### 1. Research Keyboard Voice Button Implementation
- [ ] Analyze Gboard voice button implementation
- [ ] Analyze SwiftKey voice button implementation
- [ ] Identify common patterns for voice button hooks
- [ ] Document Intent actions used (ACTION_RECOGNIZE_SPEECH, etc.)
- [ ] Test on multiple keyboard versions

### 2. Create KeyboardVoiceDetector Utility
- [ ] Create `tn.amin.keyboard_gpt.hook.KeyboardVoiceDetector.java`
- [ ] Implement detection for common voice button patterns
- [ ] Add keyboard-specific detection logic
- [ ] Support Intent-based detection
- [ ] Support View click detection (if applicable)

### 3. Create VoiceButtonHook Class
- [ ] Create `tn.amin.keyboard_gpt.hook.VoiceButtonHook.java`
- [ ] Implement hook registration method
- [ ] Hook startActivity for voice Intents
- [ ] Hook onClick for voice button views (if applicable)
- [ ] Prevent default system voice input
- [ ] Trigger VoiceInputCommand instead

### 4. Integrate with MainHook
- [ ] Add VoiceButtonHook initialization in MainHook
- [ ] Hook appropriate methods during keyboard load
- [ ] Handle hook setup for different keyboard apps
- [ ] Add fallback detection logic

### 5. Intent Interception
- [ ] Hook Activity.startActivity() method
- [ ] Detect RecognizerIntent.ACTION_RECOGNIZE_SPEECH
- [ ] Detect RecognizerIntent.ACTION_VOICE_SEARCH_HANDS_FREE
- [ ] Prevent Intent from proceeding
- [ ] Trigger VoiceInputCommand instead
- [ ] Handle Intent extras if needed

### 6. Settings Integration
- [ ] Add "Enable Voice Button Interception" setting
- [ ] Store preference in SharedPreferences
- [ ] Allow disabling feature (fallback to system)
- [ ] Add to OtherSettingsType enum
- [ ] Update settings dialog

### 7. Error Handling
- [ ] Handle hook failure gracefully
- [ ] Fall back to system voice if hook fails
- [ ] Log hook failures for debugging
- [ ] Handle keyboard app updates breaking hooks
- [ ] Provide user notification on hook failure

### 8. Keyboard Compatibility
- [ ] Test on Google Gboard
- [ ] Test on Microsoft SwiftKey
- [ ] Test on Samsung Keyboard
- [ ] Test on Simple Keyboard
- [ ] Test on Futo Keyboard
- [ ] Document compatibility status
- [ ] Add keyboard detection logic

### 9. Testing
- [ ] Test voice button press triggers command
- [ ] Test system voice input is prevented
- [ ] Test fallback when feature disabled
- [ ] Test on multiple keyboard apps
- [ ] Test on different Android versions
- [ ] Test with and without permission
- [ ] Verify no interference with other keyboard functions

### 10. Documentation
- [ ] Document supported keyboards
- [ ] Document how hook works
- [ ] Add troubleshooting guide
- [ ] Update README with voice button feature

## Validation Criteria
- Voice button successfully triggers VoiceInputCommand
- System voice input is prevented when feature enabled
- Works on at least 3 major keyboard apps
- Settings toggle works correctly
- Fallback to system voice works when disabled
- No crashes or keyboard malfunctions

## Estimated Effort
10-12 hours (due to keyboard compatibility research)
