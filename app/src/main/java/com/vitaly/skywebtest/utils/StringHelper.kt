package com.vitaly.skywebtest.utils

fun stringBuilder(arrayString: Array<String> ) : String =
    StringBuilder()
        .append("Город: ")
        .append(arrayString[0])
        .append("  Температура: ")
        .append(arrayString[1])
        .append(" C")
        .append("  Облачность: ")
        .append(arrayString[2])
        .append("  Влажность: ")
        .append(arrayString[3])
        .append(" %")
        .toString()


