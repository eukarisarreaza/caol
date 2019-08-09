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

    @Query("SELECT * FROM CaoUsuario ORDER BY co_usuario ASC ")
    abstract fun getAll(): LiveData<List<CaoUsuario>>

    @Query("SELECT * FROM CaoUsuario WHERE co_usuario = :id")
    abstract fun findById(id: String): LiveData<CaoUsuario>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(items: List<CaoUsuario>)

    @Query("DELETE FROM CaoUsuario")
    abstract fun deleteAll()


    @Query("SELECT * FROM CaoUsuario LEFT JOIN PermissaoSistema ON CaoUsuario.co_usuario=PermissaoSistema.co_usuario WHERE  PermissaoSistema.in_ativo = \"S\" AND (PermissaoSistema.co_tipo_usuario = \"0\" OR PermissaoSistema.co_tipo_usuario = \"1\"  OR PermissaoSistema.co_tipo_usuario = \"2\")")
    abstract fun getAllUserPermissaoSistema(): LiveData<List<CaoUsuario>>

}