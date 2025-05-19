package com.example.books

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.books.data.*
import com.example.books.databinding.ActivityMainBinding
import com.example.books.ui.*
enum class Mode { BY_BOOK, BY_GENRE }

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vm: MainViewModel by viewModels()
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private lateinit var recyclerAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinnerAdapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, mutableListOf())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = spinnerAdapter

        recyclerAdapter = MyAdapter()
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = recyclerAdapter

        vm.spinnerItems.observe(this) { list ->
            spinnerAdapter.clear()
            spinnerAdapter.addAll(list.map {
                when(it) {
                    is Book  -> it.title
                    is Genre -> it.name
                    else     -> it.toString()
                }
            })
        }

        binding.spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, v: View?, pos: Int, id: Long) {
                vm.onSpinnerSelected(pos)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbBooks.id  -> vm.setMode(Mode.BY_BOOK)
                binding.rbGenres.id -> vm.setMode(Mode.BY_GENRE)
            }
        }

        vm.relatedItems.observe(this) { list ->
            recyclerAdapter.submitList(list)
        }

        // стартовый режим
        vm.setMode(Mode.BY_BOOK)
    }
}
