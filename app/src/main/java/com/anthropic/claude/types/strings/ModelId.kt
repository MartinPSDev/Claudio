package com.anthropic.claude.types.strings

@JvmInline
value class ModelId(val value: String) {
    override fun toString(): String = value
}
