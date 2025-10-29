# Voice-to-Text Implementation Roadmap

## Quick Reference

| Proposal | Effort | Dependencies | Priority |
|----------|--------|--------------|----------|
| 1. add-audio-permissions | 2-3h | None | P0 - Start Here |
| 2. audio-recorder-service | 6-8h | #1 | P0 - Core |
| 3. gemini-audio-support | 8-10h | #2 | P0 - Core |
| 4. voice-input-command | 6-8h | #2, #3 | P0 - Core |
| 5. voice-button-hook | 10-12h | #4 | P1 - Enhancement |
| 6. voice-ui-feedback | 8-10h | #4 | P1 - UX |
| 7. audio-file-management | 5-6h | #2 | P2 - Polish |

**Total**: 45-57 hours (~1-2 weeks)

## Phase 1: MVP (P0 - Core Functionality)
**Goal**: Working voice-to-text via manual trigger
**Time**: 22-29 hours

### Week 1, Days 1-2
✅ **Proposal 1: add-audio-permissions** (2-3h)
- Add RECORD_AUDIO permission to manifest
- Implement PermissionManager
- Test permission flows

✅ **Proposal 2: audio-recorder-service** (6-8h)
- Implement AudioRecorder with MediaRecorder
- Add audio level monitoring
- Test recording on multiple devices

**Checkpoint**: Can record audio to file ✓

### Week 1, Days 3-4
✅ **Proposal 3: gemini-audio-support** (8-10h)
- Extend GeminiClient for audio
- Implement Files API upload
- Test transcription accuracy

**Checkpoint**: Can transcribe audio files ✓

### Week 1, Day 5
✅ **Proposal 4: voice-input-command** (6-8h)
- Implement VoiceInputCommand
- Orchestrate recording → upload → transcribe
- Test complete workflow

**Checkpoint**: Voice-to-text works end-to-end ✓

**MVP COMPLETE** - Feature is functional, can be tested

---

## Phase 2: Polish (P1 - User Experience)
**Goal**: Production-ready with great UX
**Time**: 18-22 hours

### Week 2, Days 1-3
✅ **Proposal 5: voice-button-hook** (10-12h)
- Hook keyboard voice buttons
- Test on Gboard, SwiftKey, others
- Add settings toggle

**Checkpoint**: Voice button triggers feature ✓

### Week 2, Days 4-5
✅ **Proposal 6: voice-ui-feedback** (8-10h)
- Implement VoiceInputDialog
- Add recording/processing/success/error views
- Add haptic feedback
- Test accessibility

**Checkpoint**: Clear visual feedback at all stages ✓

---

## Phase 3: Maintenance (P2 - Long-term)
**Goal**: Prevent issues over time
**Time**: 5-6 hours

### Week 2, End
✅ **Proposal 7: audio-file-management** (5-6h)
- Implement automatic cleanup
- Add cache management
- Add settings for manual clear

**Checkpoint**: No storage bloat ✓

---

## Testing Milestones

### After Phase 1 (MVP)
- [ ] Can record voice successfully
- [ ] Can transcribe to text
- [ ] Text inserts into keyboard
- [ ] Errors display appropriately
- [ ] Works without voice button (manual trigger)

### After Phase 2 (Polish)
- [ ] Voice button triggers feature
- [ ] Clear UI at each stage
- [ ] User can cancel at any point
- [ ] Works on 3+ keyboard apps
- [ ] Accessible to screen readers

### After Phase 3 (Complete)
- [ ] No storage buildup over time
- [ ] Cache auto-cleans correctly
- [ ] Manual clear works
- [ ] Storage warnings functional

---

## Risk Management

### High-Risk Areas
1. **Keyboard Compatibility** (Proposal 5)
   - Test early on target keyboards
   - Have fallback to manual trigger
   - Document incompatibilities

2. **API Quota** (Proposal 3)
   - Add clear error messages
   - Consider rate limiting
   - Monitor usage in testing

3. **Audio Quality** (Proposal 2)
   - Test on various devices
   - Adjust recording parameters if needed
   - Handle noisy environments

### Mitigation Strategy
- Test each proposal thoroughly before moving to next
- Keep fallbacks for critical failures
- Log extensively for debugging
- Start with manual trigger (skip #5 initially if needed)

---

## Alternative Paths

### Path A: Fastest MVP (Skip #5, #7 initially)
1. add-audio-permissions (2-3h)
2. audio-recorder-service (6-8h)
3. gemini-audio-support (8-10h)
4. voice-input-command (6-8h)
6. voice-ui-feedback (8-10h)

**Total**: 30-39h - Working feature with good UX, manual trigger

### Path B: Essential + Button Hook
1-5 only (skip UI polish and file management initially)

**Total**: 32-41h - Button-triggered but basic feedback

### Path C: Phased Release
- v1.0: Proposals 1-4 (MVP)
- v1.1: Add #6 (UI feedback)
- v1.2: Add #5 (voice button)
- v1.3: Add #7 (file management)

---

## Daily Checklist Template

### Daily Start
- [ ] Review proposal's tasks.md
- [ ] Check dependencies are complete
- [ ] Read relevant spec scenarios

### During Implementation
- [ ] Follow task list sequentially
- [ ] Test each component as built
- [ ] Log issues for later fixing
- [ ] Commit working code frequently

### Daily End
- [ ] Mark completed tasks in tasks.md
- [ ] Test what was built today
- [ ] Document any blockers
- [ ] Plan tomorrow's tasks

---

## Success Metrics

### Technical
- ✓ Voice recording works reliably
- ✓ Transcription accuracy > 90%
- ✓ End-to-end latency < 15s
- ✓ No crashes during normal use
- ✓ Handles all error scenarios

### User Experience
- ✓ Clear feedback at each step
- ✓ Easy to cancel/retry
- ✓ Works on first try (90%+ success rate)
- ✓ Better than system voice input
- ✓ Minimal storage footprint

### Business
- ✓ Feature complete within 2 weeks
- ✓ No major bugs in testing
- ✓ Positive user feedback
- ✓ Adoption > 30% of users
- ✓ Minimal support issues

---

## Next Steps

1. **Read** all 7 proposals in `openspec/changes/`
2. **Start** with Proposal 1: add-audio-permissions
3. **Follow** the task lists strictly
4. **Test** continuously
5. **Document** issues and learnings
6. **Celebrate** after MVP! 🎉

Good luck with implementation! 🚀
