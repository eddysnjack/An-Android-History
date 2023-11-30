package com.leylapps.anandroidhistory.data.models

sealed class Headers {
    class ContentType : Headers() {
        // https://www.iana.org/assignments/media-types/media-types.xhtml
        // https://stackoverflow.com/questions/23714383/what-are-all-the-possible-values-for-http-content-type-header
        companion object {
            const val key: String = "Content-Type"
            const val valueApplicationJavaArchive = "application/java-archive"
            const val valueApplicationEdiX12 = "application/EDI-X12"
            const val valueApplicationEdifact = "application/EDIFACT"
            const val valueApplicationJavascript = "application/javascript"
            const val valueApplicationOctetStream = "application/octet-stream"
            const val valueApplicationOgg = "application/ogg"
            const val valueApplicationPdf = "application/pdf"
            const val valueApplicationXhtmlXml = "application/xhtml+xml"
            const val valueApplicationJson = "application/json"
            const val valueApplicationXml = "application/xml"
            const val valueApplicationZip = "application/zip"
            const val valueApplicationFormUrlencoded = "application/x-www-form-urlencoded"
            const val valueMultipartMixed = "multipart/mixed"
            const val valueMultipartAlternative = "multipart/alternative"
            const val valueMultipartRelated = "multipart/related"
            const val valueMultipartFormData = "multipart/form-data"
            const val valueTextCss = "text/css"
            const val valueTextCsv = "text/csv"
            const val valueTextHtml = "text/html"
            const val valueTextJavascript = "text/javascript"
            const val valueTextPlain = "text/plain"
            const val valueTextXml = "text/xml"
            fun getWithCharsetUtf8(value: String): String {
                return "${value};charset=UTF-8"
            }

            fun getWithCharset(value: String, charset: String): String {
                return "${value};charset=${charset}"
            }
        }
    }

    class Authorization : Headers() {
        companion object {
            private const val key = "Authorization"
            private const val valueTemplate = "%s %s"
            private const val valueBearer = "Bearer %s"
            private const val valueBasic = "Basic %s"
            private const val valueDigest = "Digest %s"
        }
    }
}
