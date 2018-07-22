package nl.ivanhorn.kotlinchain


import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets
import java.util.*

data class Block(val data: String, val previousHash: String, val timeStamp: Long = Date().time) {
    val hash:String = calculateHash()

    fun calculateHash(): String {
        return Hashing.sha256()
                .hashString(previousHash + timeStamp.toString() + data, StandardCharsets.UTF_8)
                .toString()
    }
}