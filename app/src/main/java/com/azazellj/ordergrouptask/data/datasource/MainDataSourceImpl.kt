package com.azazellj.ordergrouptask.data.datasource

import com.azazellj.ordergrouptask.domain.datasource.MainDataSource
import com.azazellj.ordergrouptask.domain.model.Query
import com.azazellj.ordergrouptask.network.api.MainAPI
import com.azazellj.ordergrouptask.network.response.QueryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class MainDataSourceImpl(
    private val mainAPI: MainAPI,
) : MainDataSource {

    override suspend fun fetchQuery(
        query: String,
    ): Result<Query> {
        return withContext(Dispatchers.IO) {
            runCatching {
                mainAPI.fetchQuery(
                    query = query,
                )
            }
        }.map { model ->
            model.toDomain()
        }
    }
}

private fun QueryResponse.toDomain(): Query {
    return Query(
        unitName = unitName,
        station = station,
        availableDate = availableDate,
        query = query,
    )
}