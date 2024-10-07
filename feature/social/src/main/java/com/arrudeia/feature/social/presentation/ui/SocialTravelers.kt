package com.arrudeia.feature.social.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arrudeia.core.designsystem.R
import com.arrudeia.core.designsystem.component.ArrudeiaButtonElevationColor
import com.arrudeia.core.designsystem.component.ArrudeiaLoadingWheel
import com.arrudeia.core.profile.GenderOptions
import com.arrudeia.core.profile.InterestsOptions
import com.arrudeia.feature.social.data.entity.TravelersEntity
import com.arrudeia.feature.social.presentation.viewmodel.SocialTravelersViewModel
import com.arrudeia.feature.social.presentation.viewmodel.TravelersUiState
import com.arrudeia.feature.social.presentation.viewmodel.UpdateTravelerUiState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.Placeholder
import com.bumptech.glide.integration.compose.placeholder

// SocialTravelers.kt
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SocialTravelers(viewModel: SocialTravelersViewModel = hiltViewModel()) {
    val textGrey = colorResource(id = R.color.text_grey)
    val uiState by viewModel.travelersSharedFlow.collectAsStateWithLifecycle()

    var isUpdateSaved by remember { mutableStateOf(true) }
    val updateUiState by viewModel.updateTravelersSharedFlow.collectAsStateWithLifecycle()

    when (uiState) {
        is TravelersUiState.Loading -> {
            ArrudeiaLoadingWheel()
        }

        is TravelersUiState.NoMoreTravelers -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = "Não há mais viajantes disponíveis, por favor deixe sempre a sua localização permitida ativa para que possamos te mostrar mais pessoas.",
                    style = MaterialTheme.typography.titleMedium,
                    color = textGrey,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        else -> {}
    }
    when (updateUiState) {
        is UpdateTravelerUiState.Loading -> {
            ArrudeiaLoadingWheel()
        }
        else -> {    isUpdateSaved = true}
    }


    val placeholder = placeholder(R.drawable.ic_arrudeia_logo)
    val travelers = viewModel.travelers


    if (travelers.isNotEmpty() && isUpdateSaved)
        TravelerItem(
            travelers.first(),
            placeholder,
            textGrey,
            matchClick = { travelersEntity, isMatch ->
                if (isUpdateSaved) {
                    isUpdateSaved = false
                    viewModel.removeTraveler(travelersEntity, isMatch)
                }
            })

}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun TravelerItem(
    traveler: TravelersEntity,
    placeholder: Placeholder,
    textGrey: Color,
    matchClick: (TravelersEntity, Boolean) -> Unit
) {
    val context = LocalContext.current
    LazyColumn {
        item {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    Card(
                        modifier = Modifier
                            .align(Alignment.TopCenter),
                        shape = RoundedCornerShape(0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                        ) {
                            GlideImage(
                                model = traveler.img,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(280.dp),
                                loading = placeholder,
                                failure = placeholder,
                                colorFilter = null
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 270.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White)
                        ) {
                            Column(
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 16.dp,
                                    top = 0.dp
                                )
                            ) {
                                Text(
                                    text = traveler.name.orEmpty(),
                                    modifier = Modifier
                                        .padding(top = 10.dp)
                                        .fillMaxWidth(),
                                    Color.Black,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleLarge,
                                )

                                Text(
                                    text = traveler.city.orEmpty(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textGrey,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                                traveler.gender?.let {
                                    if (it.isNotEmpty())
                                        Icon(
                                            painterResource(
                                                id = GenderOptions.getDrawableFromEnum(
                                                    context,
                                                    GenderOptions.valueOf(traveler.gender)
                                                )
                                            ),
                                            tint = Color.Unspecified,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(24.dp)
                                                .align(Alignment.CenterHorizontally)
                                        )
                                }
                                Row(
                                    modifier = Modifier
                                        .padding(top = 10.dp)
                                ) {
                                    ArrudeiaButtonElevationColor(
                                        onClick = { matchClick(traveler, false) },
                                        modifier = Modifier.weight(0.5f),
                                        colorButton = colorResource(id = R.color.colorRed),
                                        buttonElevation = 2.dp,
                                        content = {
                                            Row {
                                                Icon(
                                                    painterResource(id = R.drawable.ic_deslike),
                                                    tint = Color.White,
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .size(24.dp)
                                                        .padding(end = 6.dp)
                                                        .align(Alignment.CenterVertically)
                                                )
                                                Text(
                                                    text = "Hoje não",
                                                    style = MaterialTheme.typography.titleMedium,
                                                    color = Color.White,
                                                )
                                            }
                                        }
                                    )
                                    Spacer(modifier = Modifier.size(10.dp))
                                    ArrudeiaButtonElevationColor(
                                        onClick = { matchClick(traveler, true) },
                                        modifier = Modifier.weight(0.5f),
                                        colorButton = colorResource(id = R.color.colorGreen),
                                        buttonElevation = 2.dp,
                                        content = {
                                            Row {
                                                Icon(
                                                    painterResource(id = R.drawable.ic_like),
                                                    tint = Color.White,
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .size(24.dp)
                                                        .padding(end = 6.dp)
                                                        .align(Alignment.CenterVertically)
                                                )
                                                Text(
                                                    text = "Hoje sim",
                                                    style = MaterialTheme.typography.titleMedium,
                                                    color = Color.White,
                                                )
                                            }
                                        }
                                    )
                                }

                                if (traveler.interests.orEmpty().isNotEmpty()) {
                                    Text(
                                        text = "Interesses",
                                        modifier = Modifier
                                            .padding(top = 20.dp)
                                            .fillMaxWidth(),
                                        textGrey,
                                        textAlign = TextAlign.Start,
                                        style = MaterialTheme.typography.titleMedium,
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 4.dp)
                                    ) {
                                        traveler.interests.orEmpty().split(",").forEach {
                                            Text(
                                                text = InterestsOptions.getStringFromEnum(
                                                    context,
                                                    InterestsOptions.valueOf(it)
                                                ).orEmpty(),
                                                color = Color.Black,
                                                textAlign = TextAlign.Start,
                                                style = MaterialTheme.typography.bodyMedium,
                                            )
                                            Spacer(modifier = Modifier.size(4.dp))
                                        }
                                    }
                                }


                                if (traveler.aboutMe.orEmpty().isNotEmpty()) {
                                    Text(
                                        text = "Sobre mim",
                                        modifier = Modifier
                                            .padding(top = 10.dp)
                                            .fillMaxWidth(),
                                        textGrey,
                                        textAlign = TextAlign.Start,
                                        style = MaterialTheme.typography.titleMedium,
                                    )

                                    Text(
                                        text = traveler.aboutMe.orEmpty(),
                                        modifier = Modifier
                                            .padding(top = 4.dp)
                                            .fillMaxWidth(),
                                        Color.Black,
                                        textAlign = TextAlign.Start,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}