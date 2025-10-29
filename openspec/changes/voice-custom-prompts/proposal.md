# Proposal: Voice Custom Prompts

## Change ID
`voice-custom-prompts`

## Summary
Add UI to customize the transcription prompt sent to Gemini API, allowing users to request specific output formats (e.g., "Transcribe and add punctuation", "Transcribe and correct grammar", "Transcribe in formal tone").

## Motivation
Different users have different needs for voice transcription. Some want raw transcription, others want corrected grammar, punctuation enhancement, or specific formatting. The current system sends a static "Generate a transcript of the speech" prompt, which limits flexibility.

## Goals
- Add settings UI to configure voice transcription prompt
- Provide preset prompt templates (raw, corrected, formal, etc.)
- Allow custom user-defined prompts
- Show current prompt in voice input dialog
- Save prompt preferences per-user
- Quick access to change prompt before recording

## Non-Goals
- Multi-language prompt translation
- AI-generated prompt suggestions
- Per-app prompt customization
- Voice command interpretation (that's separate feature)

## Scope
- **Affected Components**: Settings UI, Voice command, Gemini client
- **New Files**:
  - VoicePromptManager.java
  - VoicePromptPresets.java
  - dialog_voice_prompt_settings.xml
- **Modified Files**:
  - VoiceInputCommand.java
  - GeminiClient.java (submitAudioPrompt signature)
  - OtherSettingsDialogBox.java

## Dependencies
- `voice-input-command` - Uses the prompts
- `gemini-audio-support` - Sends prompts to API
- Existing settings dialog system

## Risks
- Bad custom prompts may produce poor transcriptions
- Users may not understand prompt engineering
- Longer prompts increase API cost/latency

## Alternatives Considered
- Hardcoded prompts only - rejected due to lack of flexibility
- AI Studio integration - rejected as too complex
- Per-recording prompt selection - considered as enhancement
- Voice command prefix (e.g., "formal: <speech>") - rejected as unintuitive
