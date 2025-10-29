# Proposal: Voice UI Feedback

## Change ID
`voice-ui-feedback`

## Summary
Implement visual and haptic feedback UI for voice input, showing recording status, audio levels, processing state, and errors to provide clear user experience during voice-to-text operations.

## Motivation
Voice input requires clear visual feedback so users know when to speak, when the system is listening, processing, and when transcription is complete. Without feedback, users won't know if their speech is being captured or if errors occurred.

## Goals
- Display recording indicator during audio capture
- Show real-time audio level visualization
- Display "Processing..." indicator during transcription
- Show success feedback with transcribed text preview
- Display error messages clearly
- Support haptic feedback for state changes
- Use floating overlay (non-intrusive)

## Non-Goals
- Custom keyboard UI redesign
- Animated voice visualizations (keep simple)
- Sound effects for feedback
- Persistent notification
- Full-screen voice UI

## Scope
- **Affected Components**: UI system, DialogActivity
- **New Files**:
  - VoiceInputDialog.java
  - VoiceRecordingView.xml
  - VoiceProcessingView.xml
- **Modified Files**:
  - DialogActivity.java
  - UiInteractor.java

## Dependencies
- `voice-input-command` - Receives state change events
- Existing dialog system
- Android overlay permissions

## Risks
- Overlay may not display on all Android versions
- Some apps may block overlays
- UI may conflict with keyboard UI
- Animation may lag on old devices

## Alternatives Considered
- Toast messages only - rejected due to poor UX, not real-time
- Notification - rejected due to slow, not visible during typing
- Keyboard bar - rejected due to complexity of keyboard UI modification
- Full-screen activity - rejected due to poor UX, disruptive
