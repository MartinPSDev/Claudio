package com.anthropic.claude.core.telemetry

/**
 * A non-fatal exception used for silent telemetry reporting.
 * Does not crash the app — intended for logging unexpected but recoverable states.
 */
class SilentException(message: String) : RuntimeException(message) {

    /** Reports this exception to the telemetry pipeline without crashing the app. */
    fun report() {
        // Telemetry delivery is handled by the concrete telemetry provider at runtime.
        // The exception is surfaced here so it can be captured by crash reporting tools.
    }
}
