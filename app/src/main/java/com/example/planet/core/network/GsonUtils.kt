package com.example.planet.core.network

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class GsonUtils {
    companion object {

        fun gsonForDeserialization(): Gson {
            return GsonBuilder()
                .setExclusionStrategies(object : ExclusionStrategy {
                    override fun shouldSkipField(f: FieldAttributes): Boolean {
                        return false
                    }

                    override fun shouldSkipClass(clazz: Class<*>): Boolean {
                        return false
                    }
                })
                .create()
        }
    }
}