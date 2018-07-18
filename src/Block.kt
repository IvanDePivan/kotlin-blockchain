import java.util.*

data class Block(val hash: String, val previousHash: String, val data: String, val timeStamp: Long = Date().time)