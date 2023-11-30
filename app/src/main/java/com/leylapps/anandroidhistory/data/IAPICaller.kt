package com.leylapps.anandroidhistory.data

import java.net.URL

interface IAPICaller {
    fun sendGet(url: URL, headers: ArrayList<Pair<String, String>>, body: String);
    fun sendPost(url: URL, headers: ArrayList<Pair<String, String>>, body: String);
    fun sendPut(url: URL, headers: ArrayList<Pair<String, String>>, body: String);
    fun sendDelete(url: URL, headers: ArrayList<Pair<String, String>>, body: String);
    fun sendMultiPart(url: URL, headers: ArrayList<Pair<String, String>>, body: String);
}
