# Spec: Voice Retry Mechanism

## MODIFIED Requirements

### Requirement: VOICE-CMD-007 - Timeout Handling (MODIFIED)
The command MUST support retries before declaring timeout failures.

#### Scenario: Retry on network timeout
**GIVEN** transcription request times out
**WHEN** timeout occurs
**THEN** system MUST retry up to 3 times
**AND** MUST use exponential backoff between retries
**AND** MUST show retry status in UI

---

### Requirement: AUDIO-FILE-005 - Cleanup Triggers (MODIFIED)
Audio files MUST be preserved until transcription succeeds or user cancels.

#### Scenario: Preserve audio for retry
**GIVEN** audio file uploaded
**WHEN** transcription is in progress or retrying
**THEN** audio file MUST NOT be deleted
**AND** file MUST be marked as "pending"
**AND** file MUST only be deleted after success or permanent failure

---

## ADDED Requirements

### Requirement: RETRY-001 - Automatic Retry Logic
The system MUST automatically retry failed API operations with exponential backoff.

#### Scenario: Auto-retry on network failure
**GIVEN** API request fails with network error
**WHEN** failure is detected
**THEN** system MUST classify error as retryable
**AND** MUST retry up to 3 times
**AND** MUST use exponential backoff (1s, 2s, 4s)
**AND** MUST show retry attempt in UI

#### Scenario: Auto-retry on API timeout
**GIVEN** API request times out
**WHEN** timeout occurs
**THEN** system MUST classify as retryable
**AND** MUST retry up to 3 times
**AND** MUST increase timeout for each retry

#### Scenario: Auto-retry on 5xx errors
**GIVEN** API returns 500-599 status code
**WHEN** error is detected
**THEN** system MUST classify as retryable
**AND** MUST retry up to 3 times
**AND** MUST wait between retries

#### Scenario: No retry on 4xx errors (except 429)
**GIVEN** API returns 400-499 status code (except 429)
**WHEN** error is detected
**THEN** system MUST classify as non-retryable
**AND** MUST NOT retry
**AND** MUST show error immediately

#### Scenario: Retry on rate limit (429)
**GIVEN** API returns 429 status code
**WHEN** rate limit error detected
**THEN** system MUST classify as retryable
**AND** MUST retry up to 3 times
**AND** MUST respect Retry-After header if present

---

### Requirement: RETRY-002 - Exponential Backoff
The system MUST implement exponential backoff between retry attempts.

#### Scenario: Calculate retry delay
**GIVEN** retry attempt N is about to start
**WHEN** delay is calculated
**THEN** delay MUST be base_delay × 2^(N-1)
**AND** base_delay MUST be 1 second
**AND** delay MUST be capped at 10 seconds
**AND** jitter MAY be added (±20%)

#### Scenario: First retry delay
**GIVEN** first retry attempt (N=1)
**WHEN** delay is calculated
**THEN** delay MUST be 1 second
**AND** system MUST wait before retrying

#### Scenario: Second retry delay
**GIVEN** second retry attempt (N=2)
**WHEN** delay is calculated
**THEN** delay MUST be 2 seconds
**AND** system MUST wait before retrying

#### Scenario: Third retry delay
**GIVEN** third retry attempt (N=3)
**WHEN** delay is calculated
**THEN** delay MUST be 4 seconds
**AND** system MUST wait before retrying

#### Scenario: Max delay cap
**GIVEN** calculated delay exceeds 10 seconds
**WHEN** delay is applied
**THEN** delay MUST be capped at 10 seconds
**AND** longer delays MUST NOT be used

---

### Requirement: RETRY-003 - Manual Retry Without Re-recording
The system MUST allow users to manually retry using cached audio.

#### Scenario: Show retry button on error
**GIVEN** voice transcription failed
**WHEN** error dialog is displayed
**THEN** dialog MUST show "Retry" button
**AND** audio file MUST be preserved
**AND** retry button MUST be prominent

#### Scenario: Manual retry skips recording
**GIVEN** user taps Retry button
**WHEN** retry begins
**THEN** system MUST skip recording phase
**AND** MUST use cached audio file
**AND** MUST go directly to upload/transcription
**AND** MUST show "Retrying..." in UI

#### Scenario: Manual retry resets counter
**GIVEN** auto-retries exhausted (3 attempts)
**AND** user taps manual Retry button
**WHEN** manual retry starts
**THEN** retry counter MUST reset to 0
**AND** system GETS another 3 auto-retry attempts
**AND** exponential backoff MUST restart

---

### Requirement: RETRY-004 - Audio File Preservation
The system MUST preserve audio files until transcription succeeds or user cancels permanently.

#### Scenario: Keep audio during retries
**GIVEN** transcription is retrying
**WHEN** retry is in progress
**THEN** audio file MUST exist on disk
**AND** file MUST NOT be auto-cleaned
**AND** file MUST be marked as "active"

#### Scenario: Delete audio after success
**GIVEN** transcription succeeded
**WHEN** text is inserted into keyboard
**THEN** audio file MUST be deleted
**AND** file MUST NOT be preserved further

#### Scenario: Delete audio on permanent cancel
**GIVEN** user cancels from error dialog
**AND** user does NOT tap Retry
**WHEN** dialog dismisses
**THEN** audio file MUST be deleted
**AND** file MUST NOT remain in cache

#### Scenario: Preserve audio on temporary cancel
**GIVEN** user cancels during retry
**WHEN** cancellation occurs
**THEN** audio file MAY be preserved
**AND** user SHOULD be able to retry later (future enhancement)

---

### Requirement: RETRY-005 - Error Classification
The system MUST correctly classify errors as retryable or non-retryable.

#### Scenario: Retryable errors
**GIVEN** error occurs
**WHEN** error is classified
**THEN** following MUST be retryable:
- Network timeout (SocketTimeoutException)
- Network unavailable (UnknownHostException, ConnectException)
- 5xx server errors (500, 502, 503, 504)
- 429 rate limit
- Connection reset
- SSL handshake failure (transient)

#### Scenario: Non-retryable errors
**GIVEN** error occurs
**WHEN** error is classified
**THEN** following MUST be non-retryable:
- 401 authentication failed
- 403 forbidden
- 400 bad request
- 404 not found
- Invalid API key
- Permission denied (RECORD_AUDIO)
- File not found
- Invalid audio format

---

### Requirement: RETRY-006 - Retry UI Feedback
The system MUST provide clear feedback during retry process.

#### Scenario: Show retry attempt number
**GIVEN** auto-retry is in progress
**WHEN** UI is updated
**THEN** message MUST show "Retrying... (N of 3)"
**AND** N MUST be current attempt number
**AND** message MUST be visible to user

#### Scenario: Show retry countdown
**GIVEN** system is waiting between retry attempts
**WHEN** delay is active
**THEN** UI MAY show countdown timer
**OR** UI SHOULD show "Retrying in N seconds..."
**AND** user MUST be able to cancel during wait

#### Scenario: Update processing view for retry
**GIVEN** processing dialog is visible
**WHEN** retry occurs
**THEN** dialog MUST update to show retry status
**AND** spinner MUST continue animating
**AND** cancel button MUST remain available

---

### Requirement: RETRY-007 - Retry State Management
The system MUST properly manage retry state throughout the process.

#### Scenario: Track retry attempts
**GIVEN** VoiceInputCommand is executing
**WHEN** retries occur
**THEN** current attempt count MUST be tracked
**AND** max attempts MUST be enforced (3)
**AND** state MUST reset on success

#### Scenario: Prevent concurrent retries
**GIVEN** retry is in progress
**WHEN** another retry is triggered
**THEN** system MUST ignore duplicate retry
**AND** MUST NOT start concurrent retry
**AND** current retry MUST continue

#### Scenario: Reset retry state on success
**GIVEN** transcription succeeds after retry
**WHEN** success is confirmed
**THEN** retry counter MUST reset to 0
**AND** retry state MUST be cleared
**AND** success flow MUST proceed normally

---

### Requirement: RETRY-008 - Cancellation During Retry
The system MUST allow cancellation during retry process.

#### Scenario: Cancel during retry delay
**GIVEN** system is waiting between retry attempts
**WHEN** user taps Cancel button
**THEN** wait MUST be interrupted
**AND** retry MUST be aborted
**AND** audio file MUST be deleted
**AND** dialog MUST dismiss

#### Scenario: Cancel during retry attempt
**GIVEN** retry attempt is in progress
**WHEN** user taps Cancel button
**THEN** API request MUST be aborted
**AND** retry MUST be cancelled
**AND** audio file MUST be deleted
**AND** dialog MUST dismiss

---

### Requirement: RETRY-009 - Retry Configuration
The system MUST use configurable retry parameters.

#### Scenario: Default retry configuration
**GIVEN** RetryPolicy is initialized
**WHEN** default configuration is used
**THEN** max attempts MUST be 3
**AND** base delay MUST be 1 second
**AND** max delay MUST be 10 seconds
**AND** jitter MUST be 20%

#### Scenario: Custom retry configuration (future)
**GIVEN** custom RetryPolicy is provided
**WHEN** configuration is applied
**THEN** custom values MUST be used
**AND** values MUST be validated (positive, reasonable)
**AND** invalid values MUST use defaults

---

### Requirement: RETRY-010 - Retry Logging
The system MUST log retry attempts for debugging.

#### Scenario: Log retry attempt
**GIVEN** retry is starting
**WHEN** retry begins
**THEN** system MUST log attempt number
**AND** MUST log error that triggered retry
**AND** MUST log delay duration
**AND** logs MUST appear in Xposed logs

#### Scenario: Log retry success
**GIVEN** retry succeeds
**WHEN** success occurs
**THEN** system MUST log success message
**AND** MUST log total attempts made
**AND** MUST log total time elapsed

#### Scenario: Log retry exhaustion
**GIVEN** all retries exhausted
**WHEN** final attempt fails
**THEN** system MUST log failure message
**AND** MUST log all attempted errors
**AND** MUST log reason for giving up
