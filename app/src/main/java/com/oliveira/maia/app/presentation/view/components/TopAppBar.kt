package com.oliveira.maia.app.presentation.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.oliveira.maia.R
import com.oliveira.maia.app.presentation.ui.theme.Black
import com.oliveira.maia.app.presentation.ui.theme.TiffanyPrimary


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.White,
            titleContentColor = Black,
        ),
        title = {
            Text(
                "Visão Geral de Vendas",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp),
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.logo_icon),
                contentDescription = null,
                modifier = Modifier.run {
                    size(40.dp)
                        .clip(CircleShape)
                        .background(TiffanyPrimary)
                        .clickable { }
                        .padding(8.dp)
                }
            )
        },
    )
}
