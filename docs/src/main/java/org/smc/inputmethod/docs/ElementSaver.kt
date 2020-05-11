package org.smc.inputmethod.docs

import com.google.gson.Gson
import mu.KotlinLogging

private val gson = Gson()
fun printAsJSON(element: Any) {
    println(gson.toJson(element))
}

private val logger = KotlinLogging.logger {}

class ElementSaver(var elements: Sequence<KeyboardElement>) {
    private val keyboard = ParsedKeyboard()
    lateinit var currentRow: ParsedKeyboard.ParsedRow
    fun save(): ParsedKeyboard {
        elements.toList().map {
            if (it is Row) {
                if (this::currentRow.isInitialized) {
                    logger.debug("Saving previous row")
                    keyboard.rows.add(currentRow)
                }
                logger.debug("Creating new row")
                currentRow = ParsedKeyboard.ParsedRow()
            }
            if (it is Key) {
                logger.debug("Adding key")
                currentRow.keys.add(it)
            }
        }
        return keyboard
    }
}
