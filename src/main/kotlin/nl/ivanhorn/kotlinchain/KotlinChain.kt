package nl.ivanhorn.kotlinchain

import com.google.gson.GsonBuilder

fun main(args: Array<String>) {

    val blockchain = ArrayList<Block>()
    blockchain += Block("Super real data hash yo!", "0")
    blockchain += Block("Some more real data", blockchain.last().hash)
    blockchain += Block("Third block ever", blockchain.last().hash)

    println(GsonBuilder().setPrettyPrinting().create().toJson(blockchain))
}