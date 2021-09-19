package com.theguardian.entity.roommodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.theguardian.entity.dto.Fields

@TypeConverters(FieldsTypeConverter::class)
@Entity
data class FavoriteArticle(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    var id: String,
    @ColumnInfo(name = "fields")
    var fields: Fields?,
    @ColumnInfo(name = "webTitle")
    var webTitle: String?,
    @ColumnInfo(name = "type")
    var type: String?,
)
