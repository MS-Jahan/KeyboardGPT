# Proposal: Audio Recorder Service

## Change ID
`audio-recorder-service`

## Summary
Implement an AudioRecorderService to capture voice input from the user's microphone and save it as an audio file suitable for Gemini API processing.

## Motivation
To enable voice-to-text functionality, we need a reliable audio recording mechanism that captures user speech, handles recording states, and produces audio files in formats supported by Gemini API (MP3, WAV, AAC, etc.).

## Goals
- Implement AudioRecorder class using Android MediaRecorder API
- Support MP3 and WAV audio formats
- Handle recording lifecycle (start, pause, stop)
- Provide audio level monitoring for UI feedback
- Save audio files to app's private cache directory
- Handle recording errors gracefully

## Non-Goals
- Audio streaming (not needed for initial implementation)
- Real-time audio processing
- Audio editing or manipulation
- Background recording service

## Scope
- **Affected Components**: None (new component)
- **New Files**:
  - AudioRecorder.java
  - AudioRecorderListener.java
  - AudioConfig.java
- **Modified Files**: None

## Dependencies
- `add-audio-permissions` - Requires RECORD_AUDIO permission

## Risks
- MediaRecorder can fail on some devices due to hardware issues
- Audio file size may grow large for long recordings
- Some Android versions have MediaRecorder bugs

## Alternatives Considered
- AudioRecord API (lower level) - rejected due to complexity, MP3 encoding challenges
- Third-party audio libraries - rejected to minimize dependencies
- System voice recorder - rejected due to poor integration
