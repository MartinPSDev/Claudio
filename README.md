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
│
├── analytics/              AnalyticsProperties
│   ├── ads/                GooglePlayReferrer
│   ├── events/             AnalyticsEvent, PushEvents
│   └── screens/            AnalyticsScreens
│
├── api/
│   ├── account/            Account, AccountProfile, AccountSettings, AccountConsentModels,
│   │                       AccountDeletableResponse, AccountRequestModels (UpdateAccountRequest,
│   │                       AppStartResponse), Organization, RavenType, SubscriptionLevel,
│   │                       GrowthBookModels (GrowthBookFeature, GrowthBookRule,
│   │                       GrowthBookExperimentResult), Membership
│   ├── artifacts/          ArtifactModels
│   ├── chat/               ChatConversation, ChatConversationSettings,
│   │                       ChatConversationWithProjectReference, ChatCompletionEvent,
│   │                       ChatCompletionRequest, ChatFeedback, ChatFeedbackType,
│   │                       ChatMessage, ChatResponseTypes, ConversationSearch,
│   │                       CreateChatRequest, MessageAssets, MessageAttachment,
│   │                       MessageFileModels (MessageImageFile, MessageDocumentFile,
│   │                       RecordToolApprovalRequest, ChatScreenParams),
│   │                       MessageSender, SensitiveTextField, ToolState,
│   │                       UpdatableChatConversationSettings
│   │   ├── citation/       CitationModels
│   │   ├── messages/       BellNoteDelta, CompactionStatus, CompletionMessage,
│   │   │                   ContentBlock, ContentBlockDelta, FlagBlock,
│   │   │                   McpAuthRequiredEvent, MessageDelta, MessageFlag,
│   │   │                   StreamEvent, TextBlock, ThinkingSummary,
│   │   │                   ThinkingVoiceBlocks (ThinkingBlock, VoiceNoteBlock),
│   │   │                   ToolResultBlock, ToolUseBlock, ToolUseBlockUpdateDelta,
│   │   │                   UnknownContentBlock
│   │   └── tool/           ResearchModels, ResearchStatusModels
│   ├── common/             RateLimit
│   ├── consent/            ConsentModels, ConsentRequestModels (CheckConsentRequest,
│   │                       RevokeConsentRequest), UserConsentRequest
│   ├── conway/             ConwayModels
│   ├── errors/             ApiErrors
│   ├── events/             BatchEventLoggingResponse,
│   │                       GrowthBookEvents (GrowthBookExperimentEventData,
│   │                       GrowthBookFeatureEvaluationEventData)
│   ├── experience/         ExperienceModels, Experience, SpotlightContent,
│   │                       BannerContent, ExperienceTrackRequest
│   ├── export/             ExportModels
│   ├── feedback/           FeedbackModels
│   ├── kyc/                KycModels
│   ├── login/              CodeConfiguration, SendMagicLinkRequest,
│   │                       VerifyLoginRequests (VerifyGoogleMobileRequest,
│   │                       VerifyMagicLinkRequest)
│   ├── mcp/                McpModels, McpServer, McpTool, McpToolAnnotations,
│   │                       DirectoryServer, DirectoryServerType,
│   │                       AttachMcpPromptRequest
│   ├── memory/             MemorySynthesisResponse
│   ├── mobile/             MobileModels
│   ├── model/              ModelOption, ThinkingModeOption, ModelCapabilities
│   ├── notification/       NotificationModels, FeaturePreference,
│   │                       NotificationChannelUpdateParams
│   ├── orbit/              OrbitModels
│   ├── project/            Project, ProjectActorAccount, ProjectDoc, ProjectEnums,
│   │                       ProjectParams (ProjectCreateParams, ProjectUpdateParams),
│   │                       ProjectType
│   ├── purchase/           VerifyPurchaseResponse
│   ├── share/              ChatSnapshotModels
│   ├── styles/             StyleModels, CustomStyle, DefaultStyle
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
│   ├── assist/             ClaudeRecognitionService, ClaudeVoiceInteractionService,
│   │                       ClaudeVoiceInteractionSessionService, ClaudeVoiceSession
│   ├── tts/                TTSPlaybackService
│   └── BellModeService, VoiceSessionSummary
│
├── chat/
│   ├── bottomsheet/        ChatScreenBottomSheetModels (ToolApproval),
│   │                       ChatScreenModalBottomSheetExtras (Feedback,
│   │                       PreviewImage, PreviewPdf)
│   │   ├── model/          ChatBottomSheetModels (ChatBottomSheetModel,
│   │   │                   ChatBottomSheetOption)
│   │   ├── options/        ChatOptionsBottomSheetDestination (SelectToolAccess,
│   │   │                   SelectStyle, SelectProject, ConnectorDirectory,
│   │   │                   Connectors, ConnectorDirectoryDetail, McpServerTools,
│   │   │                   McpPromptTemplate, Closed)
│   │   └── tool/approval/appuse/  MobileAppUseDestination
│   ├── dialogs/            ChatScreenDialog (Delete, StopResearch, Dismissed)
│   ├── input/draft/        DraftMessage, QueuedSendRequest
│   ├── menu/               ChatItemMenuDialogDestination (Rename, Dismissed)
│   ├── modelselector/      ModelRedirect
│   ├── parse/              ParsedContentBlock (McpToolInvocation, Thinking),
│   │                       ParsedContentBlockId
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
│   ├── bottomsheet/        CodeRemoteBottomSheetDestination
│   ├── notification/       CCRPermissionActionReceiver/Worker,
│   │                       SessionReplyActionReceiver/Worker
│   └── CodeRemoteSessionScreenParams
│
├── configs/
│   ├── flags/              UploadConfig, FileUploadConfig, StreamSmoothingConfig,
│   │                       OnboardingConfig, AgentChatWorkerTypesConfig,
│   │                       AgentChatOnboardingConfig, FlagConfigs
│   └── GrowthBookConfigs, MobileObservabilityConfig
│
├── connector/auth/         McpAuthException (ExchangeFailed, Denied, StartFailed,
│                           MissingCallbackParameters)
│
├── conway/
│   ├── protocol/           ConwayProtocol (ClientRegistrationRequest/Response,
│   │                       ConwayExtensionMeta, AgentState, StreamMessage sealed,
│   │                       RichMessage sealed, ToolResultMessage,
│   │                       ContainerHealthResponse)
│   │                       ConwayProtocolExtras (ConwaySearchHit, PaginatedMessages,
│   │                       ConwayErrorPayload, ConwayClientInfo, ContentBlock sealed,
│   │                       ModelInfo, UiDemoAppDestination)
│   └── AppForegroundDetector, ConwayAppScreen, ConwayScopeHolder, ConwayWakeWorker
│
├── core/telemetry/         SilentException
├── deeplink/               DeepLinkActivity
│
├── firebase/fcm/           AnthropicFirebaseMessagingService
│                           (FcmIntentExtras 11 keys, CcrActions 3 actions,
│                           17 FCM payload key constants)
│
├── login/                  MagicLinkSentConfig
│
├── mainactivity/           MainActivity, AssistantOverlayActivity, IntentRouter
│
├── mcpapps/
│   ├── transport/          JsonRpc, HostCapabilities, HostContext, InitializeResult,
│   │                       McpCapabilities, UiResources
│   └── McpAppModels (HydratedImageContent, McpAppFetchException,
│       ModelContextTooLargeException, DomainValidationException)
│
├── model/                  IncomingPayload
│
├── models/
│   ├── organization/
│   │   ├── configtypes/    AvailableModelsConfig, ModelFallbacksConfig,
│   │   │                   GroveConfig, GroveConfigStrings, MobileAppUseToolConfig,
│   │   │                   SpeechInputConfig
│   │   └── DefaultModelConfig
│   └── StickyModelSelection
│
├── networking/
│   ├── cookies/serializer/ SerializableCookie
│   └── AnthropicApiClient
│
├── policy/                 PermissionsRationaleActivity
│
├── project/
│   ├── create/             CreateTemplateProjectScreenParams,
│   │                       UploadMaterialsScreenParams
│   ├── details/            ProjectDetailsScreenParams,
│   │                       ProjectDetailsDialogDestination
│   │   └── custominstructions/ CustomInstructionsDialogRoute
│   ├── knowledge/          ProjectKnowledgeParams
│   └── menu/               ProjectItemMenuDialogDestination
│
├── protos/push/            ConwayWakeRequest, OpenChatRequest (Wire protos)
│
├── sessions/
│   ├── api/                ControlRequestContent (11 fields + PermissionUpdate)
│   └── types/              SessionResource, SessionTypes, SessionContext,
│                           SharedSessionData, BridgeEnvironmentInfo,
│                           ControlResponsePayload, EnvironmentResource,
│                           PostTurnSummary, SessionRequestModels
│                           (CreateSessionParams, EnvironmentCreateRequest,
│                           CreatePullRequestRequest)
│
├── settings/
│   ├── internal/           InternalSettingsScreens (4 destinations)
│   │   └── growthbook/     GrowthBookModels
│   └── SettingsAppScreen
│
├── stt/repo/               SpeechToTextLanguageNotFoundException
│   └── api/                STTApiMessage
│
├── tasks/ui/               TasksBottomSheetDestination, TasksListOverlay
│
├── tool/
│   ├── calendar/           CalendarModels
│   ├── custom/             PhoneCallMonitorEvent
│   ├── mcp/                ServerBaseFrame
│   ├── model/              ToolModels (Tool sealed, SourceImage, SearchToolInput,
│   │                       TaskStatusInput, PreviewData variants,
│   │                       ToolInvocationResult, SearchMcpRegistryInput,
│   │                       TaskListOutput),
│   │                       DisplayToolInputs (PlacesMapDisplayV0Input,
│   │                       RecipeDisplayV0Input, ChartDisplayV0Input,
│   │                       EventCreateV0Input),
│   │                       CalendarAlarmPhoneModels (EventSearchV0Input,
│   │                       AlarmCreateV0Input, PhoneCallCompletedOutput),
│   │                       LocationFormMessageModels (UserLocationV0Output,
│   │                       RequestFormInputFromUserInput, MessageComposeV1Input),
│   │                       SuggestConnectorsOutput, TaskProposeOutput
│   ├── ui/chat/            PhoneCallSheetDestination, FormSheetDestination
│   └── ToolIdentifier
│
├── types/
│   ├── environment/        AppEnvironment (PRODUCTION/STAGING/DEVELOPMENT)
│   └── strings/            DomainTypes (20+ inline value classes: ChatId,
│                           ProjectId, MessageId, SessionId, AccountId...),
│                           ModelId
│
├── ui/
│   ├── code/               SessionInputData, DiffLineComment,
│   │                       PendingAskUserQuestionSelections
│   └── MainScreen
│
├── wear/                   AuthSyncCredentials, PhoneWearableListenerService,
│                           SerializableCookieSlim
└── widget/                 ClaudeAppWidgetReceiver
```

**Total: 236 Kotlin files across 80+ packages** (commit `2d17a97`)

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
