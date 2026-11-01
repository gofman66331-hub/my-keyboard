package com.example.mykeyboard

import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.LinearLayout

class MyKeyboardService : InputMethodService() {

    override fun onCreateInputView(): View {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(0xFF222222.toInt())
        }
        listOf("йцукенгшщзхъ", "фывапролджэ", "ячсмитьбю").forEach { letters ->
            root.addView(row().apply {
                letters.forEach { c ->
                    addView(key(c.toString()) { type(c.toString()) })
                }
            })
        }
        root.addView(row().apply {
            addView(key("⌫") { currentInputConnection?.deleteSurroundingText(1, 0) })
            addView(key("пробел", 4f) { type(" ") })
            addView(key("⏎") { currentInputConnection?.performEditorAction(EditorInfo.IME_ACTION_DONE) })
        })
        return root
    }

    private fun type(text: String) {
        currentInputConnection?.commitText(text, 1)
    }

    private fun row() = LinearLayout(this).apply {
        orientation = LinearLayout.HORIZONTAL
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    private fun key(label: String, weight: Float = 1f, onClick: () -> Unit) =
        Button(this).apply {
            text = label
            isAllCaps = false
            layoutParams = LinearLayout.LayoutParams(0, 150, weight)
            setOnClickListener { onClick() }
        }
}
