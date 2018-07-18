import java.nio.charset.Charset
import java.security.MessageDigest


class HashUtil {
    companion object {
        private val SHA_256: String = "SHA-256"
        private val UTF_8: String = "UTF-8"

        /**
         * creates a sha256 hash given the input
         * @param input the string to hash
         */
        fun sha256(input: String): String {
            try {
                val messageDigest = MessageDigest.getInstance(SHA_256)
                val hash = messageDigest.digest(input.toByteArray(Charset.forName(UTF_8)))

                val hexString = StringBuffer() // This will contain hash as hexidecimal
                for (i in hash.indices) {

                    //TODO: Find out if this works
                    val hex = Integer.toHexString(0xff and hash[i].toInt())
                    if (hex.length == 1) hexString.append('0')
                    hexString.append(hex)
                }
                return hexString.toString()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }
}