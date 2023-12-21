package com.oliveira.maia.app.utils
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oliveira.maia.app.core.data.model.ProductEntity
import java.util.List
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


