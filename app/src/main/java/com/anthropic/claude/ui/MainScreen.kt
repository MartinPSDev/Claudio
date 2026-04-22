package com.anthropic.claude.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.anthropic.claude.R
import com.anthropic.claude.model.IncomingPayload

@Composable
fun MainScreen(payload: IncomingPayload?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = stringResource(R.string.incoming_title),
            style = MaterialTheme.typography.titleLarge,
        )

        if (payload == null) {
            Text(text = stringResource(R.string.no_input))
            return@Column
        }

        Text(text = "Action: ${payload.action}")
        payload.text?.let { Text(text = "Text: $it") }
        payload.deepLink?.let { Text(text = "DeepLink: $it") }
        payload.startChatMode?.let { Text(text = "Start mode: $it") }
        payload.startChatId?.let { Text(text = "Chat ID: $it") }
        if (payload.streamUris.isNotEmpty()) {
            Text(text = "Adjuntos: ${payload.streamUris.size}")
            payload.streamUris.forEachIndexed { index, uri ->
                Text(text = "${index + 1}. $uri")
            }
        }
    }
}
