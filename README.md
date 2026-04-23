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

- **Missing signing keys:** The original Claude app is signed with Anthropic's private certificate.
- **Missing API keys and secrets:** Firebase, Datadog, Sentry, Segment, and other third-party services require credentials (`google-services.json`, API tokens, etc.) that are not included in this repository.
- **Play Protect restrictions:** Android's Play Protect system may block installation without a matching signature.
- **Missing `google-services.json`:** Excluded from the repo via `.gitignore`. Firebase will fail without it.
- **Server-side authentication:** The app communicates with Anthropic's backend using session tokens obtained through a proprietary login flow.

---

## Project Structure

```
claudio_kotlin/
app/src/main/java/com/anthropic/claude/
│
├── agentchat/              AgentChatDestination
├── analytics/              AnalyticsProperties, events/, screens/, ads/
│
├── api/
│   ├── account/            AccountProfile, SubscriptionLevel, RavenType, AccountConsentModels
│   ├── artifacts/          ArtifactModels
│   ├── chat/               ChatConversation, ChatMessage, CreateChatRequest, ToolState...
│   │   ├── citation/       CitationModels
│   │   ├── messages/       ContentBlock, StreamEvent, MessageDelta, BellNoteDelta, ThinkingSummary...
│   │   └── tool/           ResearchModels, ResearchStatusModels
│   ├── common/             RateLimit
│   ├── consent/            ConsentModels
│   ├── conway/             ConwayModels
│   ├── errors/             ApiErrors
│   ├── events/             BatchEventLoggingResponse
│   ├── experience/         ExperienceModels
│   ├── export/             ExportModels
│   ├── feedback/           FeedbackModels
│   ├── kyc/                KycModels
│   ├── login/              CodeConfiguration
│   ├── mcp/                McpModels, DirectoryServer, DirectoryServerType
│   ├── memory/             MemorySynthesisResponse
│   ├── mobile/             MobileModels
│   ├── notification/       NotificationModels
│   ├── orbit/              OrbitModels
│   ├── project/            Project, ProjectDoc, ProjectEnums, ProjectType, ProjectActorAccount
│   ├── purchase/           VerifyPurchaseResponse
│   ├── share/              ChatSnapshotModels
│   ├── styles/             StyleModels
│   ├── sync/               SyncAuthModels
│   ├── tasks/              TaskModels, TaskAgentModels
│   ├── trusteddevice/      EnrollTrustedDeviceResponse
│   ├── usage/              UsageModels
│   ├── verification/       PhoneVerificationModels
│   └── wiggle/             WiggleModels
│
├── app/
│   ├── onboarding/v2/      OnboardingPage (7-step sealed)
│   ├── verification/       VerificationScreens
│   └── SettingsScreenParams
│
├── application/            ClaudeApplication
│
├── artifact/
│   ├── details/            ArtifactFullScreenParams
│   ├── dialog/             PublishArtifactParams
│   ├── model/              ArtifactMetadata, ArtifactType (9-subtype sealed)
│   └── sheet/              ArtifactSheetParams
│
├── audio/                  MicrophoneAudioException
│
├── bell/
│   ├── api/                BellApiServerMessage sealed, BellApiClientMessage sealed
│   ├── assist/             ClaudeRecognitionService, ClaudeVoiceInteractionService...
│   ├── tts/                TTSPlaybackService
│   └── BellModeService
│
├── chat/
│   ├── bottomsheet/
│   │   ├── model/          ChatBottomSheetModels
│   │   └── tool/approval/appuse/  MobileAppUseDestination (Approval / CalendarSelection)
│   ├── input/draft/        DraftMessage, QueuedSendRequest
│   ├── parse/              ParsedContentBlock (McpToolInvocation, Thinking), ParsedContentBlockId
│   │   └── sse/            ServerSentEventError, ServerSentEventException
│   ├── queue/              QueuedMessageWorkerArgs, QueuedMessageWorker
│   ├── share/              SharedChatModalBottomSheetDestination (4 destinations)
│   └── MessageSseService
│
├── chatlist/all/
│   ├── bottomsheet/        AllChatsListBottomSheetDestination
│   └── overlays/           AllChatsListOverlay
│
├── code/remote/
│   ├── bottomsheet/        CodeRemoteBottomSheetDestination (ApprovalDiffDetail, ChannelMessage, ViewCode)
│   ├── notification/       CCRPermissionActionReceiver/Worker, SessionReplyActionReceiver/Worker
│   └── CodeRemoteSessionScreenParams
│
├── configs/
│   ├── flags/              UploadConfig, FileUploadConfig, StreamSmoothingConfig,
│   │                       OnboardingConfig, AgentChatWorkerTypesConfig,
│   │                       AgentChatOnboardingConfig, ImageSearchHelpCenterConfig,
│   │                       ToolSearchModeConfig
│   └── GrowthBookExposureSampleRateConfig, PersistedFeatureOverrides
│
├── connector/auth/         McpAuthException (ExchangeFailed, Denied, StartFailed, MissingCallbackParameters)
│
├── conway/
│   ├── protocol/           ClientRegistrationRequest/Response, ConwayExtensionMeta,
│   │                       AgentState, StreamMessage sealed (Connected/AgentsUpdated/ToolCall),
│   │                       RichMessage sealed (Assistant/User/Status), ToolResultMessage,
│   │                       ContainerHealthResponse, ConwaySearchHit, PaginatedMessages,
│   │                       ConwayErrorPayload, ConwayClientInfo, ContentBlock sealed,
│   │                       ModelInfo, UiDemoAppDestination
│   └── AppForegroundDetector, ConwayAppScreen, ConwayScopeHolder, ConwayWakeWorker
│
├── core/telemetry/         SilentException
├── deeplink/               DeepLinkActivity
│
├── firebase/fcm/           AnthropicFirebaseMessagingService
│                           FcmIntentExtras (11 keys), CcrActions (3 broadcast actions),
│                           17 FCM payload key constants, routing helpers
│
├── login/                  MagicLinkSentConfig
│
├── mainactivity/           MainActivity, AssistantOverlayActivity, IntentRouter
│
├── mcpapps/
│   ├── transport/          JsonRpc, HostCapabilities, HostContext, InitializeResult,
│   │                       McpCapabilities, UiResources
│   └── HydratedImageContent, McpAppFetchException, ModelContextTooLargeException,
│       DomainValidationException
│
├── model/                  IncomingPayload
├── models/organization/    AvailableModelsConfig, ModelFallbacksConfig, DefaultModelConfig
├── networking/             AnthropicApiClient
├── policy/                 PermissionsRationaleActivity
│
├── project/
│   ├── create/             CreateTemplateProjectScreenParams, UploadMaterialsScreenParams
│   ├── details/            ProjectDetailsScreenParams, ProjectDetailsDialogDestination,
│   │                       CustomInstructionsDialogRoute
│   ├── knowledge/          ProjectKnowledgeParams
│   └── menu/               ProjectItemMenuDialogDestination (Delete/Rename/Dismissed)
│
├── protos/push/            ConwayWakeRequest (Wire proto)
│
├── sessions/
│   ├── api/                ControlRequestContent (11 fields + PermissionUpdate)
│   └── types/              SessionResource, SessionTypes, SessionContext, SharedSessionData,
│                           BridgeEnvironmentInfo, ControlResponsePayload,
│                           EnvironmentResource, PostTurnSummary
│
├── settings/
│   ├── internal/           InternalSettingsAppScreen (4 destinations),
│   │                       growthbook/GrowthBookModels (GateKind enum, overrides)
│   └── SettingsAppScreen
│
├── stt/repo/               STTApiMessage, SpeechToTextLanguageNotFoundException
│
├── tasks/ui/               TasksBottomSheetDestination, TasksListOverlay
│
├── tool/
│   ├── calendar/           EventInfo
│   ├── model/              Tool sealed (Compass/Analysis/Artifacts/DriveSearch/Unknown),
│   │                       SourceImage sealed, SearchToolInput, TaskStatusInput,
│   │                       Calendar/Location PreviewData, ToolInvocationResult,
│   │                       SearchMcpRegistryInput, TaskListOutput
│   └── ui/chat/            PhoneCallSheetDestination, FormSheetDestination
│
├── types/strings/          DomainTypes (20+ inline value classes: ChatId, ProjectId,
│                           MessageId, SessionId, AccountId...), ModelId
│
├── ui/
│   ├── code/               SessionInputData, DiffLineComment, PendingAskUserQuestionSelections
│   └── MainScreen
│
├── wear/                   AuthSyncCredentials, PhoneWearableListenerService, SerializableCookieSlim
└── widget/                 ClaudeAppWidgetReceiver
```

**Total: 172 Kotlin files across 65+ packages** (commit `634caeb`)

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
