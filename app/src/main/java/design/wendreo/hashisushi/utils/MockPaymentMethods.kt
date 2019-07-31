package design.wendreo.hashisushi.utils

import java.util.*

object MockPaymentMethods {

    val paymentMethods: List<String>
        get() {
            val list = ArrayList<String>()
   //         list.add("Selecione uma opção")
            list.add("Dinheiro")
            list.add("MasterCard Crédito")
            list.add("MasterCard Débito")
            list.add("Visa Crédito")
            list.add("Visa Débito")
            list.add("Elo Crédito")
            list.add("Elo Débito")
            return list
        }
    // OLD LIST
    //private val fillPay = arrayOf("Dinheiro",
    // "MasterCard Credito",
    // "Visa Credito",
    // "Aura Credito",
    // "Elo Credito",
    // "Diners Club Credito",
    // "Sorocred Credito",
    // "Hipercard Credito",
    // "MaestroCard Debito",
    // "Visa Eletrônic Debito")
}