package com.wlc.smartwatch.util

import android.content.Context
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity
import com.permissionx.guolindev.PermissionX

fun View.getIconRotate(){
    val drag = if (rotation == 180f) 0f else 180f
    animate().rotation(drag).interpolator = AccelerateDecelerateInterpolator()
}
fun View.hideKeyboard() {
    val inputMethodManager: InputMethodManager =
        this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.applicationWindowToken, 0)
}

fun FragmentActivity.permissions(
    vararg permissions: String,
    listener: (Boolean, List<String>, List<String>) -> Unit
) {
    val list = mutableListOf<String>()
    permissions.forEach { list.add(it) }
    PermissionX.init(this)
        .permissions(list)
        .request { allGranted, grantedList, deniedList ->
            listener.invoke(allGranted, grantedList, deniedList)
        }
}