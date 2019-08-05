package com.agence.caol.models.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agence.caol.models.CaoOs


@Dao
interface CaoOsDao {
    @Query("SELECT count(*) FROM CaoOs")
    abstract fun getCount(): Long

    @Query("SELECT * FROM CaoOs ") // LIMIT (:page - 1) * :limit, :limit
    abstract fun getAll(): LiveData<List<CaoOs>> // page: Int, limit: Int

    @Query("SELECT * FROM CaoOs WHERE co_os = :id")
    abstract fun findById(id: String): LiveData<CaoOs>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(items: List<CaoOs>)

    @Query("DELETE FROM CaoOs")
    abstract fun deleteAll()
}