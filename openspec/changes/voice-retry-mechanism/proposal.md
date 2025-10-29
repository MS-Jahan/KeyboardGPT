# Proposal: Voice Retry Mechanism

## Change ID
`voice-retry-mechanism`

## Summary
Implement intelligent retry mechanism for voice transcription that automatically retries failed API requests (upload/transcription) 2-3 times before showing error, and allows manual retry without re-recording audio.

## Motivation
Network and API failures are common. Users shouldn't need to re-record their speech when only the API call failed. Automatic retries improve success rate, while manual retry without re-recording saves time and provides better UX.

## Goals
- Auto-retry failed uploads 2-3 times with exponential backoff
- Auto-retry failed transcriptions 2-3 times with exponential backoff
- Allow manual retry using cached audio file (no re-recording)
- Show retry attempts in UI ("Retry 1 of 3...")
- Distinguish between retryable and non-retryable errors
- Delete cached audio only after successful transcription or user cancels

## Non-Goals
- Retry recording failures (audio capture cannot be retried)
- Retry permission errors (user must grant permission)
- Infinite retry loops
- Retry for invalid API keys (permanent failure)

## Scope
- **Affected Components**: Voice command, Gemini client, Audio file management
- **New Files**:
  - RetryPolicy.java
  - RetryableException.java
- **Modified Files**:
  - VoiceInputCommand.java
  - GeminiClient.java
  - AudioFileManager.java

## Dependencies
- `voice-input-command` - Orchestrates retry logic
- `gemini-audio-support` - API calls to retry
- `audio-file-management` - Preserves audio for retry

## Risks
- Too many retries delay user feedback
- Cached audio may be deleted accidentally
- Retry logic complexity
- Exponential backoff may still fail on persistent issues

## Alternatives Considered
- No retry mechanism - rejected due to poor UX
- Infinite retries - rejected due to hanging
- Always re-record - rejected due to poor UX
- Fixed delays between retries - rejected in favor of exponential backoff
- User-only retry (no auto-retry) - rejected as less resilient
