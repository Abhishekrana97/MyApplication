package com.example.myapplication.presentation.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.myapplication.R

@Composable
fun AlertDialogComponent(title:String, body :String, onClick : ()-> Unit , onDismissClick : ()-> Unit) {

    AlertDialog(
        onDismissRequest = { onDismissClick() },
        confirmButton = {
            // below line we are adding on click
            // listener for our confirm button.
            Button(onClick = { onClick()}) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = {  onDismissClick()}) {
                Text(stringResource(R.string.dismiss))
            }
        },
        icon = {
            Icon(imageVector = Icons.Default.Warning, contentDescription = "Warning Icon")
        },
        title = {
            Text(text = title, color = Color.Black)
        },
        text = {
            Text(text = body, color = Color.DarkGray)
        },

        // below line is used to add padding to our alert dialog
        modifier = Modifier.padding(16.dp),

        // below line is used to add rounded corners to our alert dialog
        shape = RoundedCornerShape(16.dp),

        // below line is used to add background color to our alert dialog
        containerColor = Color.White,

        // below line is used to add icon color to our alert dialog
        iconContentColor = Color.Red,

        // below line is used to add title color to our alert dialog
        titleContentColor = Color.Black,

        // below line is used to add text color to our alert dialog
        textContentColor = Color.DarkGray,

        // below line is used to add elevation to our alert dialog
        tonalElevation = 8.dp,

        // below line is used to add dialog properties to our alert dialog
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
    )

}