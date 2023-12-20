package com.leylapps.anandroidhistory.data.models

import android.os.Parcel
import android.os.Parcelable
import com.leylapps.anandroidhistory.data.IntentServiceAPICaller
import com.leylapps.anandroidhistory.data.common.ParcelerCustom
import com.leylapps.anandroidhistory.data.models.ApiRequestCapsule.Companion.write
import com.leylapps.anandroidhistory.data.models.BaseApiRequestCapsule.Companion.write
import com.leylapps.anandroidhistory.domain.ParcelablePair
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.parcelableCreator

@Parcelize
class ApiRequestCapsule : BaseApiRequestCapsule() {
    var broadcastResultKey: String? = null

    companion object : Parceler<ApiRequestCapsule> {
        override fun create(parcel: Parcel): ApiRequestCapsule {
            return parceler.createFromParcel(parcel)
        }

        override fun ApiRequestCapsule.write(parcel: Parcel, flags: Int) {
            parceler.writeToParcel(parcel, flags, this)
        }

        @IgnoredOnParcel
        private val parceler = object : ParcelerCustom<ApiRequestCapsule> {
            override fun writeToParcel(parcel: Parcel, flags: Int, instance: ApiRequestCapsule) {
                BaseApiRequestCapsule.parceler.writeToParcel(parcel, flags, instance)
                parcel.writeString(instance.broadcastResultKey)
            }

            override fun createFromParcel(parcel: Parcel): ApiRequestCapsule {
                return createFromParcelWithInstance(parcel, ApiRequestCapsule())
            }

            override fun <SubOfT : ApiRequestCapsule> createFromParcelWithInstance(parcel: Parcel, instance: SubOfT): SubOfT {
                val filledInstance = BaseApiRequestCapsule.parceler.createFromParcelWithInstance(parcel, instance)
                filledInstance.apply {
                    broadcastResultKey = parcel.readString()
                }

                return filledInstance
            }
        }
    }
}

@Parcelize
open class BaseApiRequestCapsule : Parcelable {
    var url: String? = null
    var headers: ArrayList<ParcelablePair<String, String>>? = null
    var httpMethod: IntentServiceAPICaller.HttpMethodTypes = IntentServiceAPICaller.HttpMethodTypes.UNDEFINED
    var requestType: IntentServiceAPICaller.RequestTypes = IntentServiceAPICaller.RequestTypes.UNDEFINED
    var data: String? = null

    companion object : Parceler<BaseApiRequestCapsule> {
        // region - parceler methods
        override fun create(parcel: Parcel): BaseApiRequestCapsule {
            return parceler.createFromParcel(parcel)
        }

        override fun BaseApiRequestCapsule.write(parcel: Parcel, flags: Int) {
            parceler.writeToParcel(parcel, flags, this)
        }
        // endregion

        @IgnoredOnParcel
        val parceler = object : ParcelerCustom<BaseApiRequestCapsule> {
            override fun createFromParcel(parcel: Parcel): BaseApiRequestCapsule {
                return createFromParcelWithInstance(parcel, BaseApiRequestCapsule())
            }

            override fun <SubOfT : BaseApiRequestCapsule> createFromParcelWithInstance(parcel: Parcel, instance: SubOfT): SubOfT {
                return instance.apply {
                    url = parcel.readString()
                    // https://stackoverflow.com/questions/51799353/how-to-use-parcel-readtypedlist-along-with-parcelize-from-kotlin-android-exte
                    headers = parcel.createTypedArrayList(parcelableCreator<ParcelablePair<String, String>>())
                    httpMethod = IntentServiceAPICaller.HttpMethodTypes.getByValue(parcel.readInt()) ?: IntentServiceAPICaller.HttpMethodTypes.UNDEFINED
                    requestType = IntentServiceAPICaller.RequestTypes.getByValue(parcel.readInt()) ?: IntentServiceAPICaller.RequestTypes.UNDEFINED
                    data = parcel.readString()
                }
            }

            override fun writeToParcel(parcel: Parcel, flags: Int, instance: BaseApiRequestCapsule) {
                with(instance) {
                    parcel.writeString(url)
                    parcel.writeTypedList(headers)
                    parcel.writeInt(httpMethod.value)
                    parcel.writeInt(requestType.value)
                    parcel.writeString(data)
                }
            }
        }
    }

    override fun toString(): String {
        return "url:$url,headers:$headers,data:$data,httpMethod:$httpMethod,requestType:$requestType"
    }
}

