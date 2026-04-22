package com.anthropic.claude.stt.repo

class SpeechToTextLanguageNotFoundException(
    language: String,
) : Exception("Speech-to-text language not supported: $language")
