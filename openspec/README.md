# KeyboardGPT OpenSpec Documentation

This directory contains OpenSpec documentation for KeyboardGPT feature development.

## Structure

- `project.md` - Project overview and current capabilities
- `changes/` - Feature proposals and change specifications
- `specs/` - Consolidated requirement specifications (generated)

## Current Proposals

### Voice-to-Text Feature (7 Proposals)

A comprehensive feature to replace system voice input with Gemini API-powered voice-to-text transcription.

See `VOICE_FEATURE_OVERVIEW.md` for complete details.

**Proposals**:
1. `add-audio-permissions` - Audio recording permissions
2. `audio-recorder-service` - Audio capture service
3. `gemini-audio-support` - Gemini API audio integration
4. `voice-input-command` - Command orchestration
5. `voice-button-hook` - Keyboard button interception
6. `voice-ui-feedback` - User interface feedback
7. `audio-file-management` - Storage and cleanup

## Reading Proposals

Each proposal in `changes/<id>/` contains:
- `proposal.md` - Overview, motivation, scope, dependencies
- `tasks.md` - Implementation checklist
- `specs/<capability>/spec.md` - Detailed requirements with scenarios

## Implementation Workflow

1. Read proposal overview
2. Review dependencies
3. Follow task list sequentially
4. Implement according to spec requirements
5. Test against scenarios in specs
6. Mark tasks complete

## Validation

Requirements follow the format:
```
### Requirement: ID - Title
Description

#### Scenario: Scenario name
GIVEN preconditions
WHEN action
THEN expected outcome
AND additional assertions
```

Each requirement MUST have at least one scenario.
