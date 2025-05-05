package com.cusufcan.marketrehberim.data.source.local

import com.cusufcan.marketrehberim.R.drawable
import com.cusufcan.marketrehberim.data.model.CatalogueItem
import javax.inject.Inject

class CatalogueLocalSource @Inject constructor() {
    fun getCatalogueItems(): List<CatalogueItem> {
        return listOf<CatalogueItem>(
            CatalogueItem(
                name = "Elma",
                itemName = "apple",
                image = drawable.ic_apple,
            ),
            CatalogueItem(
                name = "Patates",
                itemName = "potato",
                image = drawable.ic_potato,
            ),
            CatalogueItem(
                name = "Sarımsak",
                itemName = "garlic",
                image = drawable.ic_garlic,
            ),
        )
    }
}