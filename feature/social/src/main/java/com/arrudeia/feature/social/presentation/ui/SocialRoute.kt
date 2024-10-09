package com.arrudeia.feature.social.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arrudeia.core.common.R
import com.arrudeia.core.common.R.string.travelers
import com.arrudeia.core.data.navigation.signRoute
import com.arrudeia.core.designsystem.component.SimpleTabSwitch
import com.arrudeia.feature.social.presentation.navigation.param.MessageParam
import com.arrudeia.feature.social.presentation.viewmodel.SocialViewModel

@Composable
fun SocialRoute(
    onRouteClick: (String) -> Unit,
    viewModel: SocialViewModel = hiltViewModel(),
    onMessageClick: (MessageParam) -> Unit
) {
    val isUserLogged = viewModel.isUserLoggedUseCase
    viewModel.isUserLogged()
    if (isUserLogged.value) {
        Column {
            SocialHeader(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                onRouteClick
            )
            SocialContent(onMessageClick)
        }
    } else
        onRouteClick(signRoute)

}

@Composable
fun SocialContent(onMessageClick: (MessageParam) -> Unit) {
    val pages = listOf(
        stringResource(id = travelers),
        stringResource(id = R.string.messages),

        //   stringResource(id = plans),
    )
    var pagerState = rememberPagerState(initialPage = 0) { pages.size }
    var selectedTab by rememberSaveable { mutableIntStateOf(pagerState.currentPage) }


    LaunchedEffect(selectedTab) {
        pagerState.scrollToPage(selectedTab)
    }

    LaunchedEffect(pagerState.currentPage) {
        selectedTab = pagerState.currentPage
    }
    SimpleTabSwitch(
        Modifier,
        pages,
        listOf(
            { SocialTravelers() },
            {
                SocialMessagesList(
                    onChatClick = { onMessageClick(it) },
                    onShowSnackbar = { title, desc -> false })
            },

            // { SocialEvents() },
        )
    )
}