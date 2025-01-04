package com.ved.framework.take

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper

class TakeCameraBackPhoto : ActivityResultContract<Uri, Boolean>() {
    @CallSuper
    override fun createIntent(context: Context, input: Uri): Intent {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, input)
        intent.putExtra("android.intent.extras.LENS_FACING_BACK", 1)
        intent.putExtra("android.intent.extras.CAMERA_FACING", 0)
        intent.putExtra("android.intent.extra.USE_FRONT_CAMERA", false)
        return intent
    }

    override fun getSynchronousResult(
        context: Context,
        input: Uri
    ): SynchronousResult<Boolean>? = null

    @Suppress("AutoBoxing")
    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return resultCode == Activity.RESULT_OK
    }
}