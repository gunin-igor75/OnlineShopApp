package com.github.gunin_igor75.onlineshopapp.presentation.account

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class AutoVisualTransformation(
    val mask: String,
    val maskCharInput: Char,
    val enableCursorMove: Boolean
) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val inputText = text.text
        val formattedText = buildAnnotatedString {
            var separatorCount = 0
            var formattedTextLength = 0 // "(11) 1" input = 3, sep = 3, result = 6
            while (formattedTextLength < inputText.length + separatorCount) { // until 6
                if (mask[formattedTextLength++] != maskCharInput) separatorCount++ // '(' != 'X'
            }
            var inputIndex = 0
            mask.take(formattedTextLength).forEach { maskCurrentChar -> // "(XX) XXXXX - XXXX"
                if (maskCurrentChar != maskCharInput) append(maskCurrentChar) // '(', ')', ' '
                else append(inputText[inputIndex++]) // '1','1','1'
            }
        }

        val offsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                // offset = input.count/original at cursor position -- "(11) 1|" offset = 3
                var separatorCount = 0 // "(11) 1" sep = 3
                var transformedCursor = 0 // inputs + separators = formatted.length using offset
                while (transformedCursor < offset + separatorCount) { // "(11) 1" until 6
                    if (mask[transformedCursor++] != maskCharInput) separatorCount++ // '(' != 'X'
                }
                return if (enableCursorMove) transformedCursor // "(11) 1|" offset/input + sep = 6
                else formattedText.length // "(XX) XXXXX - XXXX" return 17
            }

            override fun transformedToOriginal(offset: Int): Int {
                // offset = formatted.count/transformed at cursor position -- "(11) 1|" offset = 6
                return if (enableCursorMove) {
                    val sepCount =
                        mask.take(offset).count { it != maskCharInput } // "() " sep = 3
                    offset - sepCount // originalCursor/inputs "(11) 1|" offset/form.length - sep = 3
                } else inputText.length // "(XX) XXXXX - XXXX" return 11
            }
        }
        return TransformedText(formattedText, offsetTranslator)
    }

    companion object{
        const val MASK = "+7 XXX XXX XX XX"
        const val MASK_CHAR_INPUT = 'X'

    }
}
