# Proposal: Voice Input Command

## Change ID
`voice-input-command`

## Summary
Implement a VoiceInputCommand that orchestrates the voice-to-text workflow: recording audio, uploading to Gemini, getting transcription, and inserting text into the keyboard input field.

## Motivation
To provide a complete voice input feature, we need a command that coordinates all components: audio recording, API communication, and text insertion. This follows the existing command pattern used in KeyboardGPT.

## Goals
- Create VoiceInputCommand following existing command pattern
- Orchestrate: record → upload → transcribe → insert text
- Handle command lifecycle and state management
- Provide progress callbacks for UI
- Support command cancellation
- Integrate with existing GenerativeAIController

## Non-Goals
- Custom voice command recognition (e.g., "Hey GPT")
- Voice commands for app control
- Continuous listening mode
- Multi-language selection UI (uses device default)

## Scope
- **Affected Components**: Command system, GenerativeAIController
- **New Files**:
  - VoiceInputCommand.java
  - VoiceCommandState.java
- **Modified Files**:
  - Commands.java
  - CommandManager.java

## Dependencies
- `audio-recorder-service` - Recording functionality
- `gemini-audio-support` - Transcription API
- Existing command infrastructure

## Risks
- Command may take significant time (5-15 seconds)
- Network failures during any step
- User may start typing during processing
- Interruption handling complexity

## Alternatives Considered
- Separate service instead of command - rejected to maintain consistency
- Direct keyboard hook without command - rejected due to poor architecture
- Background service - rejected for initial version (privacy concerns)
