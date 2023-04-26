package com.azazellj.ordergrouptask.data.repository

import com.azazellj.ordergrouptask.domain.datasource.MainDataSource
import com.azazellj.ordergrouptask.domain.model.Query
import com.azazellj.ordergrouptask.domain.repository.MainRepository

internal class MainRepositoryImpl(
    private val mainDataSource: MainDataSource,
) : MainRepository {

    override suspend fun fetchQuery(
        query: String,
    ): Result<Query> {
        return mainDataSource.fetchQuery(
            query = query,
        )
    }
}