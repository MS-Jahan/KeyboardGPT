# Tasks: Voice Custom Prompts

## Implementation Tasks

### 1. Create VoicePromptPresets Class
- [ ] Create `tn.amin.keyboard_gpt.voice.VoicePromptPresets.java`
- [ ] Define preset prompts enum
- [ ] Add preset descriptions
- [ ] Presets to include:
  - RAW: "Generate a transcript of the speech"
  - CORRECTED: "Transcribe the speech and correct any grammar mistakes"
  - PUNCTUATED: "Transcribe the speech with proper punctuation and capitalization"
  - FORMAL: "Transcribe the speech in a formal, professional tone"
  - SUMMARY: "Transcribe and provide a brief summary of the main points"
  - CUSTOM: User-defined prompt

### 2. Create VoicePromptManager Class
- [ ] Create `tn.amin.keyboard_gpt.voice.VoicePromptManager.java`
- [ ] Implement singleton pattern
- [ ] Load saved prompt from SharedPreferences
- [ ] Save prompt preference
- [ ] Get current active prompt
- [ ] Validate custom prompts (not empty, reasonable length)
- [ ] Provide default prompt if none set

### 3. Add Settings Storage
- [ ] Add to OtherSettingsType enum: VoicePromptPreset
- [ ] Add to OtherSettingsType enum: VoiceCustomPrompt
- [ ] Store selected preset (enum name)
- [ ] Store custom prompt text
- [ ] Add to SPManager if needed

### 4. Create Voice Prompt Settings Dialog
- [ ] Create `app/src/main/res/layout/dialog_voice_prompt_settings.xml`
- [ ] Add RadioGroup for preset selection
- [ ] Add RadioButton for each preset
- [ ] Add "Custom" option with EditText
- [ ] Add prompt preview TextView
- [ ] Add "Save" and "Cancel" buttons
- [ ] Add "Reset to default" button

### 5. Integrate Settings Dialog
- [ ] Add "Voice Prompt" option to settings menu
- [ ] Create VoicePromptSettingsDialog class
- [ ] Handle preset selection changes
- [ ] Handle custom prompt input
- [ ] Validate custom prompt before saving
- [ ] Show current active prompt
- [ ] Update preview when selection changes

### 6. Update GeminiClient Integration
- [ ] Modify `submitAudioPrompt(File, String)` to accept custom prompt
- [ ] Replace hardcoded "Generate a transcript" with parameter
- [ ] Ensure prompt is included in API request
- [ ] Handle empty/null prompt with fallback

### 7. Update VoiceInputCommand
- [ ] Get current prompt from VoicePromptManager
- [ ] Pass prompt to GeminiClient.submitAudioPrompt()
- [ ] Show current prompt in voice dialog (optional)
- [ ] Handle prompt changes during recording (prevent)

### 8. Add Quick Prompt Selection
- [ ] Add "Change prompt" button to voice recording dialog (optional)
- [ ] Show dropdown/menu with preset options
- [ ] Allow changing before starting recording
- [ ] Save selection for future use

### 9. Prompt Validation
- [ ] Validate prompt length (min 5, max 500 characters)
- [ ] Check prompt contains transcription-related keywords
- [ ] Warn if prompt seems unrelated to transcription
- [ ] Provide examples of good prompts

### 10. String Resources
- [ ] Add prompt preset names to strings.xml
- [ ] Add prompt preset descriptions
- [ ] Add validation error messages
- [ ] Add help text for custom prompts

### 11. Testing
- [ ] Test each preset prompt
- [ ] Test custom prompt input
- [ ] Test prompt validation
- [ ] Test settings persistence
- [ ] Verify API receives correct prompt
- [ ] Compare transcription quality across prompts
- [ ] Test very long custom prompts
- [ ] Test empty/invalid prompts

### 12. Documentation
- [ ] Add prompt examples to README
- [ ] Document best practices for custom prompts
- [ ] Add prompt engineering tips
- [ ] List preset descriptions

## Validation Criteria
- User can select from preset prompts
- User can create custom prompts
- Prompts persist across app restarts
- Invalid prompts are rejected with clear messages
- Transcription quality matches prompt intent
- Settings UI is intuitive and clear

## Estimated Effort
4-5 hours
