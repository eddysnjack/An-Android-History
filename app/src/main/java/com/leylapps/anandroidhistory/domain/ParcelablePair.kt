package com.leylapps.anandroidhistory.domain

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
class ParcelablePair<A : Any, B : Any>(
    val first: @RawValue A,
    val second: @RawValue B
) : Parcelable {
    constructor(parcel: Parcel) : this(
        // https://stackoverflow.com/questions/47896351/get-generics-class-loader-for-parsing-nested-parcelable-generic-field
        first = parcel.readValue(ParcelablePair<A, B>::first.javaClass.classLoader) as A,
        second = parcel.readValue(ParcelablePair<A, B>::second.javaClass.classLoader) as B
    )

    companion object : Parceler<ParcelablePair<*, *>> {
        override fun create(parcel: Parcel): ParcelablePair<*, *> {
            return ParcelablePair<Any, Any>(parcel)
        }

        override fun ParcelablePair<*, *>.write(parcel: Parcel, flags: Int) {
            parcel.writeValue(first)
            parcel.writeValue(second)
        }
    }

    public override fun toString(): String = "($first, $second)"
}