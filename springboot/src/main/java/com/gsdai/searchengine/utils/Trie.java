package com.gsdai.searchengine.utils;

import java.util.*;

public class Trie {

    final int resultSize = 10;
    int count = 0;

    static class Node {
        Map<Character, Node> child;
        boolean isEnd;

        Node() {
            this.child = new HashMap<>(1000);
            isEnd = false;
        }

        void addChild(char c) {
            this.child.put(c, new Node());
        }
    }

    Node root = new Node();

    public void add(String word) {
        Node node = root;
        int len = word.length();
        for (int i = 0; i < len; i++) {
            char c = word.charAt(i);
            if (!node.child.containsKey(c)) {
                node.addChild(c);
                if (i == len - 1) {
                    node.child.get(c).isEnd = true;
                }
            }
            node = node.child.get(c);
        }
    }

    public boolean search(String word) {
        Node node = root;
        int len = word.length();
        for (int i = 0; i < len; i++) {
            char c = word.charAt(i);
            if (!node.child.containsKey(c)) return false;
            node = node.child.get(c);
        }
        return node.isEnd;
    }

    public List<String> getRelatedWords(String word) {
        count = 0;
        List<String> res = new ArrayList<>();
        Node node = root;
        int len = word.length();
        for (int i = 0; i < len; i++) {
            char c = word.charAt(i);
            if (!node.child.containsKey(c)) return new ArrayList<>(0);
            node = node.child.get(c);
        }
        dfs(word, node, res, "");
        return res;
    }

    public void dfs(String word, Node node, List<String> res, String path) {
        if (count >= resultSize) return;
        if (node.isEnd && !word.equals(word + path)) {
            res.add(word + path);
            count++;
        }
        for (Map.Entry<Character, Node> entry : node.child.entrySet()) {
            node = entry.getValue();
            path = path + entry.getKey();
            dfs(word, node, res, path);
            path = path.substring(0, path.length() - 1);
        }
    }
}

