/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package binaari;

/**
 *
 * @author Annika
 */
public class BinaariKeko {

    int koko;
    BinaariNode root;

    public BinaariKeko() {
        koko = 0;
    }

/*
 * Lisää kekoon uuden solmun muuttaen sen binäärimuotoon. Jos loppuu 0, lisää vasemmalle, muuten oikealle.
 */
    public void insert(int value) {
        BinaariNode node = new BinaariNode(value);
        koko++;
        if (koko == 1) {
            root = node;
            return;
        }
        BinaariNode apunode = root;
        String binary = Integer.toBinaryString(koko);
        for (int i = 1; i < binary.length() - 1; i++) {
            if (binary.charAt(i) == '0') {
                apunode = apunode.vasen;
            } else {
                apunode = apunode.oikea;
            }
        }
        if (binary.charAt(binary.length() - 1) == '0') {
            apunode.vasen = node;
            node.vanhempi = apunode;
        } else {
            apunode.oikea = node;
            node.vanhempi = apunode;
        }
        heap_up(node);
    }

/*
 * Poistaa keosta solmun. Tarkistetaan lopuksi heap_down():lla keon järjestys. 
 */
    public int delete() {
        if (koko == 1) {
            int apuvalue = root.arvo;
            root = null;
            return apuvalue;
        }
        int palautettava = root.arvo;

        BinaariNode apu = root;
        apu.arvo = root.arvo;
        String binary = Integer.toBinaryString(koko);
        for (int i = 1; i < binary.length(); i++) {
            if (binary.charAt(i) == '0') {
                apu = apu.vasen;
            } else {
                apu = apu.oikea;
            }
        }
        root.arvo = apu.arvo;
        if (binary.charAt(binary.length() - 1) == '0') {
            apu.vanhempi.vasen = null;
        } else {
            apu.vanhempi.oikea = null;
        }

        koko--;
        heap_down();
        return palautettava;

    }

/*
 * Tulostaa keon.
 */
    public void print_heap(BinaariNode root) {
        System.out.println(root.arvo);
        if (root.vasen != null) {
            print_heap(root.vasen);
        }
        if (root.oikea != null) {
            print_heap(root.oikea);
        }
    }

/*
 * Ylöspäin siirto etenee samalla tavalla kuin siirto alaspäin: niin kauan kun solmun avain
 * on vanhempansa avainta pienempi, solmun alkio ja solmun vanhemman alkio vaihtavat
 * keskenään paikkoja.
 */

    private void heap_up(BinaariNode node) {
        if (node == root) {
            return;
        }

        int valuehelper;
        if (node.vanhempi.arvo > node.arvo) {
            valuehelper = node.arvo;
            node.arvo = node.vanhempi.arvo;
            node.vanhempi.arvo = valuehelper;
        } else {
            return;
        }
        heap_up(node.vanhempi);

    }
/*
 * Väärässä paikassa olevan alkion siirto oikealle paikalle.
 * Jos solmun avain on edelleen suurempi kuin vähintään toinen sen lasten avaimista,
 * jatketaan alaspäin siirtoa. Jos alkio on löytänyt oikean paikkansa,
 * lopetetaan alaspäin siirto ja keon järjestys on palautettu.
 */
    private void heap_down() {
        BinaariNode node = root;
        BinaariNode apu;
        int valuehelper;
        while (true) {
            if (node.oikea == null || node.vasen.arvo > node.oikea.arvo) {
                apu = node.oikea;
            } else {
                apu = node.vasen;
            }

            if (apu != null && apu.arvo < node.arvo) {
                valuehelper = node.arvo;
                node.arvo = apu.arvo;
                apu.arvo = valuehelper;
            } else {
                return;
            }
            node = apu;
        }
    }
}