package nl.ivanhorn.kotlinchain

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class ChainUtil {
    private val gson:Gson = GsonBuilder().setPrettyPrinting().create()

    fun isValid(blockchain: ArrayList<Block>): Boolean {
        var currentBlock: Block
        var previousBlock: Block
        for (i in 1..blockchain.size) {
            currentBlock = blockchain[i]
            previousBlock = blockchain[i-1]

            // Compare the registered hash and calculated hash
            if(currentBlock.hash != currentBlock.calculateHash()) {
                println("Hash is WRONG of this block :O -> ${gson.toJson(currentBlock)}")
                return false
            }

            // Compare previous hash with registered previous hash
            if(previousBlock.hash != currentBlock.previousHash) {
                println("Previous hash is WRONG of this block :O -> ${gson.toJson(currentBlock)}")
                return false
            }
        }

        return true
    }
}