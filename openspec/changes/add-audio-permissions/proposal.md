# Proposal: Add Audio Recording Permissions

## Change ID
`add-audio-permissions`

## Summary
Add necessary Android permissions to enable audio recording for voice-to-text functionality via Gemini API.

## Motivation
Currently, KeyboardGPT only supports text input patterns. Users want to use voice input to transcribe speech to text using Gemini API's audio understanding capabilities. This requires RECORD_AUDIO permission and proper runtime permission handling.

## Goals
- Add RECORD_AUDIO permission to AndroidManifest.xml
- Implement runtime permission request flow
- Handle permission denial gracefully
- Provide user-friendly permission explanation

## Non-Goals
- Audio playback permissions (not needed for voice input)
- Background recording (only active recording during user interaction)
- Always-on voice detection

## Scope
- **Affected Components**: AndroidManifest.xml, Permission handling utilities
- **New Files**: PermissionManager.java
- **Modified Files**: AndroidManifest.xml

## Dependencies
- None (foundational change for all voice features)

## Risks
- Users may deny permission, requiring clear UX for fallback
- Android 13+ requires more granular permission handling

## Alternatives Considered
- Using system TTS/STT without custom permissions - rejected because it doesn't give us access to Gemini's superior transcription
- Recording via external app - rejected due to poor UX and integration complexity
