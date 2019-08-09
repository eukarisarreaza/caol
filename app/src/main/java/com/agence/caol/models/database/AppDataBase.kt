package com.agence.caol.models.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.agence.caol.utilities.DATABASE_NAME
import com.agence.caol.models.*
import com.agence.caol.models.daos.*
import com.agence.caol.worker.DatabaseWorker

@Database(entities = [CaoUsuario::class, CaoCliente::class, CaoFactura::class,
                      CaoOs::class, CaoSistema::class, PermissaoSistema::class,
                      CaoSalario::class],
          version = 1,
          exportSchema = false)

abstract class AppDataBase : RoomDatabase(){
    abstract fun caoClienteDao(): CaoClienteDao
    abstract fun caoFacturaDao(): CaoFacturaDao
    abstract fun caoOsDao(): CaoOsDao
    abstract fun caoSistemaDao(): CaoSistemaDao
    abstract fun caoUsuarioDao(): CaoUsuarioDao
    abstract fun permissaoSistemaDao(): PermissaoSistemaDao
    abstract fun caoSalarioDao(): CaoSalarioDao

    companion object{
        @Volatile
        private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDataBase {
            return Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<DatabaseWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)
                    }
                })
                .build()
        }
    }
}