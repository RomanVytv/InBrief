package com.romanvytv.inbrief

import app.cash.turbine.test
import com.romanvytv.inbrief.data.repo.IBookSummaryRepository
import com.romanvytv.inbrief.fakes.FakeMediaPlayerServiceConnection
import com.romanvytv.inbrief.fakes.FakeRepository
import com.romanvytv.inbrief.ui.feature.SummaryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
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
        assertTrue(fakeMediaService.isPlaying)
        assertTrue(viewModel.playerUiState.value.isPlaying)

        viewModel.playPause()
        assertTrue(!fakeMediaService.isPlaying)
        assertTrue(!viewModel.playerUiState.value.isPlaying)
    }

    @Test
    fun `stop() should update isPlaying`() = testScope.runTest {
        viewModel.playPause()
        assertTrue(viewModel.playerUiState.value.isPlaying)

        viewModel.stop()
        assertTrue(!fakeMediaService.isPlaying)
        assertTrue(!viewModel.playerUiState.value.isPlaying)
    }

    @Test
    fun `seek() should call mediaServiceConnection seekTo`() = testScope.runTest {
        viewModel.seek(50)
        assertEquals(50, fakeMediaService.lastSeekPosition)
    }

    @Test
    fun `rewind() should seek backward by SEEK_BACKWARD_SECONDS`() = testScope.runTest {
        fakeMediaService.emitPlaybackPosition(20_000)

        viewModel.rewind()

        assertEquals(15, fakeMediaService.lastSeekPosition)
    }

    @Test
    fun `fastForward() should seek forward by SEEK_FORWARD_SECONDS`() = testScope.runTest {
        fakeMediaService.emitPlaybackPosition(20_000)

        viewModel.fastForward()

        assertEquals(30, fakeMediaService.lastSeekPosition)
    }

    @Test
    fun `changePlaybackSpeed() should set next speed`() = testScope.runTest {
        viewModel.changePlaybackSpeed()
        assertTrue(fakeMediaService.speedSet != null)
    }

    @Test
    fun `nextChapter() should keep currentChapterId same if only one chapter`() =
        testScope.runTest {
            viewModel.nextChapter()
            assertEquals(1, viewModel.chaptersUiState.value.currentChapterId)
        }

    @Test
    fun `previousChapter() should keep currentChapterId same if only one chapter`() =
        testScope.runTest {
            viewModel.previousChapter()
            assertEquals(1, viewModel.chaptersUiState.value.currentChapterId)
        }

    @Test
    fun `observePlaybackPosition should update playerUiState progress`() = testScope.runTest {
        viewModel.playerUiState.test {
            fakeMediaService.emitPlaybackPosition(10_000) // 10 seconds
            testScheduler.runCurrent()

            val state = awaitItem()
            assertEquals(10, state.progress)
        }
    }
}
