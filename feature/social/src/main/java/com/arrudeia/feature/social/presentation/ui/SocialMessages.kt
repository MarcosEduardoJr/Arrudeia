package com.arrudeia.feature.social.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.component.RectangleItemButtonImage

@Composable
fun SocialMessages() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(1) {
                RectangleItemButtonImage(
                    img = "https://firebasestorage.googleapis.com/v0/b/arrudeia-5fedc.appspot.com/o/profile_image_user%2F03YoCmlsjkUck2hyCR6oftOngI32.jpg?alt=media&token=f165a898-22a1-4386-9d56-3be841253928",
                    name = "Marcos Eduardo",
                    description = "Oi tudo bem?",
                    modifier = Modifier.clickable { },
                    iconEnd = ImageVector.vectorResource(id = R.drawable.ic_arrow_right)
                )
                Spacer(modifier = Modifier.size(8.dp))
                RectangleItemButtonImage(
                    img = "https://firebasestorage.googleapis.com/v0/b/arrudeia-5fedc.appspot.com/o/travels%2Fparque-nacional-do-catimbau.jpg?alt=media&token=d35fed86-cbfb-437e-84f7-cdbdfc29ed59",
                    name = "Vale do Catimbau",
                    description = "Oi galera! como vai funcionar o passeio?",
                    modifier = Modifier.clickable { },
                    iconEnd = ImageVector.vectorResource(id = R.drawable.ic_arrow_right)
                )
            }
        }
    }
}