package me.sun.apiserver

import java.io.File

class ResourceUtils {
    companion object {
        fun getFile(name: String) = File(javaClass.getResource(name).toURI())
    }
}
