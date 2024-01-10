package com.example.chapter14notekeepingapp.data

import android.content.res.Resources

class Note() {
    var id: Int = 0// assign a default value
    var title: String? = "NOTE Dummy"
    var description: String? = "If you're still facing issues," +
            " please provide more details about the error you are encountering" +
            " or share the relevant parts of your code and resource files for further assistance.\n"
    var todo:Boolean=false
    var important:Boolean   = false
    var idea: Boolean= false

    constructor(id:Int, title:String, description:String, todo: Boolean, important:Boolean, idea :Boolean) : this() {
        this.id = id
        this.title = title
        this.description = description
        this.todo = todo
        this.important = important
        this.idea = idea
    }


}