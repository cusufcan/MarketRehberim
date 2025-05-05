package com.cusufcan.marketrehberim.data.repository

import com.cusufcan.marketrehberim.data.source.remote.MainService
import com.cusufcan.marketrehberim.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainService: MainService,
) : MainRepository