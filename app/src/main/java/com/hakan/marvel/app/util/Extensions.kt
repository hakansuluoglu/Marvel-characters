package com.hakan.marvel.app.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.security.NoSuchAlgorithmException

fun RecyclerView.init(adapter: RecyclerView.Adapter<*>) {
    this.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    this.setHasFixedSize(true)
    this.adapter = adapter
    adapter.notifyDataSetChanged()
}

fun String.md5(): String {
    try {
        val digest = java.security.MessageDigest.getInstance("MD5")
        digest.update(toByteArray())
        val messageDigest = digest.digest()
        val hexString = StringBuilder()
        for (aMessageDigest in messageDigest) {
            var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
            while (h.length < 2)
                h = "0$h"
            hexString.append(h)
        }
        return hexString.toString()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return ""
}