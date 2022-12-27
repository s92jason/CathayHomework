package com.jasonchen.cathayhomework.utility

import java.text.FieldPosition
import java.util.StringTokenizer

object AppConstants {
    interface URL {
        companion object {
            const val baseUrl = "https://www.travel.taipei/open-api/"
            const val DEF_ATTRACTIONS_PAGE_INIT = 1
        }
    }

    enum class Language(val lang: String, val langName: String) {
        /**正體中文*/
        LANG_ZH_TW("zh-tw", "正體中文"),

        /**簡體中文*/
        LANG_ZH_CN("zh-cn", "簡體中文"),

        /**英文*/
        LANG_ZH_EN("en", "英文"),

        /**日文*/
        LANG_ZH_JA("ja", "日文"),

        /**韓文*/
        LANG_ZH_KO("ko", "韓文"),

        /**西班牙文*/
        LANG_ZH_ES("es", "西班牙文"),

        /**印尼文*/
        LANG_ZH_ID("id", "印尼文"),

        /**泰文*/
        LANG_ZH_TH("th", "泰文"),

        /**越南文*/
        LANG_ZH_VI("vi", "越南文");

        companion object {
            fun getLang(position: Int): String {
                enumValues<Language>().forEach {
                    if (position == it.ordinal) {
                        return it.lang
                    }
                }

                return LANG_ZH_TW.lang
            }
        }
    }

    enum class ListShowType(val index: Int) {
        GRID(0), LIST(1);

        companion object {
            fun getLayoutType(index: Int): ListShowType {
                return when (index) {
                    1 -> LIST
                    else -> GRID
                }
            }
        }
    }
}