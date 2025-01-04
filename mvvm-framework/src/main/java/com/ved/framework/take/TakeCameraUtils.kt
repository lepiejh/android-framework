package com.ved.framework.take

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File

class TakeCameraUtils private constructor(){
    lateinit var file: File

    companion object{
        fun getInstance() = Inner().instance

        class Inner{
            val instance: TakeCameraUtils by lazy { TakeCameraUtils() }
        }
    }

    fun getTakeCameraPhoto(context: AppCompatActivity, result: (file: File) -> Unit) : ActivityResultLauncher<Any>?{
        context.registerForActivityResult(TakeCameraBackPhoto()) {
            if (it) {
                result.invoke(file)
                return@registerForActivityResult
            }
        }
        return null
    }

    fun openCamera(context: Context, takeCameraPhoto: ActivityResultLauncher<Any>?) {
        file = File(
            context.externalCacheDir, "${System.currentTimeMillis()}.jpg"
        )
        takeCameraPhoto?.launch(FileProvider.getUriForFile(context, "${context.packageName}.utilcode.fileprovider", file.apply {
            parentFile?.mkdirs()
            createNewFile()
        }))
    }
}