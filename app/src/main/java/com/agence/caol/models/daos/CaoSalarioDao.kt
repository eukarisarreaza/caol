package com.agence.caol.models.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agence.caol.models.CaoSalario


@Dao
interface CaoSalarioDao {
    @Query("SELECT count(*) FROM CaoSalario")
    abstract fun getCount(): Long

    @Query("SELECT * FROM CaoSalario ")
    abstract fun getAll(): LiveData<List<CaoSalario>>

    @Query("SELECT * FROM CaoSalario WHERE co_usuario = :id")
    abstract fun findById(id: String): CaoSalario?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(items: List<CaoSalario>)

    @Query("DELETE FROM CaoSalario")
    abstract fun deleteAll()
}