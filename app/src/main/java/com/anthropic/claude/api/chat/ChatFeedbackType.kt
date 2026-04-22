package com.anthropic.claude.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ChatFeedbackType(val value: String) {
    @SerialName("upvote") UPVOTE("upvote"),
    @SerialName("flag") FLAG("flag"),
    @SerialName("flag/bug") FLAG_BUG("flag/bug"),
    @SerialName("flag/harmful") FLAG_HARMFUL("flag/harmful"),
    @SerialName("flag/refusal") FLAG_REFUSAL("flag/refusal"),
    @SerialName("flag/file") FLAG_FILE("flag/file"),
    @SerialName("flag/instructions") FLAG_INSTRUCTIONS("flag/instructions"),
    @SerialName("flag/facts") FLAG_FACTS("flag/facts"),
    @SerialName("flag/incomplete") FLAG_INCOMPLETE("flag/incomplete"),
    @SerialName("sc/false_positive") FLAG_SAFETY_CLASSIFIER_FALSE_POSITIVE("sc/false_positive"),
    @SerialName("flag/other") FLAG_OTHER("flag/other");

    override fun toString(): String = value
}
