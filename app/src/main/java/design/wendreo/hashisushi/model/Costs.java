package design.wendreo.hashisushi.model;

import java.io.Serializable;


public class Costs implements Serializable {

    private  String idCustoEntrega;
    private String custoEntrega;

    public Costs() {
    }


    public String getIdCustoEntrega() {
        return idCustoEntrega;
    }

    public void setIdCustoEntrega(String idCustoEntrega) {
        this.idCustoEntrega = idCustoEntrega;
    }

    public String getCustoEntrega() {
        return custoEntrega;
    }

    public void setCustoEntrega(String custoEntrega) {
        this.custoEntrega = custoEntrega;
    }
}
