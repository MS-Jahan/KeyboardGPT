# Spec: Voice Custom Prompts

## MODIFIED Requirements

### Requirement: GEMINI-AUDIO-002 - Audio Transcription Request (MODIFIED)
GeminiClient MUST support custom transcription prompts.

#### Scenario: Submit audio with custom prompt
**GIVEN** audio file uploaded to Files API
**AND** custom prompt is configured
**WHEN** `submitAudioPrompt(File, String)` is called
**THEN** system MUST use provided prompt instead of default
**AND** prompt MUST be included in request
**AND** if prompt is null/empty, MUST use default prompt

---

## ADDED Requirements

### Requirement: VOICE-PROMPT-001 - Preset Prompts
The system MUST provide predefined prompt templates for common use cases.

#### Scenario: Available preset prompts
**GIVEN** VoicePromptPresets class
**WHEN** presets are enumerated
**THEN** following presets MUST be available:
- RAW: "Generate a transcript of the speech"
- CORRECTED: "Transcribe the speech and correct any grammar mistakes"
- PUNCTUATED: "Transcribe the speech with proper punctuation and capitalization"
- FORMAL: "Transcribe the speech in a formal, professional tone"
- SUMMARY: "Transcribe and provide a brief summary of the main points"
- CUSTOM: User-defined custom prompt

#### Scenario: Get preset prompt text
**GIVEN** preset type selected
**WHEN** prompt text is requested
**THEN** system MUST return corresponding prompt string
**AND** CUSTOM preset MUST return user's custom text

---

### Requirement: VOICE-PROMPT-002 - Custom Prompt Management
The system MUST allow users to define custom transcription prompts.

#### Scenario: Set custom prompt
**GIVEN** user creates custom prompt
**WHEN** custom text is saved
**THEN** prompt MUST be stored in SharedPreferences
**AND** prompt MUST be validated before saving
**AND** CUSTOM preset MUST be selected automatically

#### Scenario: Get current active prompt
**GIVEN** VoicePromptManager instance
**WHEN** `getCurrentPrompt()` is called
**THEN** system MUST return active prompt text
**AND** if CUSTOM selected, MUST return custom text
**AND** if preset selected, MUST return preset text
**AND** if nothing set, MUST return default (RAW)

---

### Requirement: VOICE-PROMPT-003 - Prompt Validation
The system MUST validate custom prompts before accepting them.

#### Scenario: Validate prompt length
**GIVEN** custom prompt is entered
**WHEN** validation is performed
**THEN** prompt length MUST be >= 5 characters
**AND** prompt length MUST be <= 500 characters
**AND** invalid length MUST show error message

#### Scenario: Validate prompt content
**GIVEN** custom prompt is entered
**WHEN** content is validated
**THEN** prompt MUST NOT be empty or whitespace only
**AND** prompt SHOULD contain transcription-related keywords
**AND** warning MAY be shown if prompt seems unrelated

#### Scenario: Reject invalid prompts
**GIVEN** invalid custom prompt
**WHEN** save is attempted
**THEN** system MUST prevent saving
**AND** MUST show specific error message
**AND** MUST keep previous valid prompt active

---

### Requirement: VOICE-PROMPT-004 - Settings UI
The system MUST provide intuitive UI for prompt configuration.

#### Scenario: Display prompt settings dialog
**GIVEN** user opens voice prompt settings
**WHEN** settings dialog appears
**THEN** dialog MUST show all preset options
**AND** dialog MUST show custom prompt option with EditText
**AND** dialog MUST show current active selection
**AND** dialog MUST show prompt preview
**AND** dialog MUST show Save and Cancel buttons

#### Scenario: Select preset prompt
**GIVEN** prompt settings dialog is open
**WHEN** user selects preset RadioButton
**THEN** selection MUST be highlighted
**AND** prompt preview MUST update immediately
**AND** custom EditText MUST be disabled/hidden

#### Scenario: Select custom prompt
**GIVEN** prompt settings dialog is open
**WHEN** user selects Custom option
**THEN** EditText MUST become enabled/visible
**AND** prompt preview MUST show EditText content
**AND** EditText MUST have focus

#### Scenario: Save prompt selection
**GIVEN** user has selected prompt
**WHEN** Save button is clicked
**THEN** selection MUST be validated
**AND** if valid, MUST save to SharedPreferences
**AND** if valid, dialog MUST dismiss
**AND** if invalid, MUST show error and keep dialog open

---

### Requirement: VOICE-PROMPT-005 - Prompt Preview
The system MUST show preview of active prompt.

#### Scenario: Update preview on selection change
**GIVEN** settings dialog is open
**WHEN** user changes selection
**THEN** preview MUST update immediately
**AND** preview MUST show full prompt text
**AND** preview MUST be read-only

#### Scenario: Preview custom prompt
**GIVEN** Custom option selected
**WHEN** user types in EditText
**THEN** preview MUST update in real-time
**AND** preview MUST reflect actual prompt that will be used

---

### Requirement: VOICE-PROMPT-006 - Prompt Persistence
The system MUST persist prompt preferences across sessions.

#### Scenario: Save prompt preference
**GIVEN** user saves prompt selection
**WHEN** app is closed and reopened
**THEN** same prompt MUST be active
**AND** if preset was selected, same preset MUST be active
**AND** if custom was selected, custom text MUST be preserved

#### Scenario: Handle missing saved prompt
**GIVEN** no prompt preference saved
**WHEN** prompt is requested
**THEN** system MUST use RAW preset as default
**AND** MUST NOT error or crash

---

### Requirement: VOICE-PROMPT-007 - Integration with Voice Command
Voice input MUST use configured prompt for transcription.

#### Scenario: Use current prompt for transcription
**GIVEN** voice recording completed
**WHEN** transcription is requested
**THEN** VoiceInputCommand MUST get current prompt from VoicePromptManager
**AND** MUST pass prompt to GeminiClient
**AND** Gemini API MUST receive configured prompt

#### Scenario: Handle prompt change during recording
**GIVEN** voice recording in progress
**WHEN** user attempts to change prompt
**THEN** system SHOULD prevent prompt change
**OR** system MUST NOT affect current recording
**AND** change SHOULD apply to next recording

---

### Requirement: VOICE-PROMPT-008 - Prompt Display in Voice Dialog
The system MAY display active prompt in voice input dialog.

#### Scenario: Show prompt in recording dialog (optional)
**GIVEN** voice recording dialog is visible
**WHEN** recording starts
**THEN** dialog MAY show current prompt text
**OR** dialog MAY show "Using: [preset name]"
**AND** display SHOULD be subtle (small text)
**AND** display MUST NOT obstruct main UI

---

### Requirement: VOICE-PROMPT-009 - Default Behavior
The system MUST work with sensible defaults if no prompt configured.

#### Scenario: First-time user
**GIVEN** user has never configured prompt
**WHEN** voice input is used first time
**THEN** RAW preset MUST be used
**AND** prompt MUST be "Generate a transcript of the speech"
**AND** system MUST NOT require prompt configuration

#### Scenario: Reset to default
**GIVEN** settings dialog is open
**WHEN** "Reset to default" is clicked
**THEN** RAW preset MUST be selected
**AND** custom prompt MUST be cleared
**AND** change MUST be saved immediately

---

### Requirement: VOICE-PROMPT-010 - Help and Guidance
The system MUST provide guidance for prompt creation.

#### Scenario: Show prompt examples
**GIVEN** custom prompt EditText is focused
**WHEN** help icon is tapped (if available)
**THEN** system SHOULD show examples of good prompts
**AND** examples SHOULD include:
- "Transcribe with punctuation"
- "Transcribe and format as bullet points"
- "Transcribe and fix grammar errors"

#### Scenario: Show prompt tips
**GIVEN** settings dialog displays help section
**WHEN** help is visible
**THEN** tips SHOULD mention:
- Keep prompts clear and specific
- Include "transcribe" keyword
- Mention desired output format
- Keep under 200 characters for best results
