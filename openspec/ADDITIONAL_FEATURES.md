# Additional Voice Feature Proposals

This document describes 2 additional proposals that enhance the voice-to-text feature with customization and reliability improvements.

## Proposal 8: voice-custom-prompts

**Estimated Effort**: 4-5 hours
**Dependencies**: voice-input-command, gemini-audio-support
**Priority**: P1 - Enhancement

### Overview
Allows users to customize the transcription prompt sent to Gemini API, enabling different output formats and styles.

### Key Features
- **Preset Prompts**: 6 built-in templates
  - RAW: Basic transcription
  - CORRECTED: Grammar correction
  - PUNCTUATED: Proper punctuation
  - FORMAL: Professional tone
  - SUMMARY: Brief summary
  - CUSTOM: User-defined

- **Settings UI**: Easy prompt configuration
- **Validation**: Ensures valid prompts (5-500 chars)
- **Persistence**: Saves preferences across sessions
- **Preview**: Shows prompt before recording

### Use Cases
- Users wanting grammar correction: Select CORRECTED preset
- Formal communication: Select FORMAL preset
- Note-taking: Select SUMMARY preset
- Special formatting: Create custom prompt like "Transcribe as bullet points"

### Benefits
- Flexibility for different use cases
- Better transcription quality for specific needs
- User control over AI behavior
- No need to edit text after transcription

---

## Proposal 9: voice-retry-mechanism

**Estimated Effort**: 5-6 hours
**Dependencies**: voice-input-command, gemini-audio-support, audio-file-management
**Priority**: P0 - Core (Reliability)

### Overview
Implements intelligent retry mechanism for failed API requests with automatic and manual retry capabilities.

### Key Features

#### Automatic Retry
- **3 retry attempts** for transient failures
- **Exponential backoff**: 1s, 2s, 4s delays
- **Smart classification**: Retryable vs non-retryable errors
- **UI feedback**: Shows "Retrying... (2 of 3)"

#### Manual Retry
- **No re-recording needed**: Uses cached audio
- **Retry button** on error dialog
- **Reset counter**: New 3 attempts on manual retry
- **Audio preservation**: Keeps file until success/cancel

#### Error Classification
**Retryable** (auto-retry):
- Network timeouts
- 5xx server errors
- 429 rate limits
- Connection issues

**Non-retryable** (fail immediately):
- Invalid API key (401)
- Bad request (400)
- Permission denied
- Invalid audio format

### Benefits
- **Higher success rate**: Automatic retry handles transient failures
- **Better UX**: No need to re-record on network glitches
- **Time saving**: Manual retry uses cached audio
- **Resilience**: Exponential backoff prevents API flooding
- **Clarity**: Clear distinction between retryable and permanent failures

### Example Flow

```
1. User records voice
2. Upload fails (network timeout)
   → Auto-retry 1 of 3 (wait 1s)
3. Upload fails again
   → Auto-retry 2 of 3 (wait 2s)
4. Upload succeeds
5. Transcription request fails (503)
   → Auto-retry 1 of 3 (wait 1s)
6. Transcription succeeds
7. Text inserted, audio deleted ✓

Alternative flow:
1. User records voice
2. Upload fails 3 times (network down)
3. Error dialog shows "Failed after 3 attempts"
4. User fixes network, taps "Retry"
5. Upload succeeds (using cached audio, no re-recording)
6. Transcription succeeds
7. Text inserted ✓
```

---

## Integration with Existing Proposals

### Updated Dependency Graph

```
Phase 1: Foundation
├── 1. add-audio-permissions
├── 2. audio-recorder-service
├── 3. gemini-audio-support
└── 4. voice-input-command

Phase 2: Enhancements
├── 5. voice-button-hook
├── 6. voice-ui-feedback
├── 8. voice-custom-prompts ← NEW
└── 9. voice-retry-mechanism ← NEW

Phase 3: Maintenance
└── 7. audio-file-management
```

### Modified Implementation Timeline

| Proposal | Effort | Phase | Priority |
|----------|--------|-------|----------|
| 1. add-audio-permissions | 2-3h | 1 | P0 |
| 2. audio-recorder-service | 6-8h | 1 | P0 |
| 3. gemini-audio-support | 8-10h | 1 | P0 |
| 4. voice-input-command | 6-8h | 1 | P0 |
| **9. voice-retry-mechanism** | **5-6h** | **1+** | **P0** |
| 5. voice-button-hook | 10-12h | 2 | P1 |
| 6. voice-ui-feedback | 8-10h | 2 | P1 |
| **8. voice-custom-prompts** | **4-5h** | **2** | **P1** |
| 7. audio-file-management | 5-6h | 3 | P2 |

**New Total**: 54-68 hours (~1.5-2 weeks)

---

## Recommended Implementation Order

### Updated MVP Path
1. add-audio-permissions (2-3h)
2. audio-recorder-service (6-8h)
3. gemini-audio-support (8-10h)
4. voice-input-command (6-8h)
5. **voice-retry-mechanism (5-6h)** ← Add to MVP for reliability

**MVP with Retry**: 27-35 hours

### Enhanced UX Path
6. voice-ui-feedback (8-10h)
7. voice-button-hook (10-12h)
8. **voice-custom-prompts (4-5h)** ← Add for customization

### Polish Path
9. audio-file-management (5-6h)

---

## Key Benefits of These Additions

### Proposal 8 (Custom Prompts)
✅ Flexibility for different use cases
✅ Better transcription quality
✅ User control over AI output
✅ No post-editing needed
✅ Simple UI implementation

### Proposal 9 (Retry Mechanism)
✅ **Critical for production**: Handles network issues
✅ No re-recording on transient failures
✅ Higher success rate (30-50% improvement expected)
✅ Better user experience
✅ Professional error handling
✅ **Should be in MVP** due to reliability impact

---

## Testing Considerations

### Custom Prompts Testing
- [ ] Test all preset prompts
- [ ] Test custom prompt validation
- [ ] Compare transcription quality across prompts
- [ ] Test prompt persistence
- [ ] Test prompt UI usability

### Retry Mechanism Testing
- [ ] Test network timeout retries
- [ ] Test successful retry after 1-2 attempts
- [ ] Test failure after 3 attempts
- [ ] Test manual retry without re-recording
- [ ] Test exponential backoff delays
- [ ] Test cancellation during retry
- [ ] Test audio file preservation
- [ ] Test error classification
- [ ] Simulate various network conditions

---

## Updated Success Metrics

### Technical (Added)
- ✓ Auto-retry success rate > 70% for transient failures
- ✓ Manual retry works without re-recording
- ✓ Custom prompts produce expected output format
- ✓ Retry delays follow exponential backoff

### User Experience (Added)
- ✓ Users can customize transcription style
- ✓ Network glitches don't require re-recording
- ✓ Clear feedback during retry process
- ✓ One-click retry on errors

---

## Summary

These 2 additional proposals significantly enhance the voice-to-text feature:

1. **voice-custom-prompts**: Gives users control over transcription style and format
2. **voice-retry-mechanism**: Makes the feature production-ready with proper error handling

**Recommendation**:
- Include Proposal 9 (retry-mechanism) in MVP for reliability
- Add Proposal 8 (custom-prompts) in Phase 2 for enhanced UX

Both proposals are relatively quick to implement (9-11 hours combined) and provide significant value.
