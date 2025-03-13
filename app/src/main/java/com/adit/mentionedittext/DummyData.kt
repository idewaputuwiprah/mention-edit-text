package com.adit.mentionedittext

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

object DummyData {

    /**
     * To simulate API call, we use suspend function
     * */
    suspend fun getRecommendation(keywords: String): List<Person> {
        return withContext(Dispatchers.IO) {
            delay(1000)

            val persons = listOf(
                Person("Anton"),
                Person("Paulo"),
                Person("Neymar"),
                Person("Jack"),
                Person("Laura"),
                Person("Kate"),
                Person("Wanda")
            )

            persons.filter { it.name.contains(keywords, ignoreCase = true) }
        }
    }
}