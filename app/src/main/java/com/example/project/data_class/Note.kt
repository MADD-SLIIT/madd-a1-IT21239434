package com.example.project.data_class

class Note {
    var description:String ?= null
    var id:String ?= null
    constructor()

    constructor( desc:String, id:String){
        this.description = desc
        this.id = id
    }
}

