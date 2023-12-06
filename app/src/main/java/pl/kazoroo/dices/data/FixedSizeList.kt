package pl.kazoroo.dices.data

import androidx.compose.ui.graphics.Color

class FixedSizeList(private val maxSize: Int, initialItems: List<Color>) {
    private val items = mutableListOf<Color>()

    init {
        items.add(initialItems[0])
        items.add(initialItems[1])
    }
    //Nie da się cofać koloru

    fun addItem(item: Color) {
        if (items.size >= maxSize) {
            items.removeAt(0)
        }
        items.add(items.size, item)
    }

    fun getItem(): Color {
        return items[0]
    }
}