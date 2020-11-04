package com.example.realmdatabasedemo.data

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.realm.Realm
import io.realm.RealmAsyncTask
import io.realm.RealmResults
import io.realm.Sort
import java.util.*
import kotlin.random.Random

class NoteRepository {


    private var myRealm: Realm = Realm.getDefaultInstance()
    private lateinit var myRealmAsyncTask: RealmAsyncTask
    private val mutableList = MutableLiveData<RealmResults<Notess>>()
    private val mRealmState = MutableLiveData(false)

    //region Network calls
    fun networkRequest() {
        Handler(Looper.getMainLooper()).postDelayed({
            val id = UUID.randomUUID().toString()
            val note1 = Notess(
                "From Network",
                "Fake Network Call${Random.nextInt(10000)}",
                Random.nextInt(10)
            )
            myRealmAsyncTask = myRealm.executeTransactionAsync({ realm ->
                val note = realm.createObject(Notess::class.java, id)
                note.description = note1.description
                note.title = note1.title
                note.priority = note1.priority
            }, {
                Log.d("TAG", "onSuccess: success!")
                mRealmState.postValue(true)
            }
            ) { error ->
                Log.d("TAG", "onError: ${error.message}")
                mRealmState.postValue(false)
            }
        }, 3000)
    }


    //endregion

    fun getNotes(): LiveData<RealmResults<Notess>> {
        val realmResults: RealmResults<Notess> =
            myRealm.where(Notess::class.java).sort("priority", Sort.DESCENDING).findAll()
        mutableList.postValue(realmResults)
        return mutableList
    }

    fun getRealmState(): MutableLiveData<Boolean> {
        return mRealmState
    }

    fun deleteAll(): LiveData<RealmResults<Notess>> {
        myRealm.beginTransaction()
        myRealm.delete(Notess::class.java)
        myRealm.commitTransaction()
        return mutableList
    }


}