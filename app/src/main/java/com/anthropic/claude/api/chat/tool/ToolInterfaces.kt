package com.anthropic.claude.api.chat.tool

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

/**
 * Sealed interface for tool display content blocks.
 * Discriminated by "type" field in JSON.
 *
 * Subtypes: CodeBlockDisplayContent, JsonBlockDisplayContent,
 * RichItemsDisplayContent, RichLinkDisplayContent, TableDisplayContent,
 * TextDisplayContent, UnknownDisplayContent
 */
@Serializable
@JsonClassDiscriminator("type")
sealed interface ToolDisplayContent

/**
 * Sealed interface for tool result content blocks.
 * Discriminated by "type" field in JSON.
 *
 * Subtypes: ToolResultImage, ToolResultImageGallery,
 * ToolResultKnowledge, ToolResultText, ToolResultUnknown
 */
@Serializable
@JsonClassDiscriminator("type")
sealed interface ToolResultContent

/**
 * Sealed interface for source metadata in citations.
 * Discriminated by "type" field in JSON.
 *
 * Subtypes: GenericSourceMetadata, GoogleDocMetadata,
 * UnknownSourceMetadata, WebpageMetadata
 */
@Serializable
@JsonClassDiscriminator("type")
sealed interface SourceMetadata
