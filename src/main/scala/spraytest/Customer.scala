package spraytest

import com.fasterxml.jackson.annotation.JsonProperty

class Customer(
    @JsonProperty("n")
    val name:String, 
    @JsonProperty("a")
    val age:Int,
    //@JsonProperty
    val address:Address)

