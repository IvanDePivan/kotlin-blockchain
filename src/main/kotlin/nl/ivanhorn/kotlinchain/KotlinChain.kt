package nl.ivanhorn.kotlinchain

import com.google.gson.GsonBuilder

val gson = GsonBuilder().setPrettyPrinting().create()
val blockchain = ArrayList<Block>()
val chainUtil = ChainUtil()
var difficulty = 6

fun main(args: Array<String>) {
    for (i in 1..10) {
        mineNextBlock("RANDOM DATA :D $difficulty")
        println("Blockchain is Valid: " + chainUtil.isValid(blockchain))
    }
}

fun mineNextBlock(data: String) {
    val previousHash: String
    if (blockchain.size == 0) {
        previousHash = "0"
    } else {
        previousHash = blockchain.last().hash
    }
    val block = Block(data, previousHash)

    println("Mining block...")
    block.mineBlock(difficulty)

    blockchain += block
    println(gson.toJson(block))
}