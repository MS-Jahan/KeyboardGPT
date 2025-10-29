# Tasks: Audio Recorder Service

## Implementation Tasks

### 1. Create AudioConfig Class
- [ ] Create `tn.amin.keyboard_gpt.audio.AudioConfig.java`
- [ ] Define audio format constants (MP3, WAV, AAC)
- [ ] Define sample rate (16kHz for speech)
- [ ] Define audio encoding (AAC_LC for MP3)
- [ ] Define bit rate (64kbps for speech)
- [ ] Add configuration builder pattern

### 2. Create AudioRecorderListener Interface
- [ ] Create `tn.amin.keyboard_gpt.audio.AudioRecorderListener.java`
- [ ] Define `onRecordingStarted()` callback
- [ ] Define `onRecordingStopped(File audioFile)` callback
- [ ] Define `onRecordingError(Exception error)` callback
- [ ] Define `onAudioLevelChanged(int level)` callback for visualization

### 3. Implement AudioRecorder Class
- [ ] Create `tn.amin.keyboard_gpt.audio.AudioRecorder.java`
- [ ] Initialize MediaRecorder with audio configuration
- [ ] Implement `startRecording()` method
- [ ] Implement `stopRecording()` method
- [ ] Implement `pauseRecording()` method (API 24+)
- [ ] Implement `resumeRecording()` method (API 24+)
- [ ] Implement audio level monitoring with timer
- [ ] Handle MediaRecorder state transitions
- [ ] Generate unique file names with timestamp
- [ ] Save to app cache directory

### 4. Error Handling
- [ ] Handle MediaRecorder initialization failures
- [ ] Handle insufficient storage errors
- [ ] Handle microphone in-use errors
- [ ] Handle recording timeout (max 5 minutes)
- [ ] Implement proper cleanup on error
- [ ] Add logging for debugging

### 5. Testing
- [ ] Test recording start/stop cycle
- [ ] Test pause/resume functionality
- [ ] Test audio file creation and accessibility
- [ ] Test error scenarios (no permission, storage full)
- [ ] Test audio level monitoring
- [ ] Test on multiple Android versions (24+)
- [ ] Verify audio file playable in media player
- [ ] Test recording length limits

### 6. Resource Management
- [ ] Implement proper MediaRecorder release
- [ ] Clean up temporary audio files on error
- [ ] Implement auto-cleanup of old recordings
- [ ] Handle memory efficiently

## Validation Criteria
- Audio successfully recorded to file
- File format compatible with Gemini API
- Audio quality suitable for speech recognition
- Proper error handling for all failure modes
- No memory leaks or resource retention

## Estimated Effort
6-8 hours
