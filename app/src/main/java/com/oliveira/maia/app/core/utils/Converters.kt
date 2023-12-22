package com.oliveira.maia.app.core.utils
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oliveira.maia.app.core.domain.model.ProductEntity
import kotlin.jvm.JvmStatic

class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromList(list: List<ProductEntity>): String {
            val gson = Gson()
            return gson.toJson(list)
        }

        @TypeConverter
        @JvmStatic
        fun toList(value: String): List<ProductEntity> {
            val listType = object : TypeToken<List<ProductEntity>>() {}.type
            return Gson().fromJson(value, listType)
        }
    }
}


