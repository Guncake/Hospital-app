package pl.kgorak.hospitalapp.utils

import java.security.MessageDigest

class HashUtil {
    private val md = MessageDigest.getInstance("SHA-512")

    fun toHash(passwd : String): ByteArray {
        var bytes = passwd.toByteArray()
        return md.digest(bytes)
    }
}