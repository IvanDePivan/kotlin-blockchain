package nl.ivanhorn.kotlinchain.transaction

import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets
import java.security.Key
import java.security.PublicKey
import java.util.*

data class Transaction(val sender: PublicKey, val recipient: PublicKey, val value: Float, val inputs: ArrayList<TransactionInput>) {
    var transactionId: String = ""
    var signature: ByteArray = ByteArray(0)

    var outputs: ArrayList<TransactionOutput> = ArrayList(0)

    companion object {
        private var sequence: Int = 0
    }

    fun calculateHash(): String {
        sequence++
        return Hashing.sha256().hashString(
                getStringFromKey(sender)
                        + getStringFromKey(recipient)
                        + value
                        + sequence
                , StandardCharsets.UTF_8
        ).toString()
    }

    fun getStringFromKey(key: Key): String {
        return Base64.getEncoder().encodeToString(key.encoded)
    }
}