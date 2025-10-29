# Spec: Gemini Audio API Integration

## MODIFIED Requirements

### Requirement: LLM-CLIENT-001 - Language Model Client Interface (MODIFIED)
Language model clients MUST support audio input where available.

#### Scenario: Check audio support capability
**GIVEN** a LanguageModelClient instance
**WHEN** `supportsAudio()` is called
**THEN** it MUST return true if audio transcription is supported
**AND** GeminiClient MUST return true
**AND** other clients MAY return false

---

## ADDED Requirements

### Requirement: GEMINI-AUDIO-001 - Audio File Upload
GeminiClient MUST support uploading audio files to Gemini Files API.

#### Scenario: Upload audio file via Files API
**GIVEN** a valid audio file
**AND** Gemini API key is configured
**WHEN** `uploadAudioFile(File)` is called
**THEN** system MUST use resumable upload protocol
**AND** MUST POST to `https://generativelanguage.googleapis.com/upload/v1beta/files`
**AND** MUST include `x-goog-api-key` header
**AND** MUST set `X-Goog-Upload-Protocol: resumable`
**AND** MUST return file URI on success

#### Scenario: Handle small file upload
**GIVEN** audio file size < 20MB
**WHEN** upload is initiated
**THEN** system MAY use simple upload instead of resumable
**AND** MUST complete in single request

#### Scenario: Validate audio format before upload
**GIVEN** audio file to upload
**WHEN** upload is attempted
**THEN** system MUST check MIME type
**AND** MUST accept: audio/mp3, audio/wav, audio/aac, audio/mpeg
**AND** MUST reject unsupported formats with clear error message

---

### Requirement: GEMINI-AUDIO-002 - Audio Transcription Request
GeminiClient MUST support requesting transcription of uploaded audio files.

#### Scenario: Submit audio transcription request
**GIVEN** audio file uploaded to Files API
**AND** file URI is available
**WHEN** `submitAudioPrompt(File, String)` is called
**THEN** system MUST call `generateContent` endpoint
**AND** MUST include file reference in request
**AND** prompt MUST be "Generate a transcript of the speech"
**AND** MUST use model gemini-2.5-flash or compatible

#### Scenario: Build multimodal request JSON
**GIVEN** transcription request
**WHEN** JSON payload is constructed
**THEN** it MUST include `contents` array
**AND** contents MUST have user role
**AND** parts MUST include text prompt
**AND** parts MUST include file_data with mime_type and file_uri
**AND** JSON structure MUST match:
```json
{
  "contents": [{
    "parts": [
      {"text": "Generate a transcript of the speech"},
      {"file_data": {"mime_type": "audio/mp3", "file_uri": "..."}}
    ]
  }]
}
```

---

### Requirement: GEMINI-AUDIO-003 - Response Parsing
The system MUST correctly parse transcription responses from Gemini API.

#### Scenario: Extract transcription text
**GIVEN** successful API response
**WHEN** response is parsed
**THEN** system MUST extract candidates array
**AND** MUST find first candidate with role "model"
**AND** MUST extract text from parts[0].text
**AND** MUST return transcription as String

#### Scenario: Handle empty transcription
**GIVEN** API response with no text content
**WHEN** response is parsed
**THEN** system MUST return empty string
**OR** MUST throw exception indicating no transcription
**AND** error message MUST be user-friendly

#### Scenario: Handle response errors
**GIVEN** API response contains error object
**WHEN** response is parsed
**THEN** system MUST extract error message
**AND** MUST throw exception with descriptive message
**AND** MUST log full error response for debugging

---

### Requirement: GEMINI-AUDIO-004 - Audio Upload Publisher
The system MUST implement reactive Publisher for audio transcription flow.

#### Scenario: Multi-step audio processing
**GIVEN** AudioUploadPublisher is created
**WHEN** subscriber subscribes
**THEN** publisher MUST first upload audio file
**AND** MUST then request transcription
**AND** MUST emit transcription text on success
**AND** MUST emit error on failure at any step

#### Scenario: Handle upload progress
**GIVEN** large audio file upload in progress
**WHEN** upload progresses
**THEN** publisher MAY emit progress updates
**AND** progress MUST be percentage (0-100)

#### Scenario: Support cancellation
**GIVEN** audio transcription in progress
**WHEN** subscription is cancelled
**THEN** upload MUST be aborted if in progress
**AND** API request MUST be cancelled if pending
**AND** resources MUST be cleaned up

---

### Requirement: GEMINI-AUDIO-005 - File Management Integration
The system MUST properly manage audio file lifecycle.

#### Scenario: Read audio file for upload
**GIVEN** audio File object
**WHEN** upload is initiated
**THEN** system MUST verify file exists
**AND** MUST verify file is readable
**AND** MUST read file as byte stream
**AND** MUST determine MIME type from file extension

#### Scenario: Calculate file size
**GIVEN** audio file to upload
**WHEN** preparing upload
**THEN** system MUST get file size in bytes
**AND** MUST set Content-Length header correctly
**AND** MUST reject files > 2GB

---

### Requirement: GEMINI-AUDIO-006 - Error Handling
The system MUST handle all audio-specific errors gracefully.

#### Scenario: Handle upload failure
**GIVEN** audio upload in progress
**WHEN** network error occurs
**THEN** system MUST catch IOException
**AND** MUST emit error to subscriber
**AND** MUST include cause in exception
**AND** MUST clean up partial upload if possible

#### Scenario: Handle API quota exceeded
**GIVEN** transcription request sent
**WHEN** API returns quota exceeded error
**THEN** system MUST parse error response
**AND** error message MUST mention quota limitation
**AND** MUST suggest user check API key quota

#### Scenario: Handle unsupported audio format
**GIVEN** audio file with unsupported format
**WHEN** upload is attempted
**THEN** system MUST reject before upload
**AND** error MUST list supported formats
**AND** error MUST be thrown immediately

#### Scenario: Handle timeout
**GIVEN** audio upload or transcription in progress
**WHEN** operation exceeds timeout (60 seconds)
**THEN** system MUST cancel operation
**AND** MUST throw TimeoutException
**AND** error MUST indicate which step timed out

---

### Requirement: GEMINI-AUDIO-007 - API Configuration
Audio transcription MUST use appropriate Gemini model and parameters.

#### Scenario: Use audio-capable model
**GIVEN** audio transcription request
**WHEN** model is selected
**THEN** MUST use gemini-2.5-flash or later
**AND** MUST NOT use models without audio support (gemini-1.0-pro)

#### Scenario: Configure transcription parameters
**GIVEN** transcription request
**WHEN** request is built
**THEN** temperature SHOULD be 0.0 for accuracy
**AND** maxOutputTokens SHOULD be 2048 minimum
**AND** safetySettings SHOULD allow all content for transcription
