/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmanarray;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 *
 * @author fehime
 */
public class HuffmanWorker {

    public void olustur(String text) {
        if (text == null || text.length() == 0) {
            return;
        }
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : text.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(l -> l.freq));
        for (var entry : freq.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }
        while (pq.size() != 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            int sum = left.freq + right.freq;
            pq.add(new Node(null, sum, left, right));
        }
        Node root = pq.peek();
        Map<Character, String> huffmanCode = new HashMap<>();
        sifrele(root, "", huffmanCode);
        
        System.out.println("Girilen Metin: -" + text+"-");
        System.out.println("\nKelimedeki Harfler Tablosu:");
        System.out.println("-------------------");
        for (Map.Entry m : huffmanCode.entrySet()) {
            System.out.println(" | "+m.getKey() + " | " + m.getValue());
        }
        System.out.println("-------------------");


        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            sb.append(huffmanCode.get(c));
        }

        System.out.println("Huffman dönüşümü uygulanmış metin: " + sb);
        System.out.print("Huffman çözülmüş metin: ");

        if (yaprakMi(root)) {
            while (root.freq-- > 0) {
                System.out.print(root.ch);
            }
        } else {
            int index = -1;
            while (index < sb.length() - 1) {
                index = sifreCoz(root, index, sb);
            }
        }
        System.out.println("");
    }

    public void sifrele(Node root, String str, Map<Character, String> huffmanCode) {
        if (root == null) {
            return;
        }
        if (yaprakMi(root)) {
            huffmanCode.put(root.ch, str.length() > 0 ? str : "1");
        }
        sifrele(root.left, str + '0', huffmanCode);
        sifrele(root.right, str + '1', huffmanCode);
    }

    public int sifreCoz(Node root, int index, StringBuilder sb) {
        if (root == null) {
            return index;
        }
        if (yaprakMi(root)) {
            System.out.print(root.ch);
            return index;
        }
        index++;
        root = (sb.charAt(index) == '0') ? root.left : root.right;
        index = sifreCoz(root, index, sb);
        return index;
    }

    public boolean yaprakMi(Node root) {
        return root.left == null && root.right == null;
    }

}
