/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package binomi;

/**
 *
 * @author Annika
 */
public class BinomiKeko {

    BinomiNode juuri;
    int koko;

    public BinomiKeko() {
    }

    public BinomiKeko(BinomiNode solmu, int koko) {
        this.koko = koko;
        this.juuri = solmu;
    }

    //Uuden solmun lisääminen tehdään
    //luomalla uusi keko, joka sisältää vain tämän uuden solmun. Tämän jälkeen unionin avulla 
    //yhdistetään se alkuperäiseen kekoon.
    public void insert(int arvo) {
        //luodaan uusi keko
        BinomiKeko keko1 = new BinomiKeko();
        BinomiNode solmu = new BinomiNode(arvo);

        solmu.vanhempi = null;
        solmu.lapsi = null;
        solmu.sisar = null;
        solmu.aste = 0;
        keko1.juuri = solmu;
        BinomiKeko keko2 = union(keko1, this);
        this.juuri = keko2.juuri;
        koko++;
    }

    //etsitään keon pienin arvo
    public BinomiNode etsi_min() {
        //arvolle annetaan mahd suurin arvo
        int arvo = Integer.MAX_VALUE;
        BinomiNode apusolmu = juuri;
        BinomiNode min = null;
        //käydään läpi keko solmu solmulta, ja pidetään yllä keon pienintä arvoa muuttujan
        //min avulla
        while (apusolmu != null) {
            if (apusolmu.arvo < arvo) {
                arvo = apusolmu.arvo;
                min = apusolmu;
            }
            apusolmu = apusolmu.sisar;
        }
        return min;
    }

    //poistetaan pienin arvo keosta 
    public void extract_min() {
        BinomiKeko apukeko = new BinomiKeko();
        BinomiNode min = etsi_min();
        BinomiNode val = min.lapsi;
        BinomiNode nyk = juuri;
        BinomiNode apu = juuri;

        //jos juuri ei ole minimi, etsitään sen sisarista pienintä arvoa
        if (juuri.arvo != min.arvo) {
            while (apu.sisar.arvo != min.arvo) {
                apu = apu.sisar;
            }
            apu = apu.sisar.sisar;
        }
        //jos juuri on pienin arvo, poistetaan se ja tehdään sen sisaresta uusi juuri.
        if (juuri.arvo == min.arvo) {
            juuri = juuri.sisar;
        }

        //tapaus, jossa pienemmällä arvolla on lapsia. järjestetään keko apukeon avulla
        if (min.lapsi != null) {
            /*
             * Vaihdetaan alipuun järjestys
             */
            while (val != null) {
                BinomiNode seuraava = val.sisar;
                val.sisar = apukeko.juuri;
                apukeko.juuri = val;
                val = seuraava;
            }

            BinomiKeko uusi = union(this, apukeko);
            this.juuri = uusi.juuri;
        }
    }

    //luodaan kahden annetun binomisolmun välille vanhempi-lapsu yhteys
    public void link(BinomiNode y, BinomiNode z) {
        y.vanhempi = z;
        y.sisar = z.lapsi;
        z.lapsi = y;
        z.aste++;
    }
    //yhdistetään kaksi kekoa toisiinsa mergeä apuna käyttäen

    public BinomiKeko union(BinomiKeko k1, BinomiKeko k2) {
        BinomiKeko k = new BinomiKeko();
        //valitaan juuri mergen avulla
        k.juuri = merge(k1, k2);
        if (k.juuri == null) {
            return k;
        }
        BinomiNode edellinen = null;
        BinomiNode nykyinen = k.juuri;
        BinomiNode seuraava = nykyinen.sisar;
        //yhdistetään keot tarkistamalla,että keossa ei ole kahta samanasteista puuta.
        while (seuraava != null) {
            if (nykyinen.aste != seuraava.aste || (seuraava.sisar != null && seuraava.sisar.aste == nykyinen.aste)) {
                edellinen = nykyinen;
                nykyinen = seuraava;
            } else {
                if (nykyinen.arvo <= seuraava.arvo) {
                    nykyinen.sisar = seuraava.sisar;
                    link(seuraava, nykyinen);
                } else {
                    if (edellinen == null) {
                        k.juuri = seuraava;
                    } else {
                        edellinen.sisar = seuraava;
                    }
                    link(nykyinen, seuraava);
                    nykyinen = seuraava;
                }
            }
            seuraava = nykyinen.sisar;
        }
        return k;
    }

    private static BinomiNode merge(BinomiKeko keko1, BinomiKeko keko2) {
        //Jos toisen juuri on tyhjä, palautetaan suoraan toinen
        if (keko1.juuri == null) {
            return keko2.juuri;
        } else if (keko2.juuri == null) {
            return keko1.juuri;
        }
        //Kumpikaan juurilista ei siis ole tyhjä. Käydään molemmat läpi käyttäen aina 
        //pienimmän arvon omaavaa solmua.
        BinomiNode juuri;		  
        BinomiNode viimeinen;		  
        BinomiNode seuraava1 = keko1.juuri,
                seuraava2 = keko2.juuri; 
        if (keko1.juuri.arvo <= keko2.juuri.arvo) {
            juuri = keko1.juuri;
            seuraava1 = seuraava1.sisar;
        } else {
            juuri = keko2.juuri;
            seuraava2 = seuraava2.sisar;
        }
        viimeinen = juuri;
        //Käy molemmat juurilistat läpi kunnes toinen loppuu.
        while (seuraava1 != null && seuraava2 != null) {
            if (seuraava1.arvo <= seuraava2.arvo) {
                viimeinen.sisar = seuraava1;
                seuraava1 = seuraava1.sisar;
            } else {
                viimeinen.sisar = seuraava2;
                seuraava2 = seuraava2.sisar;
            }

            viimeinen = viimeinen.sisar;
        }

        //Nyt toinen juurilista on loppunut. Jatketaan jäljellä olevasta juurilistasta loput 
        //listaan.
        if (seuraava1 != null) {
            viimeinen.sisar = seuraava1;
        } else {
            viimeinen.sisar = seuraava2;
        }
        return juuri;

    }

    //tulostetaan puu
    public String toString() {
        String tuloste = "";

        BinomiNode x = juuri;
        while (x != null) {
            tuloste = tuloste + x.tulostaPuu(0);
            x = x.sisar;
        }
        return tuloste;
    }
    //ohjelman main

    public static void main(String[] args) {

        BinomiKeko binomikeko = new BinomiKeko();
        for (int i = 10; i != 0; i--) {
            binomikeko.insert(i);
        }
        binomikeko.extract_min();
        binomikeko.extract_min();
        binomikeko.extract_min();
        System.out.println(binomikeko.toString());
    }
}
