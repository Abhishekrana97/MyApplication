package com.example.myapplication.presentation.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.myapplication.theme.Black

@Composable
fun CustomRadioButton(text: String, isSelected: Boolean, onSelect: () -> Unit) {
    // containing RadioButton and Text
    ConstraintLayout(modifier = Modifier.padding(vertical = 4.dp)) {
        val (tvTitle, radioButton) = createRefs()

        RadioButton(
            selected = isSelected,
            onClick = onSelect,
            modifier = Modifier.constrainAs(radioButton) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }
        )
        Text(text,
            color = Black,
            modifier = Modifier
            .padding(start = 20.dp)
            .constrainAs(tvTitle) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(radioButton.end)
            }
        )
    }
}