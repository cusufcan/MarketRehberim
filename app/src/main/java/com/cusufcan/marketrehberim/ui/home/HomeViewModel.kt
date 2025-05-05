package com.cusufcan.marketrehberim.ui.home

import androidx.lifecycle.ViewModel
import com.cusufcan.marketrehberim.data.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@dagger.hilt.android.lifecycle.HiltViewModel
class HomeViewModel @Inject constructor(
    homeRepository: HomeRepository,
) : ViewModel() {
    private var _catalogueItems = MutableStateFlow(homeRepository.getCatalogueItems())
    val catalogueItems = _catalogueItems.asStateFlow()

    private var _isImagePickerVisible = MutableStateFlow(false)
    val isImagePickerVisible = _isImagePickerVisible.asStateFlow()

    fun setImageClicked(isClicked: Boolean) {
        _isImagePickerVisible.value = isClicked
    }
}