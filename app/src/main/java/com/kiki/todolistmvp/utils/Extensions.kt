package com.kiki.todolistmvp.utils

fun String.capitalizeWords(): String {
    return split(" ").joinToString(" ") { it.capitalize() }
}