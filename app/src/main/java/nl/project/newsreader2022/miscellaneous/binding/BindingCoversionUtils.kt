package nl.project.newsreader2022.miscellaneous.binding

import androidx.databinding.BindingConversion
import java.time.LocalDateTime

object BindingConversionUtils{
    @BindingConversion
    @JvmStatic
    fun toLocalDateTime(localDateTime: String): LocalDateTime = LocalDateTime.parse(localDateTime)
}
