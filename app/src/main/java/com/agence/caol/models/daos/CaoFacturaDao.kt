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

    @Query("SELECT * FROM CaoFactura ")
    abstract fun getAll(): LiveData<List<CaoFactura>>

    @Query("SELECT * FROM CaoFactura WHERE co_fatura = :id")
    abstract fun findById(id: String): LiveData<CaoFactura>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(items: List<CaoFactura>)

    @Query("DELETE FROM CaoFactura")
    abstract fun deleteAll()

    @Query("SELECT * FROM CaoFactura LEFT JOIN CaoOs ON CaoFactura.co_os= CaoOs.co_os WHERE CaoOs.co_usuario = :id_user AND CaoFactura.data_emissao LIKE :mes AND CaoFactura.data_emissao LIKE :anio ")
    abstract fun getAllUser(id_user: String, mes: String, anio: String): List<CaoFactura>


}