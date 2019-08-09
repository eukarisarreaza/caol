package com.agence.caol.models.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agence.caol.models.CaoSistema



@Dao
interface CaoSistemaDao {
    @Query("SELECT count(*) FROM CaoSistema")
    abstract fun getCount(): Long

    @Query("SELECT * FROM CaoSistema ")
    abstract fun getAll(): LiveData<List<CaoSistema>>

    @Query("SELECT * FROM CaoSistema WHERE co_sistema = :id")
    abstract fun findById(id: String): LiveData<CaoSistema>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(items: List<CaoSistema>)

    @Query("DELETE FROM CaoSistema")
    abstract fun deleteAll()
}