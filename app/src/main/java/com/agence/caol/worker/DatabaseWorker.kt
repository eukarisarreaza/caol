package com.agence.caol.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.agence.caol.models.*
import com.agence.caol.models.database.AppDataBase
import com.agence.caol.utilities.*
import com.cplus.android.utilities.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader

class DatabaseWorker (context: Context, param: WorkerParameters): CoroutineWorker(context, param) {
    private val TAG by lazy { DatabaseWorker::class.java.simpleName }

    override suspend fun doWork(): Result {
        try{

            val userType= object : TypeToken<CaoResponse<CaoUsuario>>(){}.type
            val permissaoSistemaType= object : TypeToken<CaoResponse<PermissaoSistema>>(){}.type
            val facturaType= object : TypeToken<CaoResponse<CaoFactura>>(){}.type
            val clienteType= object : TypeToken<CaoResponse<CaoCliente>>(){}.type
            val sistemaType= object : TypeToken<CaoResponse<CaoSistema>>(){}.type
            val osType= object : TypeToken<CaoResponse<CaoOs>>(){}.type

            val database= AppDataBase.getInstance(applicationContext)

            applicationContext.assets.open(USUARIO_DATA_FILENAME).use { input ->
                JsonReader(input.reader()).use { jsonReader ->
                    val listUsuarios: CaoResponse<CaoUsuario> = Gson().fromJson(jsonReader, userType)
                    Log.e(TAG, "LISTA DE usuarios ${listUsuarios.RECORDS.size}")

                    database.caoUsuarioDao().insertAll(listUsuarios.RECORDS)
                }
            }

            applicationContext.assets.open(PERMISO_SISTEMA_DATA_FILENAME).use { input ->
                JsonReader(input.reader()).use { jsonReader ->
                    val permissaoSistemaList: CaoResponse<PermissaoSistema> = Gson().fromJson(jsonReader, permissaoSistemaType)
                    Log.e(TAG, "LISTA DE permisos ${permissaoSistemaList.RECORDS.size}")

                    database.permissaoSistemaDao().insertAll(permissaoSistemaList.RECORDS)
                }
            }

            applicationContext.assets.open(CAO_FACTURA_DATA_FILENAME).use { input ->
                JsonReader(input.reader()).use { jsonReader ->
                    val facturaList: CaoResponse<CaoFactura> = Gson().fromJson(jsonReader, facturaType)
                    Log.e(TAG, "LISTA DE facturas ${facturaList.RECORDS.size}")

                    database.caoFacturaDao().insertAll(facturaList.RECORDS)

                }
            }

            applicationContext.assets.open(CAO_CLIENTE_DATA_FILENAME).use { input ->
                JsonReader(input.reader()).use { jsonReader ->
                    val clienteList: CaoResponse<CaoCliente> = Gson().fromJson(jsonReader, clienteType)
                    Log.e(TAG, "LISTA DE cliente ${clienteList.RECORDS.size}")

                    database.caoClienteDao().insertAll(clienteList.RECORDS)

                }
            }

            applicationContext.assets.open(CAO_SISTEMA_DATA_FILENAME).use { input ->
                JsonReader(input.reader()).use { jsonReader ->
                    val sistemaList: CaoResponse<CaoSistema> = Gson().fromJson(jsonReader, sistemaType)
                    Log.e(TAG, "LISTA DE sistema ${sistemaList.RECORDS.size}")

                    database.caoSistemaDao().insertAll(sistemaList.RECORDS)
                }
            }

            applicationContext.assets.open(CAO_OS_DATA_FILENAME).use { input ->
                JsonReader(input.reader()).use { jsonReader ->
                    val osList: CaoResponse<CaoOs> = Gson().fromJson(jsonReader, osType)
                    Log.e(TAG, "LISTA de os ${osList.RECORDS.size}")

                    database.caoOsDao().insertAll(osList.RECORDS)

                }
            }

            Utils.setPreferences(applicationContext, KEY_SET_DATA, true)
            return Result.success()

        }catch (e: Exception){
            return Result.failure()
        }
    }
}