package com.azazellj.ordergrouptask.domain.model

data class Query(
    val unitName: String,
    val station: String,
    val availableDate: String,
    val query: String,
)