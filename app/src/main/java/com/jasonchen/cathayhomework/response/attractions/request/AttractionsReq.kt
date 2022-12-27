package com.jasonchen.cathayhomework.response.attractions.request

data class AttractionsReq(
    var categoryIds: String? = null,
    var nlat: Double? = null,
    var elong: Double? = null,
    var page: Int
) {
    fun getParams(): Map<String, String> {
        val params = mutableMapOf<String, String>()

        categoryIds?.let { params.setParam("categoryIds", it) }
        nlat?.let { params.setParam("nlat", it.toString()) }
        elong?.let { params.setParam("elong", it.toString()) }
        page.let { params.setParam("page", it.toString()) }

        return params
    }

    private fun MutableMap<String, String>.setParam(paramName: String, param: String) {
        if (paramName.isNotEmpty() && param.isNotEmpty()) {
            this[paramName] = param
        }
    }
}