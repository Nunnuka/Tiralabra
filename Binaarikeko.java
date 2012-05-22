/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Annika
 */
public class Binaarikeko {

    int koko;
    Binaarinode root;

    public Binaarikeko() {
        koko = 0;
    }

/*
 * Lis‰‰ kekoon uuden solmun muuttaen sen bin‰‰rimuotoon. Jos loppuu 0, lis‰‰ vasemmalle, muuten oikealle.
 */
    public void insert(int value) {
        Binaarinode node = new Binaarinode(value);
        koko++;
        if (koko == 1) {
            root = node;
            return;
        }
        Binaarinode apunode = root;
        String binary = Integer.toBinaryString(koko);
        for (int i = 1; i < binary.length() - 1; i++) {
            if (binary.charAt(i) == '0') {
                apunode = apunode.left;
            } else {
                apunode = apunode.right;
            }
        }
        if (binary.charAt(binary.length() - 1) == '0') {
            apunode.left = node;
            node.parent = apunode;
        } else {
            apunode.right = node;
            node.parent = apunode;
        }
        heap_up(node);
    }

/*
 * Poistaa keosta solmun. Tarkistetaan lopuksi heap_down():lla keon j‰rjestys. Aikavaativuuden tehostamiseksi
 * k‰ytet‰‰n apumuuttujaa.
 */
    public int delete() {
        if (koko == 1) {
            int apuvalue = root.value;
            root = null;
            return apuvalue;
        }
        int palautettava = root.value;

        Binaarinode apu = root;
        apu.value = root.value;
        String binary = Integer.toBinaryString(koko);
        for (int i = 1; i < binary.length(); i++) {
            if (binary.charAt(i) == '0') {
                apu = apu.left;
            } else {
                apu = apu.right;
            }
        }
        root.value = apu.value;
        if (binary.charAt(binary.length() - 1) == '0') {
            apu.parent.left = null;
        } else {
            apu.parent.right = null;
        }

        koko--;
        heap_down();
        return palautettava;

    }

/*
 * Tulostaa keon.
 */
    public void print_heap(Binaarinode root) {
        System.out.println(root.value);
        if (root.left != null) {
            print_heap(root.left);
        }
        if (root.right != null) {
            print_heap(root.right);
        }
    }

/*
 * Ylˆsp‰in siirto etenee samalla tavalla kuin siirto alasp‰in: niin kauan kun solmun avain
 * on vanhempansa avainta pienempi, solmun alkio ja solmun vanhemman alkio vaihtavat
 * kesken‰‰n paikkoja.
 */

    private void heap_up(Binaarinode node) {
        if (node == root) {
            return;
        }

        int valuehelper;
        if (node.parent.value > node.value) {
            valuehelper = node.value;
            node.value = node.parent.value;
            node.parent.value = valuehelper;
        } else {
            return;
        }
        heap_up(node.parent);

    }
/*
 * V‰‰r‰ss‰ paikassa olevan alkion siirto oikealle paikalle.
 * Jos solmun avain on edelleen suurempi kuin v‰hint‰‰n toinen sen lasten avaimista,
 * jatketaan alasp‰in siirtoa. Jos alkio on lˆyt‰nyt oikean paikkansa,
 * lopetetaan alasp‰in siirto ja keon j‰rjestys on palautettu.
 */
    private void heap_down() {
        Binaarinode node = root;
        Binaarinode apu;
        int valuehelper;
        while (true) {
            if (node.right == null || node.left.value > node.right.value) {
                apu = node.right;
            } else {
                apu = node.left;
            }

            if (apu != null && apu.value < node.value) {
                valuehelper = node.value;
                node.value = apu.value;
                apu.value = valuehelper;
            } else {
                return;
            }
            node = apu;
        }
    }
}