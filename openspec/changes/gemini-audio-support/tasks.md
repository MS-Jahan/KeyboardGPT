# Tasks: Gemini Audio Support

## Implementation Tasks

### 1. Create GeminiFileUploader Utility
- [ ] Create `tn.amin.keyboard_gpt.llm.client.GeminiFileUploader.java`
- [ ] Implement Files API upload endpoint integration
- [ ] Implement resumable upload protocol (for large files)
- [ ] Implement simple upload for small files (<20MB)
- [ ] Return file URI and metadata
- [ ] Handle upload progress for UI feedback
- [ ] Add timeout handling (60 second default)

### 2. Create AudioUploadPublisher
- [ ] Create `tn.amin.keyboard_gpt.llm.publisher.AudioUploadPublisher.java`
- [ ] Extend reactive Publisher pattern
- [ ] Handle multi-step flow: upload → generateContent
- [ ] Implement error propagation
- [ ] Add cancellation support

### 3. Extend GeminiClient for Audio
- [ ] Add `submitAudioPrompt(File audioFile, String prompt)` method to GeminiClient
- [ ] Implement audio file upload via Files API
- [ ] Build request JSON with file reference
- [ ] Handle transcription response parsing
- [ ] Extract text from response candidates
- [ ] Add audio-specific error handling

### 4. Update LanguageModelClient Base Class
- [ ] Add abstract `submitAudioPrompt()` method (optional, returns error)
- [ ] Allow other clients to not implement if unsupported
- [ ] Add `supportsAudio()` capability check method

### 5. Audio Format Validation
- [ ] Validate audio MIME types before upload
- [ ] Check file size limits (<2GB per Gemini docs)
- [ ] Verify file exists and is readable
- [ ] Add format conversion suggestion on unsupported format

### 6. API Request Implementation
- [ ] Use `generateContent` endpoint with file part
- [ ] Set appropriate prompt: "Generate a transcript of the speech"
- [ ] Configure response parameters (temperature, tokens, etc.)
- [ ] Handle streaming vs. non-streaming responses
- [ ] Parse JSON response for transcribed text

### 7. Error Handling
- [ ] Handle file upload failures (network, timeout)
- [ ] Handle API errors (quota exceeded, invalid format)
- [ ] Handle empty transcriptions
- [ ] Handle malformed API responses
- [ ] Add retry logic for transient failures

### 8. Testing
- [ ] Test with MP3 audio files
- [ ] Test with WAV audio files
- [ ] Test with AAC audio files
- [ ] Test with various file sizes (1KB to 100MB)
- [ ] Test with different speech content (English, accents)
- [ ] Test error scenarios (invalid file, no API key)
- [ ] Verify transcription accuracy
- [ ] Test concurrent requests

### 9. Integration
- [ ] Integrate with existing API key management
- [ ] Reuse existing InternetProvider infrastructure
- [ ] Maintain consistent error handling with text prompts
- [ ] Add logging for debugging

## Validation Criteria
- Audio files successfully uploaded to Gemini Files API
- Transcription requests return accurate text
- All supported audio formats work correctly
- Error handling provides clear feedback
- No memory leaks during upload/processing

## Estimated Effort
8-10 hours
