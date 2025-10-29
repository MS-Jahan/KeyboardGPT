# KeyboardGPT Project

## Overview
KeyboardGPT is an LSPosed Module that integrates Generative AI (ChatGPT, Gemini, Claude, etc.) into Android keyboards. The module intercepts text patterns and sends prompts to AI APIs, returning responses directly into the text field.

## Current Capabilities
- Text-based AI prompts via patterns (e.g., `$prompt$`)
- Multiple AI provider support (OpenAI, Gemini, Groq, Claude, OpenRouter, Mistral)
- Custom commands and patterns
- Text formatting (bold, italic, strikethrough, underline)
- Web search integration
- Xposed/LSPosed framework integration for keyboard hooking

## Technology Stack
- Android SDK (minSdk 24, targetSdk 36)
- Java 17
- Xposed Framework
- HTTP-based API integration
- SharedPreferences for configuration storage

## Architecture
- **MainHook**: Xposed entry point, hooks InputMethodService
- **KeyboardGPTBrain**: Core logic coordinator
- **LLM Clients**: API integration for various AI providers
- **Command System**: Pattern-based command execution
- **UI System**: Dialog-based configuration interface

## Current Limitations
- Text-only input (no voice/audio support)
- Requires manual pattern typing
- No real-time audio processing
