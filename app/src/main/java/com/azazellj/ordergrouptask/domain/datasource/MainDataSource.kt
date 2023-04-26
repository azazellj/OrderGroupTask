package com.azazellj.ordergrouptask.domain.datasource

import com.azazellj.ordergrouptask.domain.model.Query

interface MainDataSource {

    suspend fun fetchQuery(
        query: String,
    ): Result<Query>
}