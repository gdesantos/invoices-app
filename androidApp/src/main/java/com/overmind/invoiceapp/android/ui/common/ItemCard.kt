package com.overmind.invoiceapp.android.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemCard(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
    ) {
        Box(modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp)) { content() }
    }
}

@Composable
fun ItemCardField(icon: ImageVector?, text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = null)
        } else {
            Box(modifier = Modifier.size(24.dp))
        }
        Text(text = text, fontSize = 14.sp)
    }
}
