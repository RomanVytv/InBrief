package com.romanvytv.inbrief

import com.romanvytv.inbrief.data.repo.IBookSummaryRepository
import com.romanvytv.inbrief.fakes.FakeMediaPlayerServiceConnection
import com.romanvytv.inbrief.fakes.FakeRepository
import com.romanvytv.inbrief.ui.feature.SummaryViewModel
import com.romanvytv.inbrief.ui.feature.player.PlaybackSpeed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mockStatic
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SummaryViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var viewModel: SummaryViewModel
    private lateinit var repository: IBookSummaryRepository
    private lateinit var fakeMediaService: FakeMediaPlayerServiceConnection

    @Before
    fun setUp() = runTest {
        Dispatchers.setMain(testDispatcher)

        mockStatic(android.util.Log::class.java).use { mocked ->
            mocked.`when`<Any> { android.util.Log.d(anyString(), anyString()) }.thenReturn(0)
        }

        repository = FakeRepository()
        fakeMediaService = FakeMediaPlayerServiceConnection()

        viewModel = SummaryViewModel(repository, fakeMediaService)

        testScope.runCurrent()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should load book summary and set correct ui states`() = testScope.runTest {
        val playerState = viewModel.playerUiState.value
        val chaptersState = viewModel.chaptersUiState.value

        assertEquals("The Intelligent Investor", playerState.title)
        assertTrue(playerState.coverPath.contains("cover.png"))
        assertEquals(120, playerState.duration)

        assertEquals(1, chaptersState.currentChapterId)
        assertEquals(1, chaptersState.chapters.size)
    }

    @Test
    fun `playPause() should play when paused and pause when playing`() = testScope.runTest {
        viewModel.playPause()
        assertTrue(viewModel.playerUiState.value.isPlaying)

        viewModel.playPause()
        assertTrue(!viewModel.playerUiState.value.isPlaying)
    }

    @Test
    fun `stop() should update isPlaying`() = testScope.runTest {
        viewModel.playPause()
        assertTrue(viewModel.playerUiState.value.isPlaying)

        viewModel.stop()
        assertTrue(!viewModel.playerUiState.value.isPlaying)
    }

    @Test
    fun `nextChapter() should update chapters correctly`() = testScope.runTest {
        viewModel.nextChapter()
        assertEquals(2, viewModel.chaptersUiState.value.currentChapterId)
        assertTrue(!viewModel.playerUiState.value.isPlaying)
        assertTrue(viewModel.playerUiState.value.progress == 0)
    }

    @Test
    fun `previousChapter() should update chapters correctly`() = testScope.runTest {
        repeat(3) { viewModel.nextChapter() }

        viewModel.previousChapter()
        assertEquals(2, viewModel.chaptersUiState.value.currentChapterId)
        assertTrue(!viewModel.playerUiState.value.isPlaying)
        assertTrue(viewModel.playerUiState.value.progress == 0)
    }

    @Test
    fun `initial playback speed should be SPEED_1`() = testScope.runTest {
        assertEquals(
            PlaybackSpeed.SPEED_1_0.speed,
            viewModel.playerUiState.value.playbackSpeed.speed
        )
    }

    @Test
    fun `changePlaybackSpeed() should set next speed`() = testScope.runTest {
        viewModel.changePlaybackSpeed()
        assertTrue(viewModel.playerUiState.value.playbackSpeed.speed == PlaybackSpeed.SPEED_1_25.speed)
        viewModel.changePlaybackSpeed()
        assertTrue(viewModel.playerUiState.value.playbackSpeed.speed == PlaybackSpeed.SPEED_1_5.speed)
    }

    @Test
    fun `changePlaybackSpeed() should cycle back to SPEED_1`() = testScope.runTest {
        repeat(PlaybackSpeed.entries.size - 1) { viewModel.changePlaybackSpeed() }
        viewModel.changePlaybackSpeed()
        assertEquals(
            PlaybackSpeed.SPEED_1_0.speed,
            viewModel.playerUiState.value.playbackSpeed.speed
        )
    }

    @Test
    fun `currentChapterId should be in limits after multiple nextChapters()`() =
        testScope.runTest {
            repeat(5) { viewModel.nextChapter() }
            assertEquals(
                viewModel.chaptersUiState.value.chapters.size,
                viewModel.chaptersUiState.value.currentChapterId
            )
        }

    @Test
    fun `currentChapterId should be in limits after multiple previousChapter()`() =
        testScope.runTest {
            repeat(5) { viewModel.previousChapter() }
            assertEquals(1, viewModel.chaptersUiState.value.currentChapterId)
        }
}
