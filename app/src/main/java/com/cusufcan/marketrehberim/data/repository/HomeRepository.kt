package com.cusufcan.marketrehberim.data.repository

import com.cusufcan.marketrehberim.data.model.CatalogueItem
import com.cusufcan.marketrehberim.data.source.local.CatalogueLocalSource
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val catalogueLocalSource: CatalogueLocalSource,
) {
    fun getCatalogueItems(): List<CatalogueItem> = catalogueLocalSource.getCatalogueItems()
}