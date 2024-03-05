package uz.john.util

//fun Double.roundToOneDecimal(): String {
//    return String.format("%.1f", this)
//}

fun Double.roundToOneDecimal(): Double {
    return (this * 10.0).toInt() / 10.0
}