package me.bdats_proja;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Obec
{
    private SimpleIntegerProperty psc;
    private SimpleStringProperty name;
    private SimpleIntegerProperty muziPocet;
    private SimpleIntegerProperty zenyPocet;
    private SimpleIntegerProperty celkemPocet;

    public Obec(int psc, String name, int muziPocet, int zenyPocet, int celkemPocet) {
        this.psc = new SimpleIntegerProperty(psc);
        this.name = new SimpleStringProperty(name);
        this.muziPocet = new SimpleIntegerProperty(muziPocet);
        this.zenyPocet = new SimpleIntegerProperty(zenyPocet);
        this.celkemPocet = new SimpleIntegerProperty(celkemPocet);
    }

    public int getPsc() { return psc.get(); }
    public String getName() { return name.get(); }
    public int getMuziPocet() { return muziPocet.get(); }
    public int getZenyPocet() { return zenyPocet.get(); }
    public int getCelkemPocet() { return celkemPocet.get(); }
}
