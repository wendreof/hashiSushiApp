package com.example.hashisushi.model

import java.io.Serializable

class Product : Serializable {

    var description: String? = null
    var idProd: String? = null
    var idInterno: String? = null
    var promotion: Boolean? = null
    var name: String? = null
    var salePrice: String? = null
    var type: String? = null
    var imgUrl: String? = null

    override fun toString(): String {
        return "Product{" +
                "description='" + description + '\''.toString() +
                ", idProd='" + idProd + '\''.toString() +
                ", idInterno='" + idInterno + '\''.toString() +
                ", isPromotion=" + promotion +
                ", name='" + name + '\''.toString() +
                ", salePrice='" + salePrice + '\''.toString() +
                ", type='" + type + '\''.toString() +
                ", imgUrl='" + imgUrl + '\''.toString() +
                '}'.toString()
    }
}
