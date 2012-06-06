/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TiraAnnika;

    /**
 *
 * @author Annika
 */
public class Binomikeko{

    Binominode juuri;
    int koko;

    public Binomikeko() {
    }

    public Binomikeko(Binominode solmu, int koko) {
        this.koko = koko;
        this.juuri = solmu;
    }

    //Uuden solmun lisääminen tehdään
    //luomalla uusi keko, joka sisältää vain tämän uuden solmun. Tämän jälkeen unionin avulla 
    //yhdistetään se alkuperäiseen kekoon.
    public void insert(int arvo) {
        //luodaan uusi keko
        Binomikeko keko1 = new Binomikeko();
        Binominode solmu = new Binominode(arvo);
        
        solmu.vanhempi = null;
        solmu.lapsi = null;
        solmu.sisar = null;
        solmu.aste = 0;
        keko1.juuri = solmu;
        Binomikeko keko2 = union(keko1, this);
        this.juuri = keko2.juuri;
        koko++;
    }

    //etsitään keon pienin arvo
    public Binominode etsi_min() {
        //arvolle annetaan mahd suurin arvo
        int arvo = Integer.MAX_VALUE;
        Binominode apusolmu = juuri;
        Binominode min = null;
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
        Binomikeko apukeko = new Binomikeko();
        Binominode min = etsi_min();
        Binominode val = min.lapsi;
        Binominode nyk = juuri;
        Binominode apu = juuri;

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
                Binominode seuraava = val.sisar;
                val.sisar = apukeko.juuri;
                apukeko.juuri = val;
                val = seuraava;
            }

            Binomikeko uusi = union(this, apukeko);
            this.juuri = uusi.juuri;
        }
    }

  
    //luodaan kahden annetun binomisolmun välille vanhempi-lapsu yhteys
    public void link(Binominode y, Binominode z) {
        y.vanhempi = z;
        y.sisar = z.lapsi;
        z.lapsi = y;
        z.aste++;
    }
    //yhdistetään kaksi kekoa toisiinsa mergeä apuna käyttäen
    public Binomikeko union(Binomikeko k1, Binomikeko k2) {
        Binomikeko k = new Binomikeko();
        //valitaan juuri mergen avulla
        k.juuri = merge(k1, k2);
        if (k.juuri == null) {
            return k;
        }
        Binominode edellinen = null;
        Binominode nykyinen = k.juuri;
        Binominode seuraava = nykyinen.sisar;
        //yhdistetään keot tarkistamalla,että keossa ei ole kahta samanasteista puuta.
        while (seuraava != null) {
            if (nykyinen.aste != seuraava.aste || ((seuraava.sisar != null) && (seuraava.sisar.aste == nykyinen.aste))) {
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
    //yhdistetään kaksi kekoa
    Binominode merge(Binomikeko k1, Binomikeko k2) {
        Binominode solmu1 = null;
        Binominode solmu2 = null;
        /*
         * Tarkastetaan, onko 
         * saaduissa keoissa sisältöä.
         */
        if (k1 != null && k1.juuri != null) {
            solmu1 = k1.juuri;
        }
        if (k2 != null && k2.juuri != null) {
            solmu2 = k2.juuri;
        }
        /*
         * Jos toinen juuri oli null, palautetaan toinen suoraan.
         */
        if (solmu1 == null) {
            return solmu2;
        } else if (solmu2 == null) {
            return solmu1;
        }
        Binominode h;
        if (solmu1.aste < solmu2.aste) {
            h = solmu1;
            solmu1 = solmu1.sisar;
        } else {
            h = solmu2;
            solmu2 = solmu2.sisar;
        }
        /*
         * Kuljetaan kahden listan läpi linkaten nykyinen seuraavaan pienempään
         * solmuun. Muutetaan tämä pienin uudeksi nykyiseksi ja jatketaan kunnes
         * toinen alipuu tyhjä.
         */
        Binominode nykyinen = h;
        while (solmu1 != null && solmu2 != null) {
            if (solmu1.aste < solmu2.aste) {
                nykyinen.sisar = solmu1;
                nykyinen = solmu1;
                solmu1 = solmu1.sisar;
            } else {
                nykyinen.sisar = solmu2;
                nykyinen = solmu2;
                solmu2 = solmu2.sisar;
            }
        }
        if (solmu1 == null) {
            nykyinen.sisar = solmu2;
        } else {
            nykyinen.sisar = solmu1;
        }
        return h;
    }
    //tulostetaan puu
    public String toString() {
        String tuloste = "";

        Binominode x = juuri;
        while (x != null) {
            tuloste = tuloste + x.tulostaPuu(0);
            x = x.sisar;
        }
        return tuloste;
    }
    //ohjelman main
    public static void main(String[] args) {

        Binomikeko binomikeko = new Binomikeko();
        for (int i = 10; i != 0; i--) {
            binomikeko.insert(i);
        }
        binomikeko.extract_min();
        binomikeko.extract_min();
        binomikeko.extract_min();
        System.out.println(binomikeko.toString());
    }
}

    

