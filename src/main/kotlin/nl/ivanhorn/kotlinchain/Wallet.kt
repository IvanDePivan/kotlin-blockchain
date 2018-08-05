package nl.ivanhorn.kotlinchain

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.security.spec.ECGenParameterSpec

class Wallet {
    val keyPair: KeyPair

    init {
        val keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC")
        val random = SecureRandom.getInstance("SHA1PRNG")
        val ecSpec = ECGenParameterSpec("prime192v1")

        // Initialize the key generator and generate the keypair
        keyPairGenerator.initialize(ecSpec, random)
        keyPair = keyPairGenerator.generateKeyPair()
    }
}