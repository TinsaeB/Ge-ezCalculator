package com.ttbzrs.geezcalculator

object GeezToAmharicMapping {
    private val mapping = mapOf(
        "፩" to "አንድ",
        "፪" to "ሁለት",
        "፫" to "ሶስት",
        "፬" to "አራት",
        "፭" to "አምስት",
        "፮" to "ስድስት",
        "፯" to "ሰባት",
        "፰" to "ስምንት",
        "፱" to "ዘጠኝ",
        "0" to "ዜሮ",
        "+" to "መደመር",
        "-" to " መቀነስ",
        "*" to "ማባዛት",
        "/" to "ማካፈል",
        "=" to "እኩል ነው",
        "AC" to "አጽዳ"
    )

    fun geezToAmharic(geez: String): String {
        return mapping[geez] ?: geez
    }
}