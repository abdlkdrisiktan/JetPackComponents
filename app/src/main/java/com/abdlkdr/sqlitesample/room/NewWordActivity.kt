package com.abdlkdr.sqlitesample.room

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.abdlkdr.sqlitesample.MainActivity
import com.abdlkdr.sqlitesample.R

/**
 * Created by aisiktan on 27,September,2021
 */
class NewWordActivity : AppCompatActivity() {
    private lateinit var editWordView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)
        val oldWord : Word = intent.extras?.getSerializable(MainActivity.UPDATE_WORD) as Word
        val isUpdateActivity = oldWord.word.isNotEmpty()
        editWordView = findViewById(R.id.edit_word)

        val button = findViewById<Button>(R.id.button_save)
        if (isUpdateActivity) {
            button.setText(R.string.update_word)
            editWordView.setText(oldWord.word)
        }
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = editWordView.text.toString()
                if (isUpdateActivity) {
                    val newWord = oldWord.copy(word = word)
                    replyIntent.putExtra(WORD_UPDATE_REPLY, newWord)
                } else {
                    replyIntent.putExtra(EXTRA_REPLY, word)
                }
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.abdlkdr.sqlitesample.REPLY"
        const val WORD_UPDATE_REPLY = "com.abdlkdr.sqlitesample.UPDATE"
    }
}