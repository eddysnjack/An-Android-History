package com.leylapps.anandroidhistory.data.models

import com.leylapps.anandroidhistory.data.IntentServiceAPICaller

class ApiRequestCapsule : BaseApiRequestCapsule() {
    val broadcastResultKey: String? = null
}

open class BaseApiRequestCapsule {
    val url: String? = null
    val headers: ArrayList<Pair<String, String>>? = null
    val data: String? = null
    val httpMethod: IntentServiceAPICaller.HttpMethodTypes = IntentServiceAPICaller.HttpMethodTypes.UNDEFINED
    val requestType: IntentServiceAPICaller.RequestTypes? = null
}