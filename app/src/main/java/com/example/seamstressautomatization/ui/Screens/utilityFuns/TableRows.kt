package com.example.seamstressautomatization.ui.Screens.utilityFuns

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun TitleRow(head1: String, head2: String, head3: String) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(head1, color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .weight(0.1f))
        Text(head2, color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .weight(0.2f))
        Text(head3, color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.weight(0.2f))
    }
}

@Composable
fun TableRow(id: Int, name: String, stats: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        OutlinedCard(shape = CardDefaults.elevatedShape, border = BorderStroke(0.5.dp, Color.Black)
        ) {
            Row {
                Text(id.toString(), modifier = Modifier
                    .weight(0.1f))
                Text(name, modifier = Modifier.weight(0.2f))
                Text(stats.toString(), modifier = Modifier.weight(0.2f))
            }
        }
    }
}

@Composable
fun CustomTextField(
    title: String,
    textState: String,
    onTextChange: (String) -> Unit,
    keyboardType: KeyboardType,
    placeholder: String,
) {
    OutlinedTextField(
        value = textState,
        onValueChange = { onTextChange(it) },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        singleLine = true,
        label = { Text(title)},
        modifier = Modifier.padding(16.dp),
        placeholder = {placeholder},
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.DarkGray,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.DarkGray,
        ),
        shape = RoundedCornerShape(16.dp),
        trailingIcon = {if (textState.isNotEmpty()) IconButton(onClick = { onTextChange("") }) {
            Icon(Icons.Filled.Clear, contentDescription = null)
        } else null}
    )
}