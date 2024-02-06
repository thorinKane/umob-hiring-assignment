package com.thorkane.playground.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thorkane.playground.data.Choice
import com.thorkane.playground.ui.theme.PlaygroundTheme

@Composable
fun BasicRadioButton(
    option: Choice,
     isSelected: Boolean,
     onClick: (option: Choice) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .selectable(
                selected = isSelected,
                onClick = { onClick(option) },
                role = Role.RadioButton
            )
            .padding(8.dp)
    ) {
        RadioButton(
            selected = isSelected,
            enabled = true,
            onClick = { onClick(option) }
        )
        Text(
            text = option.prompt,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BasicRadioButtonPreview() {
    var isSelected by remember {
        mutableStateOf(false)
    }
    PlaygroundTheme {
        BasicRadioButton(Choice("item 1"), isSelected) {
            isSelected = !isSelected
        }
    }
}

