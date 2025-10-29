# Voice-to-Text Feature Implementation - OpenSpec Proposals Summary

## Overview
This document summarizes 7 OpenSpec proposals for adding voice-to-text functionality to KeyboardGPT using Google Gemini API's audio understanding capabilities.

## Feature Goal
Replace traditional Google TTS voice input with Gemini API-powered voice-to-text transcription, providing better accuracy and seamless integration with KeyboardGPT's AI capabilities.

## Proposals

### 1. add-audio-permissions (Foundational)
**Estimated Effort**: 2-3 hours
**Dependencies**: None

Adds Android RECORD_AUDIO permission and implements runtime permission handling.

**Key Components**:
- AndroidManifest.xml permission declaration
- PermissionManager utility class
- Permission request flow with user-friendly explanations
- "Don't ask again" handling with settings redirect

**Deliverables**:
- Runtime permission checking
- Permission request dialogs
- Graceful permission denial handling

---

### 2. audio-recorder-service (Core Audio)
**Estimated Effort**: 6-8 hours
**Dependencies**: add-audio-permissions

Implements audio recording service using Android MediaRecorder API.

**Key Components**:
- AudioRecorder class with MediaRecorder integration
- AudioConfig for recording parameters (16kHz, AAC, MONO)
- AudioRecorderListener for callbacks
- Audio level monitoring for UI feedback
- Recording lifecycle management (start, pause, resume, stop)

**Deliverables**:
- MP3/AAC/WAV audio recording
- Real-time audio level monitoring
- Error handling and recovery
- Automatic cleanup of recordings

---

### 3. gemini-audio-support (API Integration)
**Estimated Effort**: 8-10 hours
**Dependencies**: audio-recorder-service

Extends GeminiClient to support audio file uploads and transcription.

**Key Components**:
- GeminiFileUploader for Files API integration
- AudioUploadPublisher for reactive audio processing
- Extended GeminiClient with submitAudioPrompt() method
- Audio format validation and error handling

**Deliverables**:
- Audio file upload to Gemini Files API
- Transcription request and response parsing
- Multi-step workflow (upload → transcribe)
- Support for MP3, WAV, AAC formats

---

### 4. voice-input-command (Orchestration)
**Estimated Effort**: 6-8 hours
**Dependencies**: audio-recorder-service, gemini-audio-support

Implements VoiceInputCommand to orchestrate the complete workflow.

**Key Components**:
- VoiceInputCommand following existing command pattern
- VoiceCommandState for state management
- Integration with AudioRecorder and GeminiClient
- Progress callbacks for UI updates
- Cancellation and timeout handling

**Deliverables**:
- Complete voice-to-text workflow
- State management (IDLE → RECORDING → PROCESSING → COMPLETED)
- Error handling at each step
- Text insertion into keyboard

---

### 5. voice-button-hook (Keyboard Integration)
**Estimated Effort**: 10-12 hours
**Dependencies**: voice-input-command

Hooks keyboard's voice button to trigger VoiceInputCommand instead of system voice input.

**Key Components**:
- VoiceButtonHook for Intent interception
- KeyboardVoiceDetector for button detection
- Intent hooking (ACTION_RECOGNIZE_SPEECH)
- Settings toggle for enable/disable

**Deliverables**:
- Voice button interception on major keyboards
- System voice input prevention
- Keyboard compatibility (Gboard, SwiftKey, etc.)
- Fallback to system voice when disabled

---

### 6. voice-ui-feedback (User Experience)
**Estimated Effort**: 8-10 hours
**Dependencies**: voice-input-command

Implements visual and haptic feedback for voice input process.

**Key Components**:
- VoiceInputDialog with state-based views
- Recording view with audio level visualization
- Processing view with spinner
- Success/error views
- Haptic feedback for state changes

**Deliverables**:
- Real-time audio level visualization
- Clear state indicators (recording, processing, success, error)
- User controls (cancel, retry)
- Accessibility support (TalkBack, contrast, touch targets)

---

### 7. audio-file-management (Storage Management)
**Estimated Effort**: 5-6 hours
**Dependencies**: audio-recorder-service

Manages audio file lifecycle to prevent storage bloat.

**Key Components**:
- AudioFileManager for cache management
- AudioCacheConfig for cleanup policies
- Automatic cleanup by age/count/size
- Storage space checking
- Manual cache clearing

**Deliverables**:
- Automatic deletion of old recordings (1 hour)
- Cache size limit (100 MB)
- File count limit (10 files)
- Storage space validation before recording
- Settings UI for manual cache clear

---

## Implementation Sequence

### Phase 1: Foundation (Proposals 1-2)
1. add-audio-permissions
2. audio-recorder-service

**Goal**: Basic audio recording capability
**Time**: 8-11 hours

### Phase 2: API Integration (Proposals 3-4)
3. gemini-audio-support
4. voice-input-command

**Goal**: Complete voice-to-text workflow
**Time**: 14-18 hours

### Phase 3: User Experience (Proposals 5-7)
5. voice-button-hook
6. voice-ui-feedback
7. audio-file-management

**Goal**: Polished, production-ready feature
**Time**: 23-28 hours

## Total Estimated Effort
**45-57 hours** (approximately 1-2 weeks of full-time development)

## Key Risks and Mitigations

| Risk | Mitigation |
|------|-----------|
| Keyboard compatibility varies | Test on multiple keyboards, provide fallback |
| Gemini API quota limits | Clear error messages, suggest API key check |
| Audio recording failures | Comprehensive error handling, fallback to system |
| Storage bloat | Aggressive automatic cleanup, user controls |
| Permission denial | Clear explanations, settings redirect |

## Success Criteria

1. **Functionality**: Voice button triggers Gemini transcription
2. **Accuracy**: Transcription quality superior to system TTS
3. **Performance**: Total latency < 15 seconds (5s recording + 10s processing)
4. **Reliability**: Handles errors gracefully without crashes
5. **Usability**: Clear UI feedback at each stage
6. **Compatibility**: Works on 3+ major keyboard apps
7. **Storage**: No storage bloat over time

## Testing Strategy

1. **Unit Tests**: Individual components (AudioRecorder, GeminiClient)
2. **Integration Tests**: Complete workflow end-to-end
3. **Compatibility Tests**: Multiple keyboards and Android versions
4. **Error Tests**: Permission denial, network failures, API errors
5. **Performance Tests**: Latency, memory usage, battery impact
6. **User Tests**: Real-world usage scenarios

## Documentation Requirements

1. README update with voice input feature
2. Setup guide (API key configuration)
3. Troubleshooting guide (permissions, keyboards)
4. API documentation for new classes
5. Architecture diagrams
6. Demo videos

---

## Getting Started

To begin implementation:

1. Review all 7 proposals in `openspec/changes/`
2. Start with Phase 1 (permissions + audio recording)
3. Test each phase thoroughly before proceeding
4. Use the task lists in each proposal's `tasks.md`
5. Follow specs in each proposal's `specs/*/spec.md`

Each proposal is self-contained with:
- `proposal.md` - Overview and design decisions
- `tasks.md` - Step-by-step implementation tasks
- `specs/*/spec.md` - Detailed requirements and scenarios
