package nl.ivanhorn.kotlinchain

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.*

class ChainUtil {
    private val gson:Gson = GsonBuilder().setPrettyPrinting().create()

    fun isValid(blockchain: ArrayList<Block>): Boolean {
        val hashTarget: String = String().padStart(difficulty, '0')
        var currentBlock: Block
        var previousBlock: Block
        for (i in 1 until blockchain.size) {
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

            //check if hash is solved
            if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
                println("This block hasn't been mined")
                return false
            }
        }

        return true
    }
}