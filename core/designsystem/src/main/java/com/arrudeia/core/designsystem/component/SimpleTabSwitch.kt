package com.arrudeia.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
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
    val interactionSource = remember { MutableInteractionSource() }
    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = modifier.fillMaxWidth(),
            contentColor = Color.Transparent,
            containerColor = Color.Transparent,
            divider = { },
            indicator = { tabPositions ->
                /*  TabRowDefaults.PrimaryIndicator(
                      Modifier
                          .tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                      color = colorPrimary,
                      shape = RoundedCornerShape(100.dp),
                  )*/
            }
        ) {
            tabsTitle.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            modifier = Modifier
                                //  .defaultMinSize(minWidth = 20.dp, minHeight = 1.dp)
                                .drawBehind {
                                    drawRoundRect(
                                        color = if (pagerState.currentPage == index) colorPrimary else Color.Transparent,
                                        cornerRadius = CornerRadius(100f)
                                    )
                                }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null,
                                    enabled = true,
                                    onClick = {

                                    }
                                ),

                            text = title,
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = if (pagerState.currentPage == index) Color.White else textGrey
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
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            screens[page]()
        }
    }
}