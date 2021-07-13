package com.coinstats.app.mock

import com.coinstats.app.data.source.remote.CoinStatsApi
import com.coinstats.app.domain.model.Coin
import com.coinstats.app.domain.model.CoinResponse
import io.reactivex.Single


class MockCoinStatsApi : CoinStatsApi {
    override fun getCoins(limit: Int): Single<CoinResponse> {
        val coins = listOf(
            Coin(
                id = "bitcoin",
                icon = "https://static.coinstats.app/coins/Bitcoin6l39t.png",
                name = "Bitcoin",
                price = 33036.19668848853
            ),
            Coin(
                id = "ethereum",
                icon = "https://static.coinstats.app/coins/EthereumOCjgD.png",
                name = "Ethereum",
                price = 2016.005089225838
            ),
            Coin(
                id = "tether",
                icon = "https://static.coinstats.app/coins/TetherfopnG.png",
                name = "Tether",
                price = 1.0009009666646562
            ),
            Coin(
                id = "binance-coin",
                icon = "https://static.coinstats.app/coins/binancecoinOxw.png",
                name = "Binance Coin",
                price = 311.8651582324811
            ),
            Coin(
                id = "cardano",
                icon = "https://static.coinstats.app/coins/CardanojXddT.png",
                name = "Cardano",
                price = 1.2967352202746303
            ),
            Coin(
                id = "ripple",
                icon = "https://static.coinstats.app/coins/XRPdnqGJ.png",
                name = "XRP",
                price = 0.6288560118015782
            ),
            Coin(
                id = "usd-coin",
                icon = "https://static.coinstats.app/coins/usd-coiniGm.png",
                name = "USD Coin",
                price = 1.0
            ),
            Coin(
                id = "dogecoin",
                icon = "https://static.coinstats.app/coins/DogecoinIZai5.png",
                name = "Dogecoin",
                price = 0.20104150545041577
            ),
            Coin(
                id = "polkadot",
                icon = "https://static.coinstats.app/coins/1598367563043.png",
                name = "Polkadot",
                price = 14.611461461080879
            ),
            Coin(
                id = "binance-usd",
                icon = "https://static.coinstats.app/coins/binance-usdcP4.png",
                name = "Binance USD",
                price = 1.0025375302987198
            ),
            Coin(
                id = "uniswap",
                icon = "https://static.coinstats.app/coins/1601456093963.png",
                name = "Uniswap",
                price = 19.337908065323578
            ),
            Coin(
                id = "bitcoin-cash",
                icon = "https://static.coinstats.app/coins/1619105503987.png",
                name = "Bitcoin Cash",
                price = 477.73248175650605
            ),
            Coin(
                id = "litecoin",
                icon = "https://static.coinstats.app/coins/LitecoinGiD2Q.png",
                name = "Litecoin",
                price = 132.62157218748624
            ),
            Coin(
                id = "solana",
                icon = "https://static.coinstats.app/coins/solanambZ.png",
                name = "Solana",
                price = 30.16635001450815
            ),
            Coin(
                id = "chainlink",
                icon = "https://static.coinstats.app/coins/ChainLink0JkIR.png",
                name = "Chainlink",
                price = 17.56098111118889
            ),
            Coin(
                id = "wrapped-bitcoin",
                icon = "https://static.coinstats.app/coins/wrapped-bitcoinoc1.png",
                name = "Wrapped Bitcoin",
                price = 33176.0
            ),
            Coin(
                id = "matic-network",
                icon = "https://static.coinstats.app/coins/1622462644229.png",
                name = "Polygon",
                price = 0.9800679642472162
            ),
            Coin(
                id = "ethereum-classic",
                icon = "https://static.coinstats.app/coins/ethereum-classicPfU.png",
                name = "Ethereum Classic",
                price = 47.13255240259218
            ),
            Coin(
                id = "stellar",
                icon = "https://static.coinstats.app/coins/1594216268358.png",
                name = "Stellar",
                price = 0.2394264526680185
            ),
            Coin(
                id = "theta-token",
                icon = "https://static.coinstats.app/coins/theta-tokenrot.png",
                name = "Theta Network",
                price = 5.398507117346497
            )
        )

        return Single.just(CoinResponse(coins))
    }
}