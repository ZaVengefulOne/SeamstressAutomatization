package com.example.seamstressautomatization.ui.Screens.utilityFuns

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seamstressautomatization.R

@SuppressLint("SuspiciousIndentation")
@Composable
fun StuffItem(stuffItem: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp), elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ), border = BorderStroke(1.dp, Color.Black)
    ) {
        Row {
            Icon(
                painter = painterResource(R.drawable.person),
                contentDescription = "Article Icon",
                modifier = Modifier
                    .size(80.dp)
                    .background(MaterialTheme.colorScheme.primary),
                tint = MaterialTheme.colorScheme.surface
            )
            Column {
                Text(
//                            modifier = Modifier.padding(Dimens.paddingMedium),
                    text = stuffItem,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.surface,
                    textAlign = TextAlign.Start
                )
                Text(
//                            modifier = Modifier.padding(Dimens.paddingMedium),
                    text = "Статус: Свободен",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.surface,
                    textAlign = TextAlign.Center
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete button", tint = MaterialTheme.colorScheme.surface)
            }
        }
    }
}