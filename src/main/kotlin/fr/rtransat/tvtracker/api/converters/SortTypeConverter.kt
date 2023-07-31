package fr.rtransat.tvtracker.api.converters

import fr.rtransat.tvtracker.api.dto.SortType
import org.springframework.core.convert.converter.Converter
import java.util.*

class SortTypeConverter : Converter<String, SortType> {
    override fun convert(source: String): SortType? {
        if (source.isEmpty()) {
            return null
        }
        return SortType.valueOf(source.uppercase(Locale.getDefault()))
    }
}
