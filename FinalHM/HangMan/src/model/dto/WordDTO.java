package model.dto;

public class WordDTO {

	protected int wordId;
	protected String spelling;
	protected int wordLen;
	
	public int getWordId() {
		return wordId;
	}
	public void setWordId(int wordId) {
		this.wordId = wordId;
	}
	public String getSpelling() {
		return spelling;
	}
	public void setSpelling(String spelling) {
		this.spelling = spelling;
	}
	public int getWordLen() {
		return wordLen;
	}
	public void setWordLen(int wordLen) {
		this.wordLen = wordLen;
	}
}
