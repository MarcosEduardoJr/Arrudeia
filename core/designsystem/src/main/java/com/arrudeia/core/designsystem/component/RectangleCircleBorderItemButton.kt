package com.arrudeia.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrudeia.core.designsystem.R

@Composable
fun RectangleCircleBorderItemButton(
    iconStart: Int? = null,
    modifier: Modifier,
    name: String,
    iconEnd: Int? = null,
    description: String? = null
) {

    Column() {
        Card(
            modifier = modifier,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(50.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                iconStart?.let {
                    Image(
                        imageVector = ImageVector.vectorResource(iconStart),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp),
                        colorFilter = ColorFilter.tint(Color.Black),
                    )
                }

                Spacer(modifier = Modifier.size(12.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(6.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    description?.let {

                        Text(
                            text = description,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = colorResource(id = R.color.text_grey)
                        )
                    }
                }
                iconEnd?.let {
                    Image(
                        imageVector = ImageVector.vectorResource(iconEnd),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 2.dp),
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.colorBlack))
                    )
                }
            }
        }
    }
}


@Composable
fun RectangleCircleBorderItemButton(
    iconStart: ImageVector? = null,
    modifier: Modifier,
    name: String,
    iconEnd: ImageVector? = null,
    description: String? = null
) {

    Column() {
        Card(
            modifier = modifier,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(50.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                iconStart?.let {
                    Image(
                        imageVector =  iconStart,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp),
                        colorFilter = ColorFilter.tint(Color.Black),
                    )
                }

                Spacer(modifier = Modifier.size(12.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(6.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    description?.let {

                        Text(
                            text = description,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = colorResource(id = R.color.text_grey)
                        )
                    }
                }
                iconEnd?.let {
                    Image(
                        imageVector = iconEnd,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 2.dp),
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.colorBlack))
                    )
                }
            }
        }
    }
}



