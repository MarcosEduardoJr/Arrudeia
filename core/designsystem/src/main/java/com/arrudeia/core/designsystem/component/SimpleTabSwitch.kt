package com.arrudeia.core.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrudeia.core.designsystem.R.color.colorPrimary
import com.arrudeia.core.designsystem.R.color.text_grey
import kotlinx.coroutines.launch

@Composable
fun SimpleTabSwitch(
    modifier: Modifier,
    tabsTitle: List<String>,
    screens: List<@Composable () -> Unit>
) {
    var pagerState = rememberPagerState(initialPage = 0) { tabsTitle.size }
    val coroutineScope = rememberCoroutineScope()
    val colorPrimary = colorResource(id = colorPrimary)
    val textGrey = colorResource(id = text_grey)
    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = modifier.fillMaxWidth(),
            contentColor = Color.Transparent,
            containerColor = Color.Transparent,
            divider = { },
            indicator = { tabPositions ->
                TabRowDefaults.PrimaryIndicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = colorPrimary,
                    shape = RoundedCornerShape(100.dp),
                )
            }
        ) {
            tabsTitle.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            title,
                            fontSize = 18.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = if (pagerState.currentPage == index) colorPrimary else textGrey
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            screens[page]()
        }
    }
}