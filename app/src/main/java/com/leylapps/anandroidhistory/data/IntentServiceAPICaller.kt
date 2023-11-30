package com.leylapps.anandroidhistory.data

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.leylapps.anandroidhistory.common.helpers.ExceptionHelper
import com.leylapps.anandroidhistory.data.models.ApiRequestCapsule
import com.leylapps.anandroidhistory.data.models.ApiResponseCapsule
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class IntentServiceAPICaller : IntentService(SERVICE_NAME) {
    companion object {
        val SERVICE_NAME: String = IntentServiceAPICaller::class.simpleName ?: "IntentServiceAPICaller"
        val API_SERVICE_INTENT_KEY = "com.leylapps.anandroidhistory.data.IntentServiceAPICaller.API_SERVICE_INTENT_KEY"
        val API_SERVICE_BROADCAST_RESULT_KEY = "com.leylapps.anandroidhistory.data.IntentServiceAPICaller.API_SERVICE_BROADCAST_RESULT_KEY"
    }

    enum class RequestTypes(val value: Int) {
        UNDEFINED(0),
        RAW_TEXT_OR_JSON(1),
        MULTI_PART_FORM_DATA(2),
        FILE(3);

        companion object {
            private val VALUES = values()
            fun getByValue(value: Int) = VALUES.firstOrNull { it.value == value }
        }
    }

    enum class HttpMethodTypes(val value: Int, val methodName: String) {
        UNDEFINED(0, ""),
        GET(1, "GET"),
        POST(2, "POST"),
        PUT(3, "PUT"),
        DELETE(4, "DELETE");

        companion object {
            private val VALUES = values()
            fun getByValue(value: Int) = VALUES.firstOrNull { it.value == value }
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        var serviceRequest: ApiRequestCapsule? = null
        if (intent != null) {
            serviceRequest = (intent.getParcelableArrayExtra(API_SERVICE_INTENT_KEY) as ApiRequestCapsule?)
            if (serviceRequest != null) {
                // Get data from the incoming Intent
                val urlAddress: String = serviceRequest.url ?: ""
                val httpMethod: HttpMethodTypes = serviceRequest.httpMethod
                val postData: String? = serviceRequest.data
                val headers = serviceRequest.headers
//                val formFields: HashMap<String, String> = serviceRequest.getFormFields()
//                val fileFields: HashMap<String, String> = serviceRequest.getFileFields()
//                val savePath: String = serviceRequest.getSavePath()
                val broadcastResultKey: String = serviceRequest.broadcastResultKey ?: ""

                when (serviceRequest.requestType) {
                    RequestTypes.RAW_TEXT_OR_JSON -> {
                        flowControllerForRawRequests(urlAddress, httpMethod, postData, headers, broadcastResultKey)
                    }
//                    RequestTypes.MULTI_PART_FORM_DATA -> {
//                        flowControllerForMultiPartRequests(urlAddress, httpMethod, bearerToken, formFields, fileFields, broadcastResultKey)
//                    }
//
//                    RequestTypes.FILE -> flowControllerForFileRequests(urlAddress, httpMethod, bearerToken, broadcastResultKey, savePath)
                    else -> {}
                }
            }
        }
    }


//    private fun flowControllerForFileRequests(urlAddress: String, httpMethod: HttpMethodTypes, bearerToken: String, broadcastResultKey: String, savePath: String) {
//        when (httpMethod) {
//            HttpMethodTypes.GET -> {
//                val serviceResponse: ServiceResponse = getFileAndSaveIt(urlAddress, savePath, bearerToken)
//                sendBroadcastBackToActivity(broadcastResultKey, serviceResponse) // SendBroadcast is an internal method, method name changed in order to avoid confusion.
//            }
//
//            else -> {}
//        }
//    }

//    private fun flowControllerForMultiPartRequests(urlAddress: String, httpMethod: HttpMethodTypes, bearerToken: String, formFields: HashMap<String, String>, fileFields: HashMap<String, String>, broadcastResultKey: String) {
//        when (httpMethod) {
//            HttpMethodTypes.POST -> {
//                val headers = HashMap<String, String>()
//                headers["Authorization"] = "Bearer $bearerToken"
//                val serviceResponse: ServiceResponse = sendMultiPart(urlAddress, "UTF-8", headers, formFields, fileFields, "POST")
//                sendBroadcastBackToActivity(broadcastResultKey, serviceResponse)
//            }
//
//            HttpMethodTypes.PUT -> {
//                val headers = HashMap<String, String>()
//                headers["Authorization"] = "Bearer $bearerToken"
//                val serviceResponse: ServiceResponse = sendMultiPart(urlAddress, "UTF-8", headers, formFields, fileFields, "PUT")
//                sendBroadcastBackToActivity(broadcastResultKey, serviceResponse)
//            }
//        }
//    }

    private fun sendBroadcastBackToActivity(broadcastResultKey: String, serviceResponse: ApiResponseCapsule) {
        val localIntent = Intent(API_SERVICE_BROADCAST_RESULT_KEY)
        localIntent.putExtra(broadcastResultKey, serviceResponse)
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent)
    }

    private fun flowControllerForRawRequests(urlAddress: String, httpMethod: HttpMethodTypes, postData: String?, headers: ArrayList<Pair<String, String>>?, broadcastResultKey: String) {
        val serviceResponse: ApiResponseCapsule = makeHttpRawRequest(urlAddress, httpMethod, postData, headers)
        sendBroadcastBackToActivity(broadcastResultKey, serviceResponse)
    }

    private fun makeHttpRawRequest(urlAddress: String, httpMethod: HttpMethodTypes, postData: String?, headers: ArrayList<Pair<String, String>>?): ApiResponseCapsule {
        var responseBody: String? = null
        var responseCode = -1
        var responseMessage = ""
        var isConnectionSucceed: Boolean = true
        var responseContentType: String? = null
        var httpURLConnection: HttpURLConnection? = null
        try {
            val url = URL(urlAddress)
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = httpMethod.methodName
            headers?.forEach {
                httpURLConnection.setRequestProperty(it.first, it.second)
            }
            httpURLConnection.connectTimeout = 8000
            if (postData.isNullOrBlank().not()) {
                val outputBytes = postData!!.toByteArray()
                val outputStream = httpURLConnection.outputStream
                outputStream.write(outputBytes)
                outputStream.close()
            }

            responseCode = httpURLConnection.responseCode
            responseMessage = httpURLConnection.responseMessage
            responseContentType = httpURLConnection.getHeaderField("Content-Type")
            if (responseCode.toString()[0] == '2') {
                if (responseContentType != null) {
                    if (responseContentType.contains("json") || responseContentType.contains("text")) {
                        val bufferedReader = BufferedReader(InputStreamReader(httpURLConnection.inputStream))
                        val stringBuilder = StringBuilder()
                        var line: String?
                        while (bufferedReader.readLine().also { line = it } != null) {
                            stringBuilder.append(line).append("\r\n")
                        }
                        bufferedReader.close()
                        responseBody = stringBuilder.toString()
                    }
                }
            } else {
                isConnectionSucceed = false
                if (responseContentType != null) {
                    if (responseContentType.contains("json") || responseContentType.contains("text")) {
                        val bufferedReader = BufferedReader(InputStreamReader(httpURLConnection.errorStream)) //https://github.com/square/okhttp/issues/676
                        val stringBuilder = StringBuilder()
                        var line: String?
                        while (bufferedReader.readLine().also { line = it } != null) {
                            stringBuilder.append(line).append("\r\n")
                        }
                        bufferedReader.close()
                        responseBody = stringBuilder.toString()
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            isConnectionSucceed = false
            responseMessage = ExceptionHelper.shortenedStackTrace(e, 1)
        } finally {
            httpURLConnection?.disconnect()
        }
        return ApiResponseCapsule(responseBody, isConnectionSucceed, responseCode, responseMessage, responseContentType)
    }
}