package com.example.realmdatabasedemo.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.realmdatabasedemo.data.NoteRepository
import com.example.realmdatabasedemo.data.Notess
import io.realm.RealmResults

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository = NoteRepository()

    fun insert() {
        noteRepository.networkRequest()
    }

    fun getRealmState(): MutableLiveData<Boolean> {
        return noteRepository.getRealmState()
    }

    fun deleteAll(): LiveData<RealmResults<Notess>> {
        return noteRepository.deleteAll()
    }

    fun getAllNotes(): LiveData<RealmResults<Notess>> {
        return noteRepository.getNotes()
    }

}