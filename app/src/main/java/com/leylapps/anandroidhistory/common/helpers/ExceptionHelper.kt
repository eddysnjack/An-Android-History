package com.leylapps.anandroidhistory.common.helpers

import java.io.PrintWriter
import java.io.StringWriter


object ExceptionHelper {
    // https://stackoverflow.com/questions/21706722/fetch-only-first-n-lines-of-a-stack-trace
    fun shortenedStackTrace(e: Exception, maxLines: Int): String {
        val writer = StringWriter()
        e.printStackTrace(PrintWriter(writer))
        val lines = writer.toString().split("\n")
        val sb = StringBuilder()
        for (i in 0 until Math.min(lines.size, maxLines)) {
            sb.append(lines[i]).append("\n")
        }
        return sb.toString()
    }
}