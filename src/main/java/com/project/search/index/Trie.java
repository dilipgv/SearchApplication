package com.project.search.index;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
class Trie {

  class TrieNode {
    private HashMap<Character, TrieNode> links;
    private boolean isWord;
    private String word;

    public TrieNode() {
      links = new HashMap();
    }

    public boolean containsKey(char ch) {
      return links.containsKey(ch);
    }

    public TrieNode get(char ch) {
      return links.get(ch);
    }

    public TrieNode set(char ch) {
      links.put(ch, new TrieNode());
      return links.get(ch);
    }

    public void setWord(String word) {
      isWord = true;
      this.word = word;
    }

    public boolean isWord() {
      return isWord;
    }
  }

  private TrieNode root;

  /** Initialize your data structure here. */
  public Trie() {
    root = new TrieNode();
  }

  /** Inserts a word into the trie. */
  public void insert(String word) {

    char[] chars = word.toCharArray();

    TrieNode curr = root;
    for (int i = 0; i < chars.length; i++) {
      if (!curr.containsKey(chars[i])) {
        curr.set(chars[i]);
      }
      curr = curr.get(chars[i]);
    }
    curr.setWord(word);
  }

  /** Returns if the word is in the trie. */
  public String[] search(String word) {
    char[] chars = word.toCharArray();

    // get char Node for the first character
    TrieNode curr = root.get(word.charAt(0));
    if (curr == null) return null;

    for (int i = 1; i < chars.length; i++) {
      if (!curr.containsKey(chars[i])) return null;
      curr = curr.get(chars[i]);
    }

    if (curr.isWord()) {
      String[] out = new String[1];
      out[0] = word;
      return out;
    }

    //        NO MATCH -> Check Starts With
    Stack<TrieNode> trieNodeStack = new Stack<>();
    List<String> out = new ArrayList<>();

    if (curr != null) {
      trieNodeStack.addAll(curr.links.values());

      while (!trieNodeStack.isEmpty()) {
        TrieNode node = trieNodeStack.pop();
        // check if this is a word -> add it to match
        if (node.isWord()) out.add(node.word);
        // get links
        for (TrieNode trieNode : node.links.values() ){
          trieNodeStack.push(trieNode);
        }
      }
    }

    return out.toArray(new String[out.size()]);
  }
}
