package com.agence.caol.models.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agence.caol.models.CaoCliente


@Dao
interface CaoClienteDao {
    @Query("SELECT count(*) FROM CaoCliente")
    abstract fun getCount(): Long

    @Query("SELECT * FROM CaoCliente ")
    abstract fun getAll(): LiveData<List<CaoCliente>>

    @Query("SELECT * FROM CaoCliente WHERE co_cliente = :id")
    abstract fun findById(id: String): LiveData<CaoCliente>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(items: List<CaoCliente>)

    @Query("DELETE FROM CaoCliente")
    abstract fun deleteAll()
}