package com.adit.library

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.ReplacementSpan

data class MentionSpan(
    val mentionText: String,
    private val textColor: Int
): ReplacementSpan() {

    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        return paint.measureText(mentionText).toInt()
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val originalColor = paint.color

        // Draw text
        paint.color = textColor
        canvas.drawText(mentionText, x, y.toFloat(), paint)

        // Restore original paint color
        paint.color = originalColor
    }
}