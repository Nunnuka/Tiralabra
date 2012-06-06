/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TiraAnnika;

/**
 *
 * @author Annika
 */
public class Binominode {

    int arvo;
    int aste;
    Binominode sisar;
    Binominode vanhempi;
    Binominode lapsi;

    public Binominode() {
    }

    public Binominode(int arvo) {
        this.arvo = arvo;
    }

    public String tulostaPuu(int syvyys) {
        String tulostus = "";

        for (int i = 0; i < syvyys; i++) {
            tulostus = tulostus + "  ";
        }
        tulostus = tulostus + toString() + "\n";

        Binominode a = lapsi;
        while (a != null) {
            tulostus = tulostus + a.tulostaPuu(syvyys + 1);
            a = a.sisar;
        }

        return tulostus;
    }

    public String toString() {
        return ("Arvo: " + arvo + " aste: " + aste);
    }
}

