package com.zenika

data class Field(val name: String,
                 val type: String,
                 val validators: List<Validator>) {


    data class Validator(
            val name: String,
            val args: List<Arg>) {

        data class Arg(val name: String,
                       val value: String)
    }


}


