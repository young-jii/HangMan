package model.ext;

import model.dto.WordDTO;

public class Word extends WordDTO {
	
	public Word(int wordid, String spelling, int wordlen) {
		this.wordId = wordid;
		this.spelling = spelling;
		this.wordLen = wordlen;
	}
}
