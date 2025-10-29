# Tasks: Voice Input Command

## Implementation Tasks

### 1. Create VoiceCommandState Enum
- [ ] Create `tn.amin.keyboard_gpt.instruction.command.VoiceCommandState.java`
- [ ] Define states: IDLE, RECORDING, PROCESSING, COMPLETED, ERROR, CANCELLED
- [ ] Add state transition validation methods

### 2. Create VoiceInputCommand Class
- [ ] Create `tn.amin.keyboard_gpt.instruction.command.VoiceInputCommand.java`
- [ ] Extend AbstractCommand base class
- [ ] Implement execute() method with async handling
- [ ] Implement cancel() method
- [ ] Add state management
- [ ] Add listener callbacks for state changes

### 3. Implement Voice Input Workflow
- [ ] Initialize AudioRecorder with config
- [ ] Start recording on command execute
- [ ] Handle recording completion callback
- [ ] Get audio file from recorder
- [ ] Pass audio file to GeminiClient
- [ ] Get transcription result
- [ ] Insert transcription into keyboard input
- [ ] Clean up temporary audio file

### 4. State Management
- [ ] Track current command state
- [ ] Emit state change events
- [ ] Validate state transitions
- [ ] Handle concurrent state changes
- [ ] Prevent duplicate executions

### 5. Error Handling
- [ ] Handle recording errors
- [ ] Handle network/API errors
- [ ] Handle transcription errors (empty result)
- [ ] Handle permission errors
- [ ] Provide user-friendly error messages
- [ ] Clean up on error

### 6. Progress Callbacks
- [ ] Define VoiceInputListener interface
- [ ] Implement onRecordingStarted() callback
- [ ] Implement onRecordingStopped() callback
- [ ] Implement onProcessingStarted() callback
- [ ] Implement onTranscriptionReceived(String) callback
- [ ] Implement onError(String) callback
- [ ] Implement onCancelled() callback

### 7. Cancellation Support
- [ ] Implement cancel() method
- [ ] Stop audio recording if in progress
- [ ] Cancel API request if in progress
- [ ] Clean up resources
- [ ] Update state to CANCELLED
- [ ] Fire cancellation callback

### 8. Integration with Keyboard
- [ ] Get IMSController instance
- [ ] Use commit() method to insert text
- [ ] Handle cursor position correctly
- [ ] Support undo functionality (future)

### 9. Integration with Command System
- [ ] Register VoiceInputCommand in Commands enum
- [ ] Add command identifier
- [ ] Integrate with CommandManager
- [ ] Add command to available commands list

### 10. Timeout Handling
- [ ] Implement overall command timeout (30 seconds)
- [ ] Handle individual step timeouts
- [ ] Cancel and report timeout errors
- [ ] Clean up on timeout

### 11. Testing
- [ ] Test complete voice input flow
- [ ] Test with various speech lengths
- [ ] Test error scenarios
- [ ] Test cancellation at each step
- [ ] Test concurrent command attempts
- [ ] Test permission denied scenario
- [ ] Test network failure scenarios
- [ ] Verify text insertion accuracy

## Validation Criteria
- Voice input successfully transcribes and inserts text
- All state transitions work correctly
- Errors handled gracefully with user feedback
- Cancellation works at any stage
- No resource leaks or crashes
- Integration with existing keyboard commands

## Estimated Effort
6-8 hours
