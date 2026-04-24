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
├── analytics/              AnalyticsProperties, AnalyticsTraits
│   ├── ads/                GooglePlayReferrer
│   ├── events/             AnalyticsEvent, AnalyticsEnums (AgeSignals/AppLaunch/PushFailureCause),
│   │                       LifecycleNetworkingEvents (AgeSignalsEvents/AppStartEvents/
│   │                       CronetAnalyticsEvents/NetworkingEvents + enums),
│   │                       AppLifecycleEvents (AgeSignalsEvents/AppStartEvents),
│   │                       PushEvents, ScreenSource
│   └── screens/            AnalyticsScreens
│
├── api/
│   ├── account/            Account, AccountProfile, AccountSettings, AccountConsentModels,
│   │                       AccountDeletableResponse, AccountId, AccountGrowthBookModels
│   │                       (CurrentUserAccess, FeatureAccess, GrowthBookExperiment/Result/
│   │                       Feature/Rule/Track, UpdateOrbitSettingsRequest),
│   │                       CheckConsentRequest, FeatureAccessStatus, UpdateAccountProfileRequest,
│   │                       UpdateAccountRequest, GrowthBookModels, GrowthBookSchema,
│   │                       Membership, Organization, OrganizationSettings, RavenType,
│   │                       SubscriptionLevel, BillingType
│   ├── artifacts/          ArtifactModels
│   ├── chat/               ChatConversation, ChatConversationSettings, ChatCompletionEvent,
│   │                       ChatCompletionRequest, ChatFeedback, ChatFeedbackType,
│   │                       ChatMessage, ChatResponseTypes, ChatModeModels,
│   │                       ChatUtilityRequests (GenerateChatTitleRequest, FillSensitiveTextRequest),
│   │                       ConversationSearch, CreateChatRequest, DeleteMessageFlagRequest,
│   │                       InputMode, MessageAssets, MessageAttachment,
│   │                       MessageFileAttachments (MessageImageFile, MessageDocumentFile, MessageBlobFile),
│   │                       MessageFileTypes (MessageFile sealed, RenderingMode),
│   │                       MessageSender, MoveChatsRequest, RecordToolApprovalRequest,
│   │                       RecordToolResultRequest, SensitiveTextField, ToolState,
│   │                       UpdatableChatConversationSettings, UpdateChatRequest
│   │   ├── citation/       CitationModels
│   │   ├── messages/       BellNoteDelta, CompactionStatus, CompactionStatusEvent,
│   │   │                   CompletionMessage, ContentBlocks (ThinkingBlock, VoiceNoteBlock),
│   │   │                   ContentBlock, ContentBlockDelta, FlagBlock,
│   │   │                   McpAuthRequiredEvent, MessageDelta, MessageFlag,
│   │   │                   MessageStreamEvents, SseEventTypes (MessageStartEvent,
│   │   │                   MessageStopEvent, ConversationReadyEvent, ContentBlockStartEvent,
│   │   │                   ContentBlockDeltaEvent, MessageDeltaEvent, FlagDelta,
│   │   │                   ThinkingSummaryDelta, CitationStartDelta, CitationEndDelta),
│   │   │                   StreamDeltaModels, StreamEvent, TextBlock, TextThinkingDeltas,
│   │   │                   ThinkingSummary, ToolResultBlock, ToolUseBlock,
│   │   │                   ToolUseBlockUpdateDelta, UnknownContentBlock
│   │   └── tool/           ArrayProperty, BooleanProperty, GenericSourceMetadata,
│   │                       GoogleDocMetadata, InputSchema, IntegerProperty, JsonBlockDisplayContent,
│   │                       KnownTool, NumberProperty, ObjectProperty, StringProperty,
│   │                       TableDisplayContent, ToolResultImage, ToolResultImageGallery,
│   │                       ToolResultText, ToolSchemaDisplayModels, CustomToolDefinition
│   ├── common/             ConsistencyLevel, RateLimit, RateLimitWithinLimit
│   ├── consent/            ConsentModels, RevokeConsentRequest, UserConsentRequest
│   ├── conway/             ConwayModels
│   ├── errors/             ApiErrors
│   ├── events/             BatchEventModels (EventAuth, BatchEventLoggingRequest),
│   │                       EventLoggingRequests (FeatureEvaluation, ExperimentExposure),
│   │                       ExperimentModels (ExperimentMetadata, GrowthBookExperiment),
│   │                       GrowthBookEventData (GrowthBookExperimentEventData,
│   │                       GrowthBookFeatureEvaluationEventData)
│   ├── experience/         ExperienceModels, Experience, SpotlightContent, BannerContent,
│   │                       ChatTooltipContent, ExperienceDismissed, ExperienceShown,
│   │                       ExperienceRateLimit, ExperienceTrackModels (ExperienceActionRequest,
│   │                       ExperienceRules, TrackActionedData, TrackDismissedData, TrackShownData),
│   │                       OpenLinkAction, RefreshCacheAction
│   ├── export/             ExportModels
│   ├── feature/            Feature, FeatureSettings
│   ├── feedback/           FeedbackModels
│   ├── kyc/                KycModels
│   ├── login/              CodeConfiguration, SendMagicLinkRequest,
│   │                       VerifyAuthRequests (VerifyGoogleMobileRequest, VerifyMagicLinkRequest)
│   ├── mcp/                McpAuthStatus, McpModels, McpServer, McpTool, McpToolAnnotations,
│   │                       DirectoryServer, DirectoryServerType,
│   │                       AttachMcpPromptRequest, CreateMcpRemoteServerRequest
│   ├── memory/             MemorySynthesisResponse
│   ├── mobile/             MobileModels
│   ├── model/              ModelOption, ThinkingModeOption, ModelCapabilities
│   ├── notification/       NotificationModels, NotificationPreferenceModels,
│   │                       NotificationPreferenceParams (Preferences,
│   │                       NotificationPreferencesUpsertParams,
│   │                       NotificationPreferencesUpdateParams), TestPushRequest
│   ├── orbit/              OrbitModels
│   ├── project/            Project, ProjectActorAccount, ProjectDoc, ProjectDocsCreateParams,
│   │                       ProjectEnums, ProjectMutationParams (ProjectCreateParams,
│   │                       ProjectUpdateParams), ProjectType, PaginatedProjectsResponse
│   ├── purchase/           PurchaseReceipt, VerifyPurchaseResponse
│   ├── result/             ApiResult<T> sealed (Success/Error/NetworkError)
│   ├── share/              ChatSnapshotModels
│   ├── styles/             Style, StyleModels, CustomStyle, DefaultStyle, StylesConfig
│   ├── sync/               AuthStatus, SyncAuthModels, FinishAuthRequest
│   ├── tasks/              ApproveTaskRequest, SendTaskMessageRequest, TaskModels,
│   │                       TaskAgentModels, McpToolUseEvent, CustomToolUseEvent,
│   │                       AgentToolUseEvent
│   ├── trusteddevice/      EnrollTrustedDeviceRequest, EnrollTrustedDeviceResponse
│   ├── usage/              UsageModels
│   ├── verification/       PhoneVerificationModels
│   └── wiggle/             WiggleDeleteFileRequest, WiggleModels
│
├── app/
│   ├── main/               MainAppScreens (LoggedIn/LoggedOut/InternalSettings/
│   │   │                   RequiredUpdate/AddAccount/UiDemoApp)
│   │   ├── loggedin/       BootstrapScreen, LoggedInScreens
│   │   │                   (ClaudeApp/Onboarding/AccountVerification/SupervisedUserBlocked)
│   │   └── loggedout/      LoggedOutAppDestination (LoginApp)
│   ├── appstart/           AppStartResponse, CachedData
│   ├── onboarding/v2/      OnboardingPage (7-step sealed)
│   ├── verification/       VerificationScreens
│   ├── ChatAppDestination, ClaudeAppFullScreenOverlay, ClaudeAppListDestination,
│   │   ClaudeAppOverlayExtra (None/CreateEnvironment/CreateProject),
│   │   ExperienceSpotlightSheet, SettingsScreenParams
│
├── application/            ClaudeApplication
│
├── artifact/
│   ├── details/            ArtifactFullScreenParams
│   ├── dialog/             ArtifactDialogModels
│   ├── model/              ArtifactMetadata, ArtifactType (9-subtype sealed)
│   └── sheet/              ArtifactSheetParams
│
├── audio/                  MicrophoneAudioException
│
├── bell/
│   ├── api/                BellApiModels
│   ├── assist/             ClaudeRecognitionService, ClaudeVoiceInteractionService,
│   │                       ClaudeVoiceInteractionSessionService, ClaudeVoiceSession
│   ├── tts/                TTSPlaybackService
│   └── BellModeService, VoiceSessionSummary
│
├── chat/
│   ├── bottomsheet/        ChatScreenActionSheets, ChatScreenApprovalSheetDestination
│   │   │                   (ToolApproval/LocalToolApproval/SshHelplines),
│   │   │                   ChatScreenBottomSheetModels, ChatScreenContentDestinations,
│   │   │                   ChatScreenMediaSheetDestination (Feedback/PreviewImage/
│   │   │                   PreviewPdf/ViewCombinedSources),
│   │   │                   ChatScreenModalDestinations (ChatScreenModalBottomSheetDestination,
│   │   │                   ChatScreenArtifactSheetDestination2),
│   │   │                   ChatScreenModalBottomSheetExtras, PreviewAttachment, PreviewLink,
│   │   │                   ViewAllWiggleArtifacts, ViewCombinedSources, ViewResearchDetails, ViewSources
│   │   ├── model/          ChatBottomSheetModels,
│   │   │                   ModelSelectorBottomSheetDestination (Closed/SelectModel/MoreModels)
│   │   └── options/        ChatOptionsBottomSheetDestination (Closed/AddToChat/Connectors/
│   │                       ConnectorDirectory/ConnectorDirectoryDetail/AddFromMcpServer/
│   │                       McpServerTools/McpPromptTemplate/SelectProject/SelectStyle/SelectToolAccess),
│   │                       ConnectorDirectoryDestinations
│   ├── dialogs/            ChatScreenDialog, ChatScreenDialogFull
│   │                       (Dismissed/Rename/Delete/StopResearch/VoiceShortcut/ShareArtifact)
│   ├── input/draft/        DraftModels
│   ├── menu/               ChatItemMenuDialogDestination
│   │                       (Rename/Dismissed/Delete/ChangeProject)
│   ├── modelselector/      ModelRedirect
│   ├── parse/              ParsedContentBlock
│   │   └── sse/            ServerSentEvents
│   ├── queue/              QueueModels
│   ├── share/              ChatShareModels
│   ├── ChatScreenOverlay, ChatScreenOverlayFull (BrowserTakeover/None),
│   │   ChatScreenParams, MessageSseService
│
├── chatlist/all/
│   ├── bottomsheet/        AllChatsListBottomSheetDestination
│   └── overlays/           AllChatsListOverlay
│
├── code/remote/
│   ├── bottomsheet/        CodeRemoteBottomSheetModels
│   ├── notification/       CCRPermissionActionReceiver/Worker,
│   │                       SessionReplyActionReceiver/Worker
│   └── CodeRemoteModels
│
├── configs/
│   ├── flags/              AgentChatOnboardingConfig, AgentChatWorkerTypesConfig,
│   │                       AgentMcpConfigs, ConditionalMcpDirectoryServersConfig,
│   │                       FileUploadConfig, FlagConfigs, McpQueueSearchConfigs
│   │                       (MessageQueueConfig/TaskAgentOverridesDebugConfig/ToolSearchConfig),
│   │                       OnboardingConfig, SendRetryConfig, SseConfig,
│   │                       StreamSmoothingConfig, ToolQueueConfigs, UploadConfig
│   └── FlexibleUpdateConfig, GrowthBookConfigs, MobileObservabilityConfig
│
├── connector/auth/         McpAuthException, McpConnectorAuthException
│
├── conway/
│   ├── protocol/           ConwayProtocol, ConwayProtocolExtras
│   └── AppForegroundDetector, ConwayAppScreen, ConwayScopeHolder, ConwayWakeWorker
│
├── core/
│   ├── logging/            EmptyDestination
│   └── telemetry/          SilentException
│
├── db/                     ClaudeDatabase (table name constants stub)
│
├── deeplink/               DeepLinkActivity
├── firebase/fcm/           AnthropicFirebaseMessagingService
│
├── login/                  AuthIntentData (MagicLinkIntentData/SSOIntentData),
│                           LoginNavigationScreens, LoginScreensFull
│                           (Welcome/MagicLinkSent/SupervisedUserBlocked),
│                           LoginStateModels, MagicLinkSentConfig,
│                           OverlayScreensFull (None/MagicLinkVerify)
│
├── mainactivity/           AssistantOverlayActivity, IntentRouter, MainActivity
│
├── mcpapps/
│   ├── transport/          HostCapabilities, HostContext, InitializeResult,
│   │                       JsonRpc, McpCapabilities, UiResources
│   └── McpAppModels
│
├── model/                  IncomingPayload
│
├── models/
│   ├── organization/
│   │   ├── configtypes/    AlarmCreateInputDescriptions, AvailableModelsConfig,
│   │   │                   BetaToolsConfig, CalendarSearchToolDescriptions,
│   │   │                   ChartDisplayInputDescriptions, ChartHealthUpsellConfigs
│   │   │                   (ChartDisplayXAxisDescription/ChartDisplayYAxisDescription/
│   │   │                   HealthConnectQueryQueriesItemDescription/UpsellConfig),
│   │   │                   ChartDisplaySeriesItemDescription, EventToolDescriptions
│   │   │                   (EventRecurrenceDescription/EventCreateNewEventsItemDescription/
│   │   │                   EventUpdateEventUpdatesItemDescription/EventCreateInputDescriptions/
│   │   │                   EventSearchInputDescriptions), GroveConfig, GroveConfigStrings,
│   │   │                   MobileAppUseToolConfig, ModelFallbacksConfig,
│   │   │                   ProjectPromptStarters, ProjectsLimitsConfig,
│   │   │                   ProjectTemplatesCopyConfig, SpeechInputConfig,
│   │   │                   StickyConfig, StickyDeedeeConfigs,
│   │   │                   ToolInputDescriptions, WidgetToolConfig
│   │   └── DefaultModelConfig
│   └── StickyModelSelection
│
├── networking/                                          ← COMPLETE NETWORKING LAYER
│   ├── ApiEndpoints.kt     55+ endpoint path constants grouped by domain:
│   │                       Auth / Account / Consent / Chat / Files (Wiggle) /
│   │                       Artifacts / Projects / Styles / Memory / Notifications /
│   │                       Orbit / Tasks / Sessions / MCP / Experiences
│   ├── AnthropicApiClient  OkHttp client — 30+ typed methods using ApiEndpoints
│   ├── NetworkInterceptors SessionInterceptor (cookie + headers) +
│   │                       AuthExpiredInterceptor (401 → logout callback)
│   ├── NetworkingModule    Factory for OkHttpClient + AnthropicApiClient
│   ├── ApiResult           ApiResult<T> sealed (Success/Error/NetworkError) +
│   │                       ClaudeApiError / ClaudeApiErrorDetail
│   └── cookies/serializer/ SerializableCookie
│
├── policy/                 PermissionsRationaleActivity
│
├── project/
│   ├── create/             CreateTemplateProjectScreenParams, UploadMaterialsScreenParams
│   ├── details/            ProjectDetailsDialogDestination, ProjectDetailsScreenParams
│   │   └── custominstructions/ CustomInstructionsDialogRoute
│   ├── knowledge/          ProjectKnowledgeParams
│   └── menu/               ProjectMenuModels
│
├── protos/push/            ConwayWakeRequest, LoggedInPushOperationsService,
│                           OpenChatRequest, OpenRequestProtos,
│                           PushOperationProtos (OpenCodeSessionRequest/OpenDispatchRequest/
│                           OpenOrbitRequest/PushOperationEnvelope), PushProtos
│
├── sessions/
│   ├── api/                ControlRequestContent
│   └── types/              BridgeEnvironmentInfo, ControlResponsePayload,
│                           CreatePullRequestRequest, CreateShareRequest,
│                           EnvironmentResource, GitProxyFileRequest, GitProxyRequests,
│                           PostTurnSummary, PrSubscriptionRequest,
│                           ReportClientPresenceRequest, RepoSessionRequests,
│                           ScanSecretsRequest, SendEventsRequest, SessionContext,
│                           SessionMutationParams (CreateSessionParams/
│                           EnvironmentCreateRequest/GenerateTitleAndBranchParams/
│                           GetBatchBranchStatusRequest/GetOrCreateDispatchSessionRequest/
│                           GitProxyCompareRequest/RepoResyncParams/
│                           SetPrAutoMergeRequest/UpdateSessionParams),
│                           SessionRequestModels, SessionResource, SessionTitleParams,
│                           SessionTypes, SharedSessionData
│
├── settings/
│   ├── internal/           InternalSettingsScreens
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
│   ├── custom/             CustomToolOutputs, DisplaySearchOutputs
│   │                       (ImageSearchOutput/RecipeDisplayOutput),
│   │                       PhoneCallMonitorEvent, PhoneUseOutput
│   ├── mcp/                McpCapabilityFrames (ToolsFrame/ResourcesFrame/PromptsFrame),
│   │                       McpFrames, ServerBaseFrame, ServerListFrame
│   ├── model/              AlarmCreateV0Input, CalendarAlarmPhoneModels,
│   │                       CalendarEventErrorModels,
│   │                       CalendarSearchV0OutputCalendarSearchError,
│   │                       CalendarUserToolInputs, ChartDisplayV0Input,
│   │                       ComposeTaskInputs, ConnectorFileToolInputs,
│   │                       DisplayToolInputs, EventCreateV0Input, EventSearchV0Input,
│   │                       FileSearchHealthModels, HealthConnectQueryResult,
│   │                       HealthMapModels, LocationFormComposeHealthModels
│   │                       (UserLocationV0OutputUserLocationResult/RequestFormInputFromUserInput/
│   │                       MessageComposeV0Input/MessageComposeV1Input/TaskProposeInput/
│   │                       HealthConnectQueryV0OutputHealthConnectQueryResult),
│   │                       LocationFormMessageModels, PhoneCalendarConnectorModels
│   │                       (PhoneCallCompletedOutput/EventSearchV0Input/
│   │                       SuggestConnectorsOutputConnectorsItem/EventCreateV0Input),
│   │                       PhoneCallCompletedInput, RequestUserBrowserTakeoverOutput,
│   │                       RichDisplayToolInputs (RecipeDisplayV0Input/PlacesMapDisplayV0Input/
│   │                       ChartDisplayV0Input), SuggestConnectorsOutput,
│   │                       TaskProposeOutput, TimeHealthInputs, TimerLocationToolInputs,
│   │                       ToolErrorModels, ToolModels, ToolOutputResults, WebSearchTool
│   ├── ui/chat/            ToolSheetDestinations
│   └── ToolIdentifier
│
├── types/
│   ├── environment/        AppEnvironment (PRODUCTION/STAGING/DEVELOPMENT)
│   └── strings/            CapabilityModeTypes (Capability/ToolSearchMode/ResearchMode),
│                           ChatId, DomainTypes (20+ inline value classes),
│                           McpStringTypes (McpToolKey/McpToolApprovalKey/McpServerId/
│                           MessageId/StyleId/EmailAddress/ThinkingMode),
│                           ModelId, OrgSessionIds (OrganizationId/AppSessionId),
│                           ProjectId
│
├── ui/
│   ├── code/               CodeUiModels
│   └── MainScreen
│
├── wear/                   AuthSyncCredentials, PhoneWearableListenerService,
│                           SerializableCookieSlim
└── widget/                 ClaudeAppWidgetReceiver
```

**Total: 448 Kotlin files across 105+ packages** (commit `f7e1734`)

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
