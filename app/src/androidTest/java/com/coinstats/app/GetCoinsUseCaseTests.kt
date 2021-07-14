package com.coinstats.app

import androidx.paging.*
import com.coinstats.app.domain.model.Coin
import com.coinstats.app.domain.usecase.GetCoinsUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class GetCoinsUseCaseTests {
    //region Utils
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var getCoinsUseCase: GetCoinsUseCase

    @Before
    fun setup() {
        hiltRule.inject()
    }
    //endregion

    //region Tests
    @Test
    @OptIn(ExperimentalPagingApi::class)
    fun testMediator() {
        val pagingState = PagingState<Int, Coin>(
            listOf(),
            null,
            PagingConfig(20),
            20
        )
        val observerMediator = TestObserver<RemoteMediator.MediatorResult>()
        getCoinsUseCase.getRemoteMediator()
            .loadSingle(LoadType.REFRESH, pagingState)
            .subscribe(observerMediator)
        observerMediator.awaitTerminalEvent()
        observerMediator
            .assertNoErrors()
            .assertValueCount(1)
        val result = observerMediator.values().last()
        assert(result is RemoteMediator.MediatorResult.Success)
    }

    @Test
    @Suppress("SpellCheckingInspection")
    fun testSearch() {
        //load data and populate cache
        testMediator()

        //search from cache
        val observerSearch = TestObserver<List<Coin>>()
        getCoinsUseCase
            .searchCoins("bitc")
            .subscribe(observerSearch)

        observerSearch.assertNoErrors()
        observerSearch.assertComplete()
        val result = observerSearch.values().firstOrNull()

        //asserts
        assertEquals("bitcoin", result?.firstOrNull()?.name?.lowercase())
    }
    //endregion
}