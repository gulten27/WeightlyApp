package com.gultendogan.weightlyapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [WeightEntity::class],
    version=1,
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun weightDao():WeightDao
}