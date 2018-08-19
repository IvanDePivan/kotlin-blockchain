package nl.ivanhorn.kotlinchain.transaction

import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets
import java.security.Key
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature
import java.util.*

data class Transaction(val sender: PublicKey, val recipient: PublicKey, val value: Float, val inputs: ArrayList<TransactionInput>) {
    var transactionId: String = ""
    var signature: ByteArray = ByteArray(0)

    var outputs: ArrayList<TransactionOutput> = ArrayList(0)

    companion object {
        private var sequence: Int = 0
        const val ALGORITHM_ECDSA = "ECDSA"
        const val PROVIDER_BC = "BC"
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

    fun generateSignature(privateKey: PrivateKey) {
        signature = applyECDSASig(privateKey, getData())
    }

    fun verifySignature(): Boolean {
        return verifyECDSASig(sender, getData(), signature)
    }

    private fun getData(): String {
        return getStringFromKey(sender) + getStringFromKey(recipient) + value
    }

    private fun applyECDSASig(privateKey: PrivateKey, input: String): ByteArray {
        try {
            val dsa: Signature = Signature.getInstance(ALGORITHM_ECDSA, PROVIDER_BC)
            val strByte = input.toByteArray()

            dsa.initSign(privateKey)
            dsa.update(strByte)

            return dsa.sign()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun verifyECDSASig(publicKey: PublicKey, data: String, signature: ByteArray): Boolean {
        try {
            val ecdsaVerify = Signature.getInstance(ALGORITHM_ECDSA, PROVIDER_BC)
            ecdsaVerify.initVerify(publicKey)
            ecdsaVerify.update(data.toByteArray())
            return ecdsaVerify.verify(signature)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}