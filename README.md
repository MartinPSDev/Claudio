# CLAUDIO ANDROID APP

> **Educational purposes only.** This repository is a reverse-engineering and learning exercise. It is not affiliated with, endorsed by, or connected to Anthropic in any way.

---

## About

This project is a Kotlin reconstruction of the Claude Android application, built by analyzing the decompiled APK using Smali bytecode analysis. The goal is to understand the architecture, networking patterns, and component structure of a production-grade Android app.

---

## ⚠️ Important Disclaimers

### Educational Use Only

This repository exists **solely for educational purposes** — to study Android architecture, Jetpack Compose patterns, service design, and networking implementation. It is not intended for distribution, commercial use, or as a functional replacement for the original application.

### Code Accuracy

The code in this repository **may differ from, be incomplete, or contain errors** compared to the original Anthropic application. The reconstruction is based on Smali decompilation, which involves interpretation of obfuscated bytecode. Some logic may be inaccurate, missing, or approximated.

### Build & Runtime Errors

If you attempt to compile and run this project on a physical device or emulator, **it will most likely fail** for the following reasons:

- **Missing signing keys:** The original Claude app is signed with Anthropic's private certificate. This project uses a debug keystore that does not match the production signature.
- **Missing API keys and secrets:** Firebase, Datadog, Sentry, Segment, and other third-party services require credentials (`google-services.json`, API tokens, etc.) that are not included in this repository.
- **Play Protect restrictions:** Android's Play Protect system may block or warn about installing an APK that uses the `com.anthropic.claude` package name without a matching signature.
- **Missing `google-services.json`:** The Firebase integration will fail at build time or runtime without this configuration file.
- **Server-side authentication:** The app communicates with Anthropic's backend using session tokens obtained through a proprietary login flow that cannot be replicated without access to the production infrastructure.

---

## Project Structure

```
claudio_kotlin/
├── app/
│   └── src/main/
│       ├── java/com/anthropic/claude/
│       │   ├── api/
│       │   │   ├── account/
│       │   │   │   ├── AccountProfile.kt
│       │   │   │   └── SubscriptionLevel.kt
│       │   │   ├── chat/
│       │   │   │   ├── messages/
│       │   │   │   │   ├── CompactionStatus.kt
│       │   │   │   │   ├── CompletionMessage.kt
│       │   │   │   │   ├── MessageDelta.kt
│       │   │   │   │   ├── MessageFlag.kt
│       │   │   │   │   └── ThinkingSummary.kt
│       │   │   │   ├── ChatFeedback.kt
│       │   │   │   ├── ChatFeedbackType.kt
│       │   │   │   ├── ChatMessage.kt
│       │   │   │   ├── CreateChatRequest.kt
│       │   │   │   ├── MessageAttachment.kt
│       │   │   │   └── MessageSender.kt
│       │   │   └── login/
│       │   │       └── CodeConfiguration.kt
│       │   ├── application/
│       │   │   └── ClaudeApplication.kt
│       │   ├── bell/
│       │   │   ├── assist/
│       │   │   │   ├── ClaudeRecognitionService.kt
│       │   │   │   ├── ClaudeVoiceInteractionService.kt
│       │   │   │   ├── ClaudeVoiceInteractionSessionService.kt
│       │   │   │   └── ClaudeVoiceSession.kt
│       │   │   ├── tts/
│       │   │   │   └── TTSPlaybackService.kt
│       │   │   └── BellModeService.kt
│       │   ├── chat/
│       │   │   └── MessageSseService.kt
│       │   ├── code/remote/notification/
│       │   │   ├── CCRPermissionActionReceiver.kt
│       │   │   ├── CCRPermissionActionWorker.kt
│       │   │   ├── SessionReplyActionReceiver.kt
│       │   │   └── SessionReplyActionWorker.kt
│       │   ├── conway/
│       │   │   ├── AppForegroundDetector.kt
│       │   │   ├── ConwayAppScreen.kt
│       │   │   ├── ConwayScopeHolder.kt
│       │   │   └── ConwayWakeWorker.kt
│       │   ├── deeplink/
│       │   │   └── DeepLinkActivity.kt
│       │   ├── firebase/fcm/
│       │   │   └── AnthropicFirebaseMessagingService.kt
│       │   ├── login/
│       │   │   └── MagicLinkSentConfig.kt
│       │   ├── mainactivity/
│       │   │   ├── AssistantOverlayActivity.kt
│       │   │   ├── IntentRouter.kt
│       │   │   └── MainActivity.kt
│       │   ├── model/
│       │   │   └── IncomingPayload.kt
│       │   ├── models/organization/
│       │   │   ├── configtypes/
│       │   │   │   ├── AvailableModelsConfig.kt
│       │   │   │   └── ModelFallbacksConfig.kt
│       │   │   └── DefaultModelConfig.kt
│       │   ├── networking/
│       │   │   └── AnthropicApiClient.kt
│       │   ├── policy/
│       │   │   └── PermissionsRationaleActivity.kt
│       │   ├── types/strings/
│       │   │   ├── DomainTypes.kt
│       │   │   └── ModelId.kt
│       │   ├── ui/
│       │   │   └── MainScreen.kt
│       │   ├── wear/
│       │   │   ├── AuthSyncCredentials.kt
│       │   │   ├── PhoneWearableListenerService.kt
│       │   │   └── SerializableCookieSlim.kt
│       │   └── widget/
│       │       └── ClaudeAppWidgetReceiver.kt
│       ├── AndroidManifest.xml
│       └── res/
├── gradle/
│   └── libs.versions.toml     # Version catalog
└── build.gradle.kts
```

---

## Tech Stack (as found in the original APK)

| Layer | Technology |
|---|---|
| Language | Kotlin 2.1.20 |
| UI | Jetpack Compose 1.10.4 + Material3 1.5.0-alpha12 |
| Architecture | MVVM + StateFlow |
| Networking | OkHttp 5.5.0 + Retrofit 2.11.0 + Moshi 1.15.2 |
| Async | Coroutines 1.10.2 |
| Serialization | kotlinx.serialization 1.8.1 |
| Storage | Room 2.8.4 + DataStore 1.3.0-alpha06 |
| Protobuf | Wire 5.5.0 |
| Analytics | Firebase 33.13.0, Datadog 2.18.1, Sentry 8.8.0, Segment 1.19.1 |
| Min SDK | 32 (Android 12L) |
| Target SDK | 36 (Android 16) |

---

## License

This project is shared for **educational and research purposes only**. All trademarks, service marks, and brand names belong to their respective owners. Claude™ is a trademark of Anthropic, PBC.
