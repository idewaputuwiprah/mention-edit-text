package com.adit.library

import android.content.Context
import android.text.Editable
import android.text.Spannable
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import timber.log.Timber

class MentionEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private var mentionColor = ContextCompat.getColor(context, DEFAULT_MENTION_COLOR)
    private var onMentionTriggered: ((String) -> Unit)? = null
    private var isUpdating = false

    private var startIndex: Int? = null
    private var keywords: String? = null

    private val mentions = mutableListOf<MentionSpan>()

    init {
        isFocusable = true
        isFocusableInTouchMode = true
        isClickable = true
        gravity = Gravity.CENTER_VERTICAL

        getAttributes(attrs)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == null || isUpdating) return

                if (count > before) {
                    Timber.tag("MentionEditTextTag").d("""
                        before: $before
                        count: $count
                        start: $start
                        s: $s
                    """.trimIndent())

                    val newChar = s.getOrNull(start + count - 1)
                    if (newChar == '@') {
                        startIndex = start
                        return
                    }

                    if (startIndex != null) {
                        val keywords = s.toString().substring(startIndex!! + 1, start + count)

                        Timber.tag("MentionEditTextTag").d("keywords: $keywords")

                        this@MentionEditText.keywords = keywords
                        onMentionTriggered?.invoke(keywords)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating || s == null) return
            }
        })
    }

    private fun getAttributes(attrs: AttributeSet?) {
        if (attrs == null) return

        setPaddingRelative(
            paddingStart.getValueIfNotZero(15),
            paddingTop.getValueIfNotZero(8),
            paddingEnd.getValueIfNotZero(15),
            paddingBottom.getValueIfNotZero(8)
        )

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.MentionEditText,
            0,
            0
        )

        mentionColor = typedArray.getColor(R.styleable.MentionEditText_mentionColor, mentionColor)

        typedArray.recycle()
    }

    private fun highlightMentions(mentionSpan: MentionSpan) {
        editableText.setSpan(
            ForegroundColorSpan(mentionColor),
            mentionSpan.start,
            mentionSpan.end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    /** Get list of mentions with start and end positions */
    fun getMentions(): List<MentionSpan> = mentions

    fun setOnMentionTriggeredListener(listener: (String) -> Unit) {
        onMentionTriggered = listener
    }

    fun setMention(mention: Mention) {
        val mentionText = mention.getDisplayedTextInEditText()

        val start = startIndex ?: return
        val end = mentionText.length + start + 1

        val endKeywords = (keywords?.length ?: return).let { keywordSize->
            keywordSize + 1 + start
        }

        val span = MentionSpan(mentionText, start, end)
        mentions.add(span)

        isUpdating = true
        editableText.replace(start, endKeywords, "$mentionText ")
        highlightMentions(span)
        startIndex = null
        isUpdating = false
    }

    companion object {
        private val DEFAULT_MENTION_COLOR = R.color.primary_blue
    }
}

fun Int.getValueIfNotZero(default: Int): Int {
    return if (this == 0) default else this
}
