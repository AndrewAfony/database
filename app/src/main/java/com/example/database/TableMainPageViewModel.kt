package com.example.database

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TableMainPageViewModel : ViewModel() {

    val columns = mutableListOf<ColumnsInfo>()
}