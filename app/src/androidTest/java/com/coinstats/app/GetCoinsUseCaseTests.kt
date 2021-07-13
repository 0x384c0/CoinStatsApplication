package com.coinstats.app

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

    private fun loadCoins(): TestObserver<List<Coin>> {
        val observer = TestObserver<List<Coin>>()
        getCoinsUseCase
            .getCoins()
            .subscribe(observer)
        return observer
    }
    //endregion

    //region Tests
    @Test
    fun testGetCoins() {
        //load data and populate cache
        val observer = loadCoins()
        observer.assertComplete()
        observer.assertNoErrors()
        val result = observer.values().last()

        //asserts
        assertEquals(20, result.count())
    }

    @Test
    fun testGetCoinsFromCache() {
        //load data and populate cache
        val observer = loadCoins()
        observer.assertComplete()
        observer.assertNoErrors()

        //load data from cache and network
        val observerWithCache = loadCoins()
        observerWithCache.assertNoErrors()
        observerWithCache.assertComplete()
        observerWithCache.assertValueCount(2)

        val resultCache = observerWithCache.values().first()
        val result = observerWithCache.values().last()

        //asserts
        assertEquals(20, resultCache.count())
        assertEquals(20, result.count())
    }

    @Test
    @Suppress("SpellCheckingInspection")
    fun testSearch() {
        //load data and populate cache
        val observer = loadCoins()
        observer.assertComplete()
        observer.assertNoErrors()

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