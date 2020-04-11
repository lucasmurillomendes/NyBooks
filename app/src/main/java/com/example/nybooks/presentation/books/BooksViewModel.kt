package com.example.nybooks.presentation.books

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nybooks.data.ApiService
import com.example.nybooks.data.model.Book
import com.example.nybooks.data.response.BookBodyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksViewModel : ViewModel() {

    val booksLiveData: MutableLiveData<List<Book>> = MutableLiveData()

    init {
        getBooks()
    }

    fun getBooks(){
        ApiService.service.getBooks().enqueue(object: Callback<BookBodyResponse>{
           override fun onResponse(call: Call<BookBodyResponse>,response: Response<BookBodyResponse>) {
               if (response.isSuccessful) {
                   val books: MutableList<Book> = mutableListOf()

                   response.body()?.let { bookBodyResponse ->
                       for (result in bookBodyResponse.bookResults) {
                           val book = result.bookDetailResponses[0].getBookModel()
                           books.add(book)
                       }
                   }
                   booksLiveData.value = books
               }
           }

           override fun onFailure(call: Call<BookBodyResponse>, t: Throwable) {
               Log.e(TAG, t.message.toString())
           }

       })
    }

    companion object{
        val TAG = BooksViewModel::class.java.simpleName
    }

}