package com.adit.mentionedittext

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.mentionedittext.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val mainAdapter by lazy {
        MainAdapter().also {
            it.setOnClickListener { item->
                binding?.edMention?.setMention(item)
                it.setData(listOf())
                Timber.tag("MentionEditTextTag").d("span: ${binding?.edMention?.getMentions().toString()}")
            }
        }
    }
    private var getSuggestionJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initViews()
    }

    private fun initViews() {
        binding?.apply {
            edMention.setOnMentionTriggeredListener { keywords->
                getSuggestionJob?.cancel()
                getSuggestionJob = this@MainActivity.lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        delay(300)
                    }
                    val persons = DummyData.getRecommendation(keywords)

                    mainAdapter.setData(persons)
                }
            }

            rvRecommendation.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                setHasFixedSize(true)
                adapter = mainAdapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        getSuggestionJob?.cancel()
    }
}