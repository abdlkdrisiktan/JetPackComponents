package com.abdlkdr.sqlitesample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdlkdr.sqlitesample.databinding.ActivityMainBinding
import com.abdlkdr.sqlitesample.room.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import android.widget.TextView

class MainActivity : AppCompatActivity(), WordListAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: WordListAdapter

    private val newWordActivityRequestCode = 1
    private val updateWordActivityRequestCode = 2

    companion object {
        const val UPDATE_WORD: String = "update_word_value"
    }

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
        adapter = WordListAdapter(listener = this)
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

    private fun deleteSelectedWord(word: Word) {
        wordViewModel.delete(word)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
                val word = Word(word = it)
                wordViewModel.insert(word)
            }
        } else if (requestCode == updateWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getSerializableExtra(NewWordActivity.WORD_UPDATE_REPLY)?.let {
                wordViewModel.updateData(word = it as Word)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onItemClick(view: View, position: Int, word: Word) {
        val snackBar : Snackbar = Snackbar.make(
            view,
            String.format(getString(R.string.delete_word_snack_bar_title), word.word),
            Snackbar.LENGTH_LONG
        ).setAction(getString(R.string.button_delete)) {
            deleteSelectedWord(word = word)
        }
        val snackBarView : View = snackBar.view
        val textView : TextView = snackBarView.findViewById(R.id.snackbar_text)
        textView.maxLines = 5
        snackBar.show()
    }

    override fun onLongItemClick(view: View, position: Int, word: Word) {
        val intent = Intent(this, NewWordActivity::class.java)
        intent.putExtra(UPDATE_WORD, word)
        startActivityForResult(
            intent,
            updateWordActivityRequestCode
        )
    }

}