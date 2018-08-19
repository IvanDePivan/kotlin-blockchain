package nl.ivanhorn.kotlinchain

import com.google.gson.GsonBuilder
import nl.ivanhorn.kotlinchain.transaction.Transaction
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Security

val gson = GsonBuilder().setPrettyPrinting().create()
val blockchain = ArrayList<Block>()
val chainUtil = ChainUtil()
var difficulty = 6

fun main(args: Array<String>) {
    testWallets()
}

fun testWallets() {
    // Set up bouncy castle as security provide
    Security.addProvider(BouncyCastleProvider())

    // Create new wallets
    val walletA = Wallet()
    val walletB = Wallet()

    // Test public and private keys
    println("Private and public keys:")
    println(walletA.keyPair.private)
    println(walletB.keyPair.public)

    // Create a test transaction from wallet A to wallet B
    val transaction = Transaction(walletA.keyPair.public, walletB.keyPair.public, 5F, ArrayList())
    transaction.generateSignature(walletA.keyPair.private)

    // Verify the signature works and verify it from the public key
    println("Signature is verified: ${transaction.verifySignature()}")
}


fun testChain() {
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