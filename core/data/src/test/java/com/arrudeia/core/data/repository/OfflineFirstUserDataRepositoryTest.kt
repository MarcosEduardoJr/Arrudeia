package com.nowinandroid.core.data.repository

import com.arrudeia.core.analytics.NoOpAnalyticsHelper
import com.arrudeia.core.data.repository.OfflineFirstUserDataRepository
import com.arrudeia.core.datastore.ArrudeiaPreferencesDataSource
import com.arrudeia.core.datastore.test.testUserPreferencesDataStore
import com.arrudeia.core.model.data.ThemeBrand
import com.arrudeia.core.model.data.UserData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OfflineFirstUserDataRepositoryTest {

    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var subject: OfflineFirstUserDataRepository

    private lateinit var niaPreferencesDataSource: ArrudeiaPreferencesDataSource

    private val analyticsHelper = NoOpAnalyticsHelper()

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @Before
    fun setup() {
        niaPreferencesDataSource = ArrudeiaPreferencesDataSource(
            tmpFolder.testUserPreferencesDataStore(testScope),
        )

        subject = OfflineFirstUserDataRepository(
            niaPreferencesDataSource = niaPreferencesDataSource,
            analyticsHelper,
        )
    }

    @Test
    fun offlineFirstUserDataRepository_default_user_data_is_correct() =
        testScope.runTest {
            assertEquals(
                UserData(
                    bookmarkedNewsResources = emptySet(),
                    viewedNewsResources = emptySet(),
                    followedTopics = emptySet(),
                    themeBrand = ThemeBrand.DEFAULT,
                    useDynamicColor = false,
                    shouldHideOnboarding = false,
                ),
                subject.userData.first(),
            )
        }

    @Test
    fun offlineFirstUserDataRepository_toggle_followed_topics_logic_delegates_to_nia_preferences() =
        testScope.runTest {
            subject.setTopicIdFollowed(followedTopicId = "0", followed = true)

            assertEquals(
                setOf("0"),
                subject.userData
                    .map { it.followedTopics }
                    .first(),
            )

            subject.setTopicIdFollowed(followedTopicId = "1", followed = true)

            assertEquals(
                setOf("0", "1"),
                subject.userData
                    .map { it.followedTopics }
                    .first(),
            )

            assertEquals(
                niaPreferencesDataSource.userData
                    .map { it.followedTopics }
                    .first(),
                subject.userData
                    .map { it.followedTopics }
                    .first(),
            )
        }

    @Test
    fun offlineFirstUserDataRepository_set_followed_topics_logic_delegates_to_nia_preferences() =
        testScope.runTest {
            subject.setFollowedTopicIds(followedTopicIds = setOf("1", "2"))

            assertEquals(
                setOf("1", "2"),
                subject.userData
                    .map { it.followedTopics }
                    .first(),
            )

            assertEquals(
                niaPreferencesDataSource.userData
                    .map { it.followedTopics }
                    .first(),
                subject.userData
                    .map { it.followedTopics }
                    .first(),
            )
        }

    @Test
    fun offlineFirstUserDataRepository_bookmark_news_resource_logic_delegates_to_nia_preferences() =
        testScope.runTest {
            subject.updateNewsResourceBookmark(newsResourceId = "0", bookmarked = true)

            assertEquals(
                setOf("0"),
                subject.userData
                    .map { it.bookmarkedNewsResources }
                    .first(),
            )

            subject.updateNewsResourceBookmark(newsResourceId = "1", bookmarked = true)

            assertEquals(
                setOf("0", "1"),
                subject.userData
                    .map { it.bookmarkedNewsResources }
                    .first(),
            )

            assertEquals(
                niaPreferencesDataSource.userData
                    .map { it.bookmarkedNewsResources }
                    .first(),
                subject.userData
                    .map { it.bookmarkedNewsResources }
                    .first(),
            )
        }

    @Test
    fun offlineFirstUserDataRepository_update_viewed_news_resources_delegates_to_nia_preferences() =
        runTest {
            subject.setNewsResourceViewed(newsResourceId = "0", viewed = true)

            assertEquals(
                setOf("0"),
                subject.userData
                    .map { it.viewedNewsResources }
                    .first(),
            )

            subject.setNewsResourceViewed(newsResourceId = "1", viewed = true)

            assertEquals(
                setOf("0", "1"),
                subject.userData
                    .map { it.viewedNewsResources }
                    .first(),
            )

            assertEquals(
                niaPreferencesDataSource.userData
                    .map { it.viewedNewsResources }
                    .first(),
                subject.userData
                    .map { it.viewedNewsResources }
                    .first(),
            )
        }

    @Test
    fun offlineFirstUserDataRepository_set_theme_brand_delegates_to_nia_preferences() =
        testScope.runTest {
            subject.setThemeBrand(ThemeBrand.ANDROID)

            assertEquals(
                ThemeBrand.ANDROID,
                subject.userData
                    .map { it.themeBrand }
                    .first(),
            )
            assertEquals(
                ThemeBrand.ANDROID,
                niaPreferencesDataSource
                    .userData
                    .map { it.themeBrand }
                    .first(),
            )
        }

    @Test
    fun offlineFirstUserDataRepository_set_dynamic_color_delegates_to_nia_preferences() =
        testScope.runTest {
            subject.setDynamicColorPreference(true)

            assertEquals(
                true,
                subject.userData
                    .map { it.useDynamicColor }
                    .first(),
            )
            assertEquals(
                true,
                niaPreferencesDataSource
                    .userData
                    .map { it.useDynamicColor }
                    .first(),
            )
        }



    @Test
    fun whenUserCompletesOnboarding_thenRemovesAllInterests_shouldHideOnboardingIsFalse() =
        testScope.runTest {
            subject.setFollowedTopicIds(setOf("1"))
            subject.setShouldHideOnboarding(true)
            assertTrue(subject.userData.first().shouldHideOnboarding)

            subject.setFollowedTopicIds(emptySet())
            assertFalse(subject.userData.first().shouldHideOnboarding)
        }
}
