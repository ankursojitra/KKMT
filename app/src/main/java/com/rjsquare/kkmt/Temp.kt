package com.rjsquare.kkmt

import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class Temp {
    companion object {
        fun getEncryption(toConverterStr: String): Triple<ByteArray, ByteArray, ByteArray> {
            val random = SecureRandom()
            val salt = ByteArray(16)
            random.nextBytes(salt)
            /**Constructor that takes a password, salt, iteration count, and to-be-derived key length for generating PBEKey of variable-key-size PBE ciphers.**/
            val pbKeySpec =
//            PBEKeySpec(CommonVariable.Keys.PWD.toCharArray(), salt, 1024, 128) /** Key Format***/
                PBEKeySpec("ABCD".toCharArray(), salt, 1024, 128)

            /** Key Format***/
            val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")

            /** Algorithm/Hashing **/
            val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded

            /***Encoded Key**/
            val keySpec = SecretKeySpec(keyBytes, "AES")

            /***Cipher Algorithm***/

            val ivRandom = SecureRandom()
            val iv = ByteArray(16)
            ivRandom.nextBytes(iv)
            val ivSpec =
                IvParameterSpec(iv) // 2 /***Creates an IvParameterSpec object using the bytes in iv as the IV.****/

            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            /**Addl par source of randomness**/
            return Triple(cipher.doFinal(toConverterStr.toByteArray()), iv, salt)
        }

        fun doDecrypt(encrypted: ByteArray, iv: ByteArray, salt: ByteArray): String {
            val pbKeySpec = PBEKeySpec("ABCD".toCharArray(), salt, 1024, 128)
            val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
            val keySpec = SecretKeySpec(keyBytes, "AES")

            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val ivSpec = IvParameterSpec(iv)
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            val decrypted = cipher.doFinal(encrypted)
            return String(decrypted)
        }

    }
}