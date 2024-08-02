package me.androidbox.presentation.coin_list

data class CoinListState(
    val imageUri: String = "",
    val name: String = "",
    val symbol: String = "",
    val price: String = "",
    val change: String = "",
    val uuid: String = "",
    val description: String = "",
    val websiteUrl: String = "",
    val marketCap: String = "",
    val color: String = "",
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val itemCardType: ItemCardType = ItemCardType.COIN_CARD
) {
    enum class ItemCardType {
        COIN_CARD,
        INVITE_FRIEND_CARD
    }
}
