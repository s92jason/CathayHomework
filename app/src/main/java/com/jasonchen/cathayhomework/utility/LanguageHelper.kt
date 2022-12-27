package com.jasonchen.cathayhomework.utility

object LanguageHelper {
    var lang: String = AppConstants.Language.LANG_ZH_TW.lang
    var langNameList: List<String>

    init {
        langNameList = initLangNameList()
    }

    private fun initLangNameList(): List<String> {
        val list = mutableListOf<String>()
        enumValues<AppConstants.Language>().forEach {
            list.add(it.langName)
        }
        return list
    }
}