# Spec: Audio File Storage Management

## ADDED Requirements

### Requirement: AUDIO-FILE-001 - File Storage Location
Audio recordings MUST be stored in appropriate temporary storage.

#### Scenario: Store in cache directory
**GIVEN** audio file needs to be created
**WHEN** file path is determined
**THEN** file MUST be stored in app cache directory
**AND** path MUST be Context.getCacheDir() + "/voice_recordings/"
**AND** subdirectory MUST be created if not exists

#### Scenario: Generate unique filename
**GIVEN** new recording is starting
**WHEN** filename is generated
**THEN** format MUST be "voice_YYYYMMDD_HHmmss_SSS.{ext}"
**AND** timestamp MUST include milliseconds
**AND** extension MUST match audio format (mp3, aac, wav)
**AND** filename MUST be unique (no collisions)

---

### Requirement: AUDIO-FILE-002 - Automatic Cleanup by Age
The system MUST automatically delete old audio recordings.

#### Scenario: Delete files older than threshold
**GIVEN** cleanup is triggered
**WHEN** file age is checked
**THEN** files older than 1 hour MUST be deleted
**AND** deletion MUST be logged
**AND** deletion errors MUST be handled gracefully

#### Scenario: Preserve recent files
**GIVEN** cleanup is triggered
**WHEN** files are evaluated
**THEN** files newer than 1 hour MUST NOT be deleted
**AND** currently active files MUST NOT be deleted

---

### Requirement: AUDIO-FILE-003 - Automatic Cleanup by Count
The system MUST limit the number of cached recordings.

#### Scenario: Enforce maximum file count
**GIVEN** cleanup is triggered
**WHEN** file count exceeds limit
**THEN** oldest files MUST be deleted first
**AND** most recent 10 files MUST be kept
**AND** active files MUST NOT be deleted

#### Scenario: Sort files by creation time
**GIVEN** file count needs to be reduced
**WHEN** files are sorted
**THEN** sorting MUST be by last modified time
**AND** oldest files MUST be identified correctly

---

### Requirement: AUDIO-FILE-004 - Automatic Cleanup by Size
The system MUST limit total cache size for audio recordings.

#### Scenario: Enforce maximum cache size
**GIVEN** cleanup is triggered
**WHEN** total cache size exceeds 100 MB
**THEN** oldest files MUST be deleted until size < 100 MB
**AND** at least 1 file MUST remain if possible
**AND** deletion MUST stop when target reached

#### Scenario: Calculate cache size
**GIVEN** cache size needs to be determined
**WHEN** calculation is performed
**THEN** all files in voice_recordings/ MUST be included
**AND** size MUST be sum of file sizes in bytes
**AND** calculation MUST handle file access errors

---

### Requirement: AUDIO-FILE-005 - Cleanup Triggers
The system MUST trigger cleanup at appropriate times.

#### Scenario: Cleanup on app start
**GIVEN** KeyboardGPT module loads
**WHEN** initialization completes
**THEN** AudioFileManager MUST run cleanup
**AND** cleanup MUST be asynchronous
**AND** cleanup MUST not block initialization

#### Scenario: Cleanup after recording
**GIVEN** voice recording completes
**WHEN** audio file is processed
**THEN** cleanup SHOULD be triggered
**AND** cleanup MUST not affect current recording

#### Scenario: Periodic cleanup
**GIVEN** app is running
**WHEN** 6 hours have elapsed since last cleanup
**THEN** cleanup SHOULD be triggered
**AND** cleanup MUST use WorkManager or AlarmManager
**AND** cleanup MUST respect battery optimization

---

### Requirement: AUDIO-FILE-006 - Storage Space Checking
The system MUST verify sufficient storage before recording.

#### Scenario: Check available space before recording
**GIVEN** voice recording is about to start
**WHEN** storage space is checked
**THEN** available space MUST be >= 50 MB
**AND** if insufficient, error MUST be shown
**AND** recording MUST NOT start

#### Scenario: Handle low storage scenario
**GIVEN** storage space is insufficient
**WHEN** recording is attempted
**THEN** system MAY trigger cleanup first
**AND** if space still insufficient, MUST show error
**AND** error message MUST indicate storage issue

---

### Requirement: AUDIO-FILE-007 - Active File Protection
The system MUST NOT delete files currently in use.

#### Scenario: Track active files
**GIVEN** audio recording or upload in progress
**WHEN** cleanup is triggered
**THEN** active file MUST be marked as in-use
**AND** in-use files MUST NOT be deleted
**AND** in-use flag MUST be cleared after completion

#### Scenario: Handle concurrent access
**GIVEN** multiple operations accessing files
**WHEN** cleanup runs
**THEN** system MUST synchronize file access
**AND** MUST prevent deletion of files being read
**AND** MUST handle race conditions safely

---

### Requirement: AUDIO-FILE-008 - Manual Cache Management
The system MUST allow users to manually clear voice cache.

#### Scenario: Manual cache clear via settings
**GIVEN** user opens settings
**WHEN** "Clear voice cache" button is tapped
**THEN** confirmation dialog MUST appear
**AND** dialog MUST show current cache size
**AND** if confirmed, all cached files MUST be deleted
**AND** active files SHOULD be preserved

#### Scenario: Display cache statistics
**GIVEN** settings screen is open
**WHEN** cache section is visible
**THEN** current cache size MUST be displayed
**AND** file count MUST be displayed
**AND** values MUST be updated after cleanup

---

### Requirement: AUDIO-FILE-009 - Error Handling
The system MUST handle all file management errors gracefully.

#### Scenario: Handle cache directory creation failure
**GIVEN** cache directory doesn't exist
**WHEN** directory creation is attempted
**THEN** if creation fails, error MUST be logged
**AND** recording MUST fail with clear error message
**AND** system MUST NOT crash

#### Scenario: Handle file deletion failure
**GIVEN** cleanup attempts to delete file
**WHEN** deletion fails (permissions, locked file, etc.)
**THEN** error MUST be logged
**AND** cleanup MUST continue with other files
**AND** failed file SHOULD be retried next cleanup

#### Scenario: Handle file access permission errors
**GIVEN** file operation is attempted
**WHEN** permission denied error occurs
**THEN** error MUST be caught and logged
**AND** user-friendly error MUST be shown
**AND** alternative action MAY be suggested

---

### Requirement: AUDIO-FILE-010 - Cleanup Configuration
The system MUST use configurable limits for cache management.

#### Scenario: Default configuration values
**GIVEN** AudioCacheConfig is initialized
**WHEN** default values are used
**THEN** max age MUST be 1 hour
**AND** max file count MUST be 10
**AND** max cache size MUST be 100 MB
**AND** cleanup interval MUST be 6 hours

#### Scenario: Allow configuration override
**GIVEN** custom configuration is provided
**WHEN** AudioFileManager is initialized
**THEN** custom values MUST be used
**AND** values MUST be validated (positive, reasonable)
**AND** invalid values MUST use defaults

---

### Requirement: AUDIO-FILE-011 - Deletion Safety
The system MUST safely delete files without data loss.

#### Scenario: Verify file before deletion
**GIVEN** file is marked for deletion
**WHEN** deletion is performed
**THEN** file path MUST be validated
**AND** MUST NOT delete files outside cache directory
**AND** MUST NOT delete system files

#### Scenario: Atomic deletion
**GIVEN** file deletion is in progress
**WHEN** deletion completes
**THEN** file MUST be completely removed
**OR** deletion MUST be rolled back on error
**AND** partial deletions MUST NOT occur

---

### Requirement: AUDIO-FILE-012 - Background Processing
Cache cleanup MUST run efficiently in the background.

#### Scenario: Use background worker
**GIVEN** periodic cleanup is scheduled
**WHEN** cleanup executes
**THEN** MUST use WorkManager for scheduling
**AND** work MUST have KEEP constraint (run when idle)
**AND** work MUST respect battery optimization
**AND** work MUST complete within timeout (5 minutes)

#### Scenario: Handle worker cancellation
**GIVEN** cleanup worker is running
**WHEN** worker is cancelled (app killed, etc.)
**THEN** partial cleanup MUST be safe
**AND** next cleanup MUST complete the job
**AND** no files MUST be left in corrupted state
