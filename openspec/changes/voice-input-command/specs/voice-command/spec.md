# Spec: Voice Input Command

## ADDED Requirements

### Requirement: VOICE-CMD-001 - Command Lifecycle
VoiceInputCommand MUST manage the complete voice-to-text workflow.

#### Scenario: Execute voice input command
**GIVEN** VoiceInputCommand instance
**AND** RECORD_AUDIO permission is granted
**WHEN** `execute()` is called
**THEN** state MUST change to RECORDING
**AND** AudioRecorder MUST start recording
**AND** `onRecordingStarted()` callback MUST fire
**AND** command MUST wait for recording completion

#### Scenario: Complete voice input workflow
**GIVEN** voice input command is executing
**WHEN** user completes speaking
**THEN** recording MUST stop
**AND** state MUST change to PROCESSING
**AND** audio file MUST be uploaded to Gemini
**AND** transcription MUST be requested
**AND** transcription text MUST be inserted into keyboard
**AND** state MUST change to COMPLETED
**AND** `onTranscriptionReceived()` callback MUST fire

#### Scenario: Handle command execution failure
**GIVEN** voice input command is executing
**WHEN** any step fails
**THEN** state MUST change to ERROR
**AND** `onError()` callback MUST fire with error message
**AND** resources MUST be cleaned up
**AND** partial audio file MUST be deleted

---

### Requirement: VOICE-CMD-002 - State Management
The command MUST maintain accurate state throughout execution.

#### Scenario: Valid state transitions
**GIVEN** VoiceInputCommand exists
**WHEN** state changes occur
**THEN** valid transitions are: IDLE→RECORDING→PROCESSING→COMPLETED
**OR** any state→ERROR
**OR** any state→CANCELLED
**AND** invalid transitions MUST be prevented

#### Scenario: Prevent concurrent execution
**GIVEN** VoiceInputCommand is in RECORDING or PROCESSING state
**WHEN** `execute()` is called again
**THEN** system MUST reject execution
**AND** MUST throw IllegalStateException
**AND** error message MUST indicate command in progress

#### Scenario: State change notifications
**GIVEN** VoiceInputCommand has listeners
**WHEN** state changes
**THEN** all registered listeners MUST be notified
**AND** notification MUST include old state and new state
**AND** notification MUST be synchronous

---

### Requirement: VOICE-CMD-003 - Recording Integration
The command MUST properly integrate with AudioRecorder.

#### Scenario: Start recording with configuration
**GIVEN** voice input command executes
**WHEN** recording starts
**THEN** AudioRecorder MUST be initialized with speech config
**AND** config MUST use 16kHz sample rate
**AND** config MUST use AAC encoding
**AND** config MUST use MONO channel

#### Scenario: Handle recording completion
**GIVEN** recording is in progress
**WHEN** user stops speaking or timeout occurs
**THEN** `onRecordingStopped()` callback MUST be received
**AND** audio File MUST be extracted
**AND** file MUST be validated (exists, size > 0)
**AND** processing phase MUST begin

#### Scenario: Handle recording error
**GIVEN** recording is in progress
**WHEN** recording error occurs
**THEN** command state MUST change to ERROR
**AND** error message MUST be propagated to listeners
**AND** command MUST terminate

---

### Requirement: VOICE-CMD-004 - Transcription Integration
The command MUST properly integrate with Gemini audio API.

#### Scenario: Request transcription
**GIVEN** audio recording completed successfully
**WHEN** processing phase begins
**THEN** audio file MUST be passed to GeminiClient
**AND** `submitAudioPrompt()` MUST be called
**AND** prompt SHOULD be empty or "Transcribe"
**AND** API call MUST be asynchronous

#### Scenario: Receive transcription result
**GIVEN** transcription request sent
**WHEN** Gemini API responds with transcription
**THEN** transcription text MUST be extracted
**AND** text MUST be trimmed of whitespace
**AND** empty results MUST be handled as error
**AND** `onTranscriptionReceived()` callback MUST fire

#### Scenario: Handle transcription error
**GIVEN** transcription request sent
**WHEN** API returns error
**THEN** command state MUST change to ERROR
**AND** error message MUST be user-friendly
**AND** API error details MUST be logged

---

### Requirement: VOICE-CMD-005 - Text Insertion
The command MUST insert transcribed text into keyboard input.

#### Scenario: Insert transcription text
**GIVEN** transcription received successfully
**WHEN** text insertion begins
**THEN** IMSController.commit() MUST be called
**AND** full transcription text MUST be passed
**AND** cursor position MUST be respected
**AND** text MUST appear in input field

#### Scenario: Handle insertion at cursor position
**GIVEN** input field has existing text with cursor position
**WHEN** transcription is inserted
**THEN** text MUST be inserted at current cursor position
**AND** cursor MUST move to end of inserted text
**AND** existing text MUST be preserved

---

### Requirement: VOICE-CMD-006 - Cancellation Support
The command MUST support cancellation at any stage.

#### Scenario: Cancel during recording
**GIVEN** voice input command is RECORDING
**WHEN** `cancel()` is called
**THEN** AudioRecorder MUST stop immediately
**AND** state MUST change to CANCELLED
**AND** audio file MUST be deleted
**AND** `onCancelled()` callback MUST fire

#### Scenario: Cancel during processing
**GIVEN** voice input command is PROCESSING
**WHEN** `cancel()` is called
**THEN** API request MUST be aborted
**AND** state MUST change to CANCELLED
**AND** audio file MUST be deleted
**AND** `onCancelled()` callback MUST fire

#### Scenario: Handle cancellation cleanup
**GIVEN** command is cancelled
**WHEN** cleanup occurs
**THEN** AudioRecorder MUST be released
**AND** temporary files MUST be deleted
**AND** API connections MUST be closed
**AND** listeners MUST be notified

---

### Requirement: VOICE-CMD-007 - Timeout Handling
The command MUST enforce timeouts to prevent hanging.

#### Scenario: Overall command timeout
**GIVEN** voice input command is executing
**WHEN** execution time exceeds 30 seconds
**THEN** command MUST be cancelled automatically
**AND** state MUST change to ERROR
**AND** error message MUST indicate timeout
**AND** resources MUST be cleaned up

#### Scenario: Recording timeout
**GIVEN** recording is in progress
**WHEN** recording duration exceeds 5 minutes
**THEN** recording MUST stop automatically
**AND** processing MUST begin with recorded audio
**AND** user MAY be notified of timeout

---

### Requirement: VOICE-CMD-008 - Error Handling
The command MUST handle all errors gracefully.

#### Scenario: Handle permission denied
**GIVEN** RECORD_AUDIO permission not granted
**WHEN** command executes
**THEN** execution MUST fail immediately
**AND** error message MUST indicate permission required
**AND** state MUST be ERROR

#### Scenario: Handle network errors
**GIVEN** transcription request in progress
**WHEN** network error occurs
**THEN** command MUST retry once
**AND** if retry fails, state MUST be ERROR
**AND** error message MUST indicate network issue

#### Scenario: Handle empty transcription
**GIVEN** transcription completed
**WHEN** result is empty or whitespace only
**THEN** command MAY retry recording
**OR** state MUST be ERROR with message "No speech detected"

#### Scenario: Handle API quota exceeded
**GIVEN** transcription request sent
**WHEN** API returns quota exceeded
**THEN** state MUST be ERROR
**AND** error message MUST mention API quota limit
**AND** MUST suggest checking API key

---

### Requirement: VOICE-CMD-009 - Progress Callbacks
The command MUST provide detailed progress information.

#### Scenario: Recording progress
**GIVEN** recording in progress
**WHEN** audio level changes
**THEN** `onAudioLevelChanged(int)` SHOULD fire
**AND** level MUST be 0-100 value

#### Scenario: Processing progress
**GIVEN** transcription in progress
**WHEN** processing state is active
**THEN** `onProcessingStarted()` callback MUST fire
**AND** UI SHOULD show "Processing..." indicator

---

### Requirement: VOICE-CMD-010 - Resource Management
The command MUST properly manage all resources.

#### Scenario: Clean up on completion
**GIVEN** command completed successfully
**WHEN** cleanup is performed
**THEN** audio file MUST be deleted
**AND** AudioRecorder MUST be released
**AND** listeners MAY be cleared

#### Scenario: Clean up on error
**GIVEN** command failed with error
**WHEN** cleanup is performed
**THEN** partial audio file MUST be deleted
**AND** AudioRecorder MUST be released
**AND** API connections MUST be closed
**AND** state MUST be ERROR
