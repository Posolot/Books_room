package com.example.books.ui

import android.app.Application
import androidx.lifecycle.*
import com.example.books.data.*

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = AppDatabase.getInstance(app).bookDao()

    private val _mode = MutableLiveData(Mode.BY_BOOK)
    private val _spinnerItems = MutableLiveData<List<Any>>()
    val spinnerItems: LiveData<List<Any>> = _spinnerItems

    private val _selectedPos = MutableLiveData<Int>()
    private val _selectedId = MediatorLiveData<Long>().apply {
        addSource(_spinnerItems) { update() }
        addSource(_selectedPos)  { update() }
    }
    private fun MediatorLiveData<Long>.update() {
        val list = _spinnerItems.value ?: return
        val pos  = _selectedPos.value ?: return
        val item = list[pos]
        value = when(item) {
            is Book  -> item.bookId
            is Genre -> item.genreId
            else     -> -1L
        }
    }

    // связанные данные
    val relatedItems: LiveData<List<Any>> = _selectedId.switchMap { id ->
        liveData {
            val list = when (_mode.value) {
                Mode.BY_BOOK  -> dao.getBookWithGenres(id).genres
                Mode.BY_GENRE -> dao.getGenreWithBooks(id).books
                else          -> emptyList()
            }
            emit(list)
        }
    }

    fun setMode(mode: Mode) {
        _mode.value = mode
        _spinnerItems.value = when(mode) {
            Mode.BY_BOOK  -> dao.getAllBooks()
            Mode.BY_GENRE -> dao.getAllGenres()
        }
        _selectedPos.value = 0
    }

    fun onSpinnerSelected(pos: Int) {
        _selectedPos.value = pos
    }
}