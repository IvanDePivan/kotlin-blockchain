package nl.ivanhorn.kotlinchain


import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets
import java.util.*

data class Block(val data: String, val previousHash: String, val timeStamp: Long = Date().time) {
    var hash:String = calculateHash()
    private var nonce: Int = 0

    fun calculateHash(): String {
        return Hashing.sha256()
                .hashString(previousHash
                        + timeStamp
                        + nonce
                        + data, StandardCharsets.UTF_8
                )
                .toString()
    }

    fun mineBlock(difficulty: Int) {
        val target: String = String().padStart(difficulty, '0')
        while (!hash.startsWith(target)) {
            nonce++
            hash = calculateHash()
        }
    }
}

//TODO: Random nonce per block
//TODO: Allow for larger nonce