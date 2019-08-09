package com.agence.caol.models.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agence.caol.models.PermissaoSistema



@Dao
interface PermissaoSistemaDao {
    @Query("SELECT count(*) FROM PermissaoSistema")
    abstract fun getCount(): Long

    @Query("SELECT * FROM PermissaoSistema ")
    abstract fun getAll(): LiveData<List<PermissaoSistema>>

    @Query("SELECT * FROM PermissaoSistema WHERE co_sistema = :id")
    abstract fun findById(id: String): LiveData<PermissaoSistema>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(items: List<PermissaoSistema>)

    @Query("DELETE FROM PermissaoSistema")
    abstract fun deleteAll()
}