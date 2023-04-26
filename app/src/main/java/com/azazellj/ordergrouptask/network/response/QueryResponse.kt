package com.azazellj.ordergrouptask.network.response

import com.google.gson.annotations.SerializedName

data class QueryResponse(
    @SerializedName("primær_nøgle") val unitName: String,
    @SerializedName("station") val station: String,
    @SerializedName("næste_vagt") val availableDate: String,
    @SerializedName("query") val query: String,
)