package com.agence.caol.models.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agence.caol.models.CaoUsuario


@Dao
interface CaoUsuarioDao {
    @Query("SELECT count(*) FROM CaoUsuario")
    abstract fun getCount(): Long

    @Query("SELECT * FROM CaoUsuario ORDER BY co_usuario ASC ") // LIMIT (:page - 1) * :limit, :limit
    abstract fun getAll(): LiveData<List<CaoUsuario>> // page: Int, limit: Int

    @Query("SELECT * FROM CaoUsuario WHERE co_usuario = :id")
    abstract fun findById(id: String): LiveData<CaoUsuario>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(items: List<CaoUsuario>)

    @Query("DELETE FROM CaoUsuario")
    abstract fun deleteAll()
}