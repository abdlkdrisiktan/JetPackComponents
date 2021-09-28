package com.abdlkdr.sqlitesample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdlkdr.sqlitesample.databinding.ActivityMainBinding
import com.abdlkdr.sqlitesample.room.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: WordListAdapter

    private val newWordActivityRequestCode = 1

    private val database by lazy { WordRoomDatabase.getDatabase(this) }
    private val repository by lazy { WordRepository(database.wordDao()) }
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory(repository = repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        bindView()
        wordViewModel.allWords.observe(this, { words ->
            words?.let { adapter.submitList(words) }
        })
    }

    private fun bindView() {
        val recyclerView: RecyclerView = binding.recyclerview
        adapter = WordListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        val fabButton: FloatingActionButton = binding.fab
        fabButton.setOnClickListener { view ->
            startActivityForResult(
                Intent(this@MainActivity, NewWordActivity::class.java),
                newWordActivityRequestCode
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings ->
                deleteAllWords()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteAllWords(): Boolean {
        wordViewModel.deleteAll()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
                val word = Word(it)
                wordViewModel.insert(word)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

}