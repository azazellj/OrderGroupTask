package com.azazellj.ordergrouptask.domain.repository

import com.azazellj.ordergrouptask.domain.model.Query

interface MainRepository {

    suspend fun fetchQuery(
        query: String,
    ): Result<Query>
}