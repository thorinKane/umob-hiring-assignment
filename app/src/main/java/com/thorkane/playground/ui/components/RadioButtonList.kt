package com.thorkane.playground.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.thorkane.playground.data.Choice
import com.thorkane.playground.ui.theme.PlaygroundTheme

@Composable
fun RadioButtonList(items: List<Choice>, selectedOption: Choice?, onSelect: (Choice) -> Unit) {

    Column {
        items.forEach { option ->
            BasicRadioButton(
                option = option,
                isSelected = option == selectedOption
            ) { selection ->
                onSelect(selection)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RadioButtonListPreview() {
    PlaygroundTheme {
        val options = listOf(Choice("item 1"), Choice("item 2"), Choice("item 3"))
        RadioButtonList(options, null) {
            Log.d("RadioButtonListPreview", "Option Selected: $it")
        }
    }
}