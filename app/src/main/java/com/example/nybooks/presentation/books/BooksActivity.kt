package com.example.nybooks.presentation.books

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nybooks.R
import com.example.nybooks.presentation.details.BookDetailsActivity
import kotlinx.android.synthetic.main.activity_books.*

class BooksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        setSupportActionBar(toolbarMain)

        val viewModel: BooksViewModel = ViewModelProviders.of(this).get(BooksViewModel::class.java)

        setupObservers(viewModel)
    }

    private fun setupObservers(viewModel: BooksViewModel) {
        viewModel.booksLiveData.observe(this, Observer {
            //Se não for nulo minha lista entra
            it?.let { books -> //minha lista
                with(recyclerBooks) {
                    layoutManager =
                        LinearLayoutManager(this@BooksActivity, RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = BooksAdapter(books) {book ->
                        val intent = BookDetailsActivity.getStartIntent(this@BooksActivity,
                            book.title, book.description)
                        this@BooksActivity.startActivity(intent)
                    }
                }
            }
        })
    }

}
