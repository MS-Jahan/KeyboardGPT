# Proposal: Audio File Management

## Change ID
`audio-file-management`

## Summary
Implement comprehensive audio file lifecycle management including temporary storage, automatic cleanup, cache management, and storage quota handling to prevent storage bloat from voice recordings.

## Motivation
Voice recordings create temporary audio files that need proper management. Without cleanup, these files accumulate and waste device storage. We need automatic cleanup, cache management, and storage-aware handling.

## Goals
- Store audio files in app cache directory (auto-cleaned by system)
- Implement automatic cleanup of old recordings
- Handle low storage scenarios gracefully
- Provide manual cache clearing option in settings
- Track storage usage for voice recordings
- Clean up on app uninstall automatically

## Non-Goals
- User-accessible recording archive
- Recording playback feature
- Cloud storage of recordings
- Recording editing/trimming
- Permanent recording storage

## Scope
- **Affected Components**: File storage, cache management
- **New Files**:
  - AudioFileManager.java
  - AudioCacheConfig.java
- **Modified Files**:
  - None directly, but integrates with AudioRecorder

## Dependencies
- `audio-recorder-service` - Produces files to manage
- Android cache directory APIs
- File I/O operations

## Risks
- Cache may be cleared by system unexpectedly
- Storage full scenarios
- File deletion race conditions
- Permissions issues on some devices

## Alternatives Considered
- External storage - rejected due to permissions, persistence
- Internal app storage - rejected due to persistence across app updates
- SQLite database - rejected as overkill for temporary files
- No management - rejected due to storage bloat
