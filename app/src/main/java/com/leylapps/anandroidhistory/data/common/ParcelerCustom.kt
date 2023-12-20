package com.leylapps.anandroidhistory.data.common

import android.os.Parcel

interface ParcelerCustom<T : Any> {
    fun writeToParcel(parcel: Parcel, flags: Int, instance: T)
    fun createFromParcel(parcel: Parcel): T
    fun <SubOfT : T> createFromParcelWithInstance(parcel: Parcel, instance: SubOfT): SubOfT
}
