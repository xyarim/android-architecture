package com.xyarim.users.ui.fragment.user_detail

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter

enum class ValidationRule {
    EMPTY, EMAIL
}

fun isValidEmail(email: String?): Boolean {
    if (email != null)
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    return false
}

/**
 * [BindingAdapter]s for the [User] detail page.
 */
@BindingAdapter(value = ["error", "rule"], requireAll = true)
fun errorWatcher(
        textInputLayout: com.google.android.material.textfield.TextInputLayout,
        errorMsg: String,
        rule: ValidationRule
) {
    textInputLayout.editText?.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (rule == ValidationRule.EMPTY && p0.toString().trim().isEmpty()) {
                textInputLayout.error =
                        errorMsg
                textInputLayout.isErrorEnabled = true
            } else if (rule == ValidationRule.EMAIL && !isValidEmail(p0.toString())) {
                textInputLayout.error =
                        errorMsg
                if (!textInputLayout.isErrorEnabled)
                    textInputLayout.isErrorEnabled = true
            } else {
                textInputLayout.isErrorEnabled = false
            }
        }
    })
}
