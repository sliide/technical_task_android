package eu.andreihaiu.task.util.bindings

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import eu.andreihaiu.task.util.extensions.setThrottledClickListener
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("stringBackgroundColor")
fun View.changeViewBackgroundColor(
    stringBackgroundColor: String?
) {
    if (!stringBackgroundColor.isNullOrEmpty()) {
        try {
            background?.run {
                mutate()
                setTint(Color.parseColor(stringBackgroundColor))
            }
        } catch (e: IllegalArgumentException) {
            Timber.e(e)
        }
    }
}

@BindingAdapter("stringImageColor")
fun ImageView.changeImageTint(
    stringImageColor: String?
) {
    if (!stringImageColor.isNullOrEmpty()) {
        try {
            drawable?.run {
                mutate()
                setTint(Color.parseColor(stringImageColor))
            }
        } catch (e: IllegalArgumentException) {
            Timber.e(e)
        }
    }
}

@BindingAdapter("stringTextColor")
fun TextView.changeTextColor(
    stringTextColor: String?
) {
    if (!stringTextColor.isNullOrEmpty()) {
        try {
            setTextColor(Color.parseColor(stringTextColor))
            compoundDrawables.forEachIndexed { ind, drawable ->
                drawable?.run {
                    mutate()
                    setTint(Color.parseColor(stringTextColor))
                }
            }
        } catch (e: IllegalArgumentException) {
            Timber.e(e)
        }
    }
}

@BindingAdapter("textColorResource")
fun AppCompatTextView.changeTextColorResource(
    textColorResource: Int
) {
    setTextColor(textColorResource)
    compoundDrawables.forEachIndexed { _, drawable ->
        drawable?.run {
            mutate()
            setTint(textColorResource)
        }
    }
}

@BindingAdapter("stringToDate")
fun TextView.convertStringToDate(text: String?) {
    text?.split("-")?.let {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(text) ?: return
        val calendar = Calendar.getInstance().apply { time = date }
        this.text = "%s.%s.%s".format(
            calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.YEAR)
        )
    }
}

@BindingAdapter("stringButtonTextColor")
fun AppCompatButton.changeButtonTextColor(
    stringButtonTextColor: String?
) {
    if (!stringButtonTextColor.isNullOrEmpty()) {
        try {
            setTextColor(Color.parseColor(stringButtonTextColor))
        } catch (e: IllegalArgumentException) {
            Timber.e(e)
        }
    }
}

@BindingAdapter("srcCompatDrawable")
fun ImageView.bindSrcCompatDrawable(
    srcCompatDrawable: Drawable
) {
    setImageDrawable(srcCompatDrawable)
}

@BindingAdapter(
    value = ["visibleIf", "keepInvisible"],
    requireAll = false
)
fun View.visibleIf(visibleIf: Boolean?, keepInvisible: Boolean? = false) {
    visibility =
        if (visibleIf == true) View.VISIBLE else (if (keepInvisible == true) View.INVISIBLE else View.GONE)
}

@BindingAdapter("onFocusChangeListener")
fun EditText.setOnFocusChangeListener(callback: () -> Unit) {
    setOnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {
            callback.invoke()
        }
    }
}

@BindingAdapter("throttleClick")
fun View.setThrottledClick(callback: () -> Unit) {
    setThrottledClickListener {
        callback.invoke()
    }
}

@BindingAdapter("showPassword")
fun TextInputEditText.setShowPasswordListener(isShown: Boolean) {
    transformationMethod = if (isShown) {
        null
    } else {
        PasswordTransformationMethod()
    }
}

@BindingAdapter("onError")
fun TextInputLayout.setOnErrorListener(errorResource: Int) {
    val errorMessage = this.context.getString(errorResource)
    if (errorMessage.isEmpty()) {
        isErrorEnabled = false
    } else {
        isErrorEnabled = true
        error = errorMessage
    }
}

@BindingAdapter("hideKeyboard")
fun View.hideKeyboard(isHide: Boolean) {
    if (isHide) {
        (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            windowToken,
            0
        )
    }
}
