package com.adit.mentionedittext

import com.adit.library.Mention

data class Person(
    val name: String,
): Mention {

    override fun getDisplayedText(): String {
        return name
    }

    override fun getDisplayedTextInEditText(): String {
        return "@$name"
    }
}
