package com.example.realmdatabasedemo.data

import io.realm.RealmModel
import io.realm.annotations.Required

data class Note(
    @Required
    var title: String,
    @Required
    var description: String,
    @Required
    var priority: Int
) : RealmModel