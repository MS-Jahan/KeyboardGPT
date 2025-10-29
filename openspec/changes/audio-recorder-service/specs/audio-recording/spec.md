# Spec: Audio Recording Service

## ADDED Requirements

### Requirement: AUDIO-REC-001 - Audio Configuration
The system MUST provide configurable audio recording parameters optimized for speech.

#### Scenario: Default speech configuration
**GIVEN** AudioConfig class
**WHEN** default configuration is requested
**THEN** sample rate MUST be 16000 Hz
**AND** encoding MUST be AAC
**AND** bit rate MUST be 64000 bps
**AND** channel MUST be MONO
**AND** output format MUST be MP3 or AAC

#### Scenario: Custom configuration
**GIVEN** AudioConfig builder
**WHEN** custom parameters are set
**THEN** configuration MUST validate parameters
**AND** MUST reject invalid sample rates
**AND** MUST reject unsupported formats

---

### Requirement: AUDIO-REC-002 - Recording Lifecycle
The AudioRecorder MUST manage recording state transitions correctly.

#### Scenario: Start recording
**GIVEN** AudioRecorder is initialized
**AND** RECORD_AUDIO permission is granted
**WHEN** `startRecording()` is called
**THEN** MediaRecorder MUST be prepared
**AND** recording MUST start
**AND** state MUST change to RECORDING
**AND** `onRecordingStarted()` callback MUST fire
**AND** output file path MUST be set

#### Scenario: Stop recording
**GIVEN** AudioRecorder is in RECORDING state
**WHEN** `stopRecording()` is called
**THEN** recording MUST stop
**AND** MediaRecorder MUST be released
**AND** state MUST change to STOPPED
**AND** audio file MUST be finalized
**AND** `onRecordingStopped(File)` callback MUST fire with audio file

#### Scenario: Pause recording (API 24+)
**GIVEN** AudioRecorder is in RECORDING state
**AND** Android API level >= 24
**WHEN** `pauseRecording()` is called
**THEN** recording MUST pause
**AND** state MUST change to PAUSED
**AND** callback MUST fire

#### Scenario: Resume recording (API 24+)
**GIVEN** AudioRecorder is in PAUSED state
**AND** Android API level >= 24
**WHEN** `resumeRecording()` is called
**THEN** recording MUST resume
**AND** state MUST change to RECORDING
**AND** callback MUST fire

---

### Requirement: AUDIO-REC-003 - File Management
The system MUST manage audio file creation and storage appropriately.

#### Scenario: Generate unique filename
**GIVEN** AudioRecorder is starting
**WHEN** output file is created
**THEN** filename MUST include timestamp
**AND** filename MUST include format extension (.mp3 or .aac)
**AND** filename pattern MUST be "voice_recording_YYYYMMDD_HHMMSS.{ext}"

#### Scenario: Save to cache directory
**GIVEN** audio recording is in progress
**WHEN** file is being written
**THEN** file MUST be saved to app's cache directory
**AND** path MUST be accessible via Context.getCacheDir()
**AND** file MUST have read/write permissions for app

#### Scenario: Verify file after recording
**GIVEN** recording has stopped
**WHEN** `onRecordingStopped()` callback is fired
**THEN** audio file MUST exist at specified path
**AND** file size MUST be greater than 0 bytes
**AND** file MUST be readable
**AND** file MUST be in valid audio format

---

### Requirement: AUDIO-REC-004 - Audio Level Monitoring
The system MUST provide real-time audio level feedback during recording.

#### Scenario: Monitor audio amplitude
**GIVEN** AudioRecorder is in RECORDING state
**WHEN** monitoring is active
**THEN** system MUST sample audio level every 100ms
**AND** MUST call `onAudioLevelChanged(int)` callback
**AND** level value MUST be between 0-100
**AND** level MUST reflect actual microphone input volume

#### Scenario: Stop monitoring on recording stop
**GIVEN** audio level monitoring is active
**WHEN** recording stops
**THEN** monitoring MUST stop
**AND** no more callbacks MUST fire

---

### Requirement: AUDIO-REC-005 - Error Handling
The system MUST handle all recording errors gracefully.

#### Scenario: Handle initialization failure
**GIVEN** MediaRecorder initialization is attempted
**WHEN** initialization fails (e.g., microphone in use)
**THEN** `onRecordingError(Exception)` callback MUST fire
**AND** error message MUST be descriptive
**AND** MediaRecorder MUST be properly released
**AND** state MUST return to IDLE

#### Scenario: Handle insufficient storage
**GIVEN** recording is in progress
**WHEN** device storage is full
**THEN** recording MUST stop
**AND** `onRecordingError(Exception)` callback MUST fire
**AND** error MUST indicate storage issue
**AND** partial file MUST be deleted

#### Scenario: Handle recording timeout
**GIVEN** recording is in progress
**WHEN** recording duration exceeds 5 minutes
**THEN** recording MUST stop automatically
**AND** `onRecordingStopped()` callback MUST fire
**AND** audio file MUST be available
**AND** user MUST be notified of timeout

#### Scenario: Handle permission loss during recording
**GIVEN** recording is in progress
**WHEN** RECORD_AUDIO permission is revoked
**THEN** recording MUST stop immediately
**AND** `onRecordingError(Exception)` callback MUST fire
**AND** error MUST indicate permission loss
**AND** resources MUST be released

---

### Requirement: AUDIO-REC-006 - Resource Cleanup
The system MUST properly clean up resources after recording.

#### Scenario: Release MediaRecorder on stop
**GIVEN** recording has stopped
**WHEN** cleanup is performed
**THEN** MediaRecorder MUST be released
**AND** audio level monitoring MUST be stopped
**AND** listeners MUST be cleared if requested

#### Scenario: Clean temporary files on error
**GIVEN** recording error occurred
**WHEN** error handling completes
**THEN** incomplete audio file MUST be deleted
**AND** MediaRecorder MUST be released
**AND** state MUST reset to IDLE

#### Scenario: Auto-cleanup old recordings
**GIVEN** app cache contains old audio files
**WHEN** new recording starts
**THEN** system MUST delete audio files older than 1 hour
**AND** MUST keep most recent 5 recordings
**AND** MUST not delete if file is currently in use
