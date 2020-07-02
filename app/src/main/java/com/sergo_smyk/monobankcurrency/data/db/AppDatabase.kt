package com.sergo_smyk.monobankcurrency.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sergo_smyk.monobankcurrency.data.db.dao.RatesDao
import com.sergo_smyk.monobankcurrency.data.db.model.RateLocal

@Database(
    entities = [RateLocal::class], version = 1, exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun ratesDao(): RatesDao
}