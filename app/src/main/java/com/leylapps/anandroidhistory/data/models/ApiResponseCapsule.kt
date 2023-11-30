package com.leylapps.anandroidhistory.data.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize


class ApiResponseCapsule() : BaseApiResponseCapsule() {
    constructor(
        responseBody: String? = null,
        isSuccess: Boolean = false,
        responseCode: Int = 0,
        responseMessage: String? = null,
        contentType: String? = null
    ) : this() {
        this.responseBody = responseBody
        this.isSuccess = isSuccess
        this.responseCode = responseCode
        this.responseMessage = responseMessage
        this.contentType = contentType
    }
}

@Parcelize
open class BaseApiResponseCapsule : Parcelable {
    var responseBody: String? = null
    var isSuccess = false
    var responseCode = 0
    var responseMessage: String? = null
    var contentType: String? = null

    private companion object : Parceler<BaseApiResponseCapsule> {
        override fun BaseApiResponseCapsule.write(parcel: Parcel, flags: Int) {
            parcel.writeString(responseBody)
            parcel.writeInt(if (isSuccess) 1 else 0)
            parcel.writeInt(responseCode)
            parcel.writeString(responseMessage)
            parcel.writeString(contentType)
        }

        override fun create(parcel: Parcel): BaseApiResponseCapsule {
            val item = BaseApiResponseCapsule()
            item.responseBody = parcel.readString()
            item.isSuccess = parcel.readInt() != 0
            item.responseCode = parcel.readInt()
            item.responseMessage = parcel.readString()
            item.contentType = parcel.readString()
            return item
        }
    }
}