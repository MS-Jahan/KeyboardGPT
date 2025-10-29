# Tasks: Voice Retry Mechanism

## Implementation Tasks

### 1. Create RetryPolicy Class
- [ ] Create `tn.amin.keyboard_gpt.retry.RetryPolicy.java`
- [ ] Define max retry attempts (3 default)
- [ ] Define base delay (1 second)
- [ ] Define max delay (10 seconds)
- [ ] Implement exponential backoff calculation
- [ ] Add retry attempt counter
- [ ] Add retry delay calculator

### 2. Create RetryableException Class
- [ ] Create `tn.amin.keyboard_gpt.retry.RetryableException.java`
- [ ] Extend Exception
- [ ] Add flag for retry eligibility
- [ ] Add retry count field
- [ ] Add original exception cause
- [ ] Add factory methods for common failures

### 3. Error Classification
- [ ] Classify network errors as retryable
- [ ] Classify timeout errors as retryable
- [ ] Classify 5xx API errors as retryable
- [ ] Classify 4xx API errors as non-retryable (except 429)
- [ ] Classify permission errors as non-retryable
- [ ] Classify invalid API key as non-retryable
- [ ] Add isRetryable() method to exceptions

### 4. Update GeminiClient for Retry
- [ ] Wrap network operations in retry logic
- [ ] Implement retry for file upload
- [ ] Implement retry for transcription request
- [ ] Add retry delay between attempts
- [ ] Log retry attempts for debugging
- [ ] Throw RetryableException on failure

### 5. Update VoiceInputCommand for Auto-Retry
- [ ] Catch RetryableException from GeminiClient
- [ ] Implement retry loop with counter
- [ ] Calculate delay using exponential backoff
- [ ] Sleep/wait between retry attempts
- [ ] Update UI with retry status ("Retry 2 of 3...")
- [ ] Give up after max retries and show error

### 6. Audio File Preservation
- [ ] Modify AudioFileManager to keep audio after upload
- [ ] Mark audio file as "pending deletion"
- [ ] Delete audio only after successful transcription
- [ ] OR delete after user cancels permanently
- [ ] Store audio file reference in VoiceInputCommand

### 7. Manual Retry Implementation
- [ ] Add "Retry" button to error dialog
- [ ] Store audio file path in error state
- [ ] On retry, skip recording phase
- [ ] On retry, go directly to upload/transcription
- [ ] Reuse existing audio file
- [ ] Reset retry counter for manual retry
- [ ] Update UI to show "Retrying..."

### 8. Retry UI Feedback
- [ ] Show retry attempt number in dialog
- [ ] Display "Retrying... (2 of 3)" message
- [ ] Show countdown timer during retry delay
- [ ] Update spinner/progress indicator
- [ ] Distinguish auto-retry from manual retry
- [ ] Allow cancellation during retry

### 9. Exponential Backoff Implementation
- [ ] Calculate delay: base_delay * 2^(attempt - 1)
- [ ] Cap delay at max_delay (10 seconds)
- [ ] Add jitter to prevent thundering herd
- [ ] Use Thread.sleep() or Handler.postDelayed()
- [ ] Allow cancellation during delay

### 10. Error Message Enhancement
- [ ] Show retry-specific error messages
- [ ] "Failed after 3 attempts. Please retry."
- [ ] "Network error. Retrying... (2 of 3)"
- [ ] Distinguish between auto-failed and user-cancelled
- [ ] Provide actionable guidance

### 11. Retry State Management
- [ ] Track current retry attempt
- [ ] Track retry state (RETRYING, RETRY_EXHAUSTED)
- [ ] Prevent concurrent retries
- [ ] Reset retry state on success
- [ ] Clean up retry state on cancel

### 12. Testing
- [ ] Test successful retry after 1 failure
- [ ] Test successful retry after 2 failures
- [ ] Test failure after 3 retry attempts
- [ ] Test exponential backoff delays
- [ ] Test manual retry button
- [ ] Test audio file preservation
- [ ] Test audio file cleanup after success
- [ ] Test cancellation during retry
- [ ] Test retryable vs non-retryable errors
- [ ] Test network timeout scenarios

### 13. Configuration
- [ ] Make max retry attempts configurable (default 3)
- [ ] Make base delay configurable (default 1s)
- [ ] Make max delay configurable (default 10s)
- [ ] Store in SharedPreferences if user-configurable
- [ ] OR hardcode for simplicity

### 14. Logging and Debugging
- [ ] Log each retry attempt
- [ ] Log retry delays
- [ ] Log retry success/failure
- [ ] Log final outcome after retries exhausted
- [ ] Include retry count in error logs

## Validation Criteria
- Transient failures automatically retry 2-3 times
- Users can manually retry without re-recording
- Audio files preserved until transcription succeeds
- Exponential backoff prevents API flooding
- Clear UI feedback during retry process
- Non-retryable errors fail immediately
- No crashes or hangs during retry

## Estimated Effort
5-6 hours
