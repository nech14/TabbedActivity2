package com.example.tabbedactivity.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import com.example.tabbedactivity.R

class PageViewModel(application: Application) : AndroidViewModel(application) {
    constructor() : this(Application())
    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = _index.map {
        application.resources.getStringArray(R.array.cities)[it]
    }

    fun getIndex(): MutableLiveData<Int> {
        return _index
    }

    fun setIndex(index: Int) {
        _index.value = index
    }
}