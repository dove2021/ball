package com.tbug.ball.service.model.order;

public class OrderCleanEx extends OrderClean {

	private String title;
	
    private Byte aScore;
	
    private Byte bScore;
    
    private String gameResult;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Byte getaScore() {
		return aScore;
	}

	public void setaScore(Byte aScore) {
		this.aScore = aScore;
	}

	public Byte getbScore() {
		return bScore;
	}

	public void setbScore(Byte bScore) {
		this.bScore = bScore;
	}

	public String getGameResult() {
		return gameResult;
	}

	public void setGameResult(String gameResult) {
		this.gameResult = gameResult;
	}
	
}
