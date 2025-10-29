# Proposal: Voice Button Hook

## Change ID
`voice-button-hook`

## Summary
Hook into the keyboard's voice input button to trigger VoiceInputCommand instead of the default system voice recognition.

## Motivation
Most Android keyboards have a microphone/voice button that triggers system voice input. We want to intercept this button press and use our Gemini-powered voice-to-text instead, providing better accuracy and consistency with the rest of KeyboardGPT.

## Goals
- Detect voice button presses in keyboard apps
- Intercept button action before system handles it
- Trigger VoiceInputCommand on button press
- Support multiple keyboard apps (Gboard, SwiftKey, etc.)
- Provide fallback to system voice input if feature disabled

## Non-Goals
- Replacing keyboard UI (button stays visual same)
- Supporting non-standard keyboard buttons
- Voice button customization (long press, etc.)
- Hardware button interception

## Scope
- **Affected Components**: MainHook, HookManager
- **New Files**:
  - VoiceButtonHook.java
  - KeyboardVoiceDetector.java
- **Modified Files**:
  - MainHook.java
  - HookManager.java

## Dependencies
- `voice-input-command` - Command to execute on button press
- Xposed framework hooking capabilities
- Keyboard app compatibility

## Risks
- Button detection may vary across keyboard apps
- Some keyboards may not expose voice button hooks
- System may intercept button before our hook
- Breaking changes in keyboard app updates

## Alternatives Considered
- Pattern-based trigger (e.g., `^voice^`) - rejected due to poor UX
- Long-press text input - rejected due to discoverability issues
- Separate KeyboardGPT button - rejected due to keyboard UI modification complexity
- Settings toggle for voice input - keeping as manual trigger option
