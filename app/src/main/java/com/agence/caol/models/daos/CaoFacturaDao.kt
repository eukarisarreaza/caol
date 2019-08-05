package com.agence.caol.models.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agence.caol.models.CaoFactura



@Dao
interface CaoFacturaDao {
    @Query("SELECT count(*) FROM CaoFactura")
    abstract fun getCount(): Long

    @Query("SELECT * FROM CaoFactura ") // LIMIT (:page - 1) * :limit, :limit
    abstract fun getAll(): LiveData<List<CaoFactura>> // page: Int, limit: Int

    @Query("SELECT * FROM CaoFactura WHERE co_fatura = :id")
    abstract fun findById(id: String): LiveData<CaoFactura>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(items: List<CaoFactura>)

    @Query("DELETE FROM CaoFactura")
    abstract fun deleteAll()
}