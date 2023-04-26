package com.rongtuoyouxuan.chatlive.databus.storage

import android.content.Context
import android.content.SharedPreferences

class Storage {
    private val SP_FILE = "qf_sp"
    private var sp: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var bulkUpdate = false

    companion object {
        val instance: Storage by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED)
        {
            val storage = Storage()
            storage
        }
    }

    public fun init(context:Context) {
        sp = context?.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE)
    }

    operator fun set(key: String, value: String?) {
        doEdit()
        editor!!.putString(key, value)
        doCommit()
    }

    operator fun set(key: String, value: Int) {
        doEdit()
        editor!!.putInt(key, value)
        doCommit()
    }

    operator fun set(key: String, value: Boolean) {
        doEdit()
        editor!!.putBoolean(key, value)
        doCommit()
    }

    operator fun set(key: String, value: Float) {
        doEdit()
        editor!!.putFloat(key, value)
        doCommit()
    }

    fun getString(key: String): String? {
        return sp!!.getString(key, "")
    }

    fun getInt(key: String): Int {
        return sp!!.getInt(key, 0)
    }

    fun getFloat(key: String): Float {
        return sp!!.getFloat(key, 0f)
    }

    fun getBoolean(key: String): Boolean {
        return sp!!.getBoolean(key, false)
    }

    fun edit() {
        bulkUpdate = true
        editor = sp!!.edit()
    }

    fun commit() {
        bulkUpdate = false
        editor!!.apply()
        editor = null
    }

    private fun doEdit() {
        if (!bulkUpdate && editor == null) {
            editor = sp!!.edit()
        }
    }

    private fun doCommit() {
        if (!bulkUpdate && editor != null) {
            editor!!.commit()
            editor = null
        }
    }
}