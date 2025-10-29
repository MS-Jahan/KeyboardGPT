# Proposal: Gemini Audio Support

## Change ID
`gemini-audio-support`

## Summary
Extend the existing GeminiClient to support audio file uploads and transcription using Gemini API's multimodal audio understanding capabilities.

## Motivation
The existing GeminiClient only handles text-based prompts. To enable voice-to-text functionality, we need to add support for audio file uploads and transcription requests to Gemini's audio understanding endpoint.

## Goals
- Extend GeminiClient to support audio file uploads via Files API
- Implement audio transcription request flow
- Support multiple audio formats (MP3, WAV, AAC)
- Handle audio-specific API errors
- Return transcribed text to caller

## Non-Goals
- Audio streaming (initial version uses file upload)
- Real-time transcription
- Speaker identification
- Audio manipulation or editing
- TTS (text-to-speech) generation

## Scope
- **Affected Components**: GeminiClient, InternetProvider
- **New Files**:
  - AudioUploadPublisher.java
  - GeminiFileUploader.java
- **Modified Files**:
  - GeminiClient.java
  - LanguageModelClient.java

## Dependencies
- `audio-recorder-service` - Produces audio files to upload
- Existing Gemini API key configuration
- Existing HTTP network infrastructure

## Risks
- Gemini Files API may have rate limits
- Large audio files may timeout
- Network errors during upload
- API quota exhaustion

## Alternatives Considered
- OpenAI Whisper API - rejected to keep using single Gemini API
- Google Cloud Speech-to-Text - rejected due to separate API key requirement
- On-device speech recognition - rejected due to lower accuracy
