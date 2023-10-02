package com.example.planet.core.storage.local.room.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
class User (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val idUser: Int = 0,
    @ColumnInfo(name = "name") val nameUser: String,
    @ColumnInfo(name = "lastName") val lastNameUser: String,
    @ColumnInfo(name = "motherLastName") val motherLastNameUser: String,
    @ColumnInfo(name = "birthdayDate") val birthdayDateUser: String,
    @ColumnInfo(name = "country") val countryUser: String
)