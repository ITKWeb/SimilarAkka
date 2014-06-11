package com.itkweb.hday.similarakka.messages;

public class WordCount {

	private String word;
	private int count = 0;
	
	public WordCount(String word) {
		this.word = word;
		this.count = 1;
	}

	public WordCount(String word, int count) {
		this.word = word;
		this.count = count;
	}
	
	public WordCount inc() {
		return new WordCount(word, count+1);
	}

	public String getWord() {
		return word;
	}

	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		return word + "["+count+"]";
	}
	
}
