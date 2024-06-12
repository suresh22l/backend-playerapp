package com.example.bcm.code.challenge.repository;

import com.example.bcm.code.challenge.entity.Player;

public class Result{

  private Player player;
  private Long count;


  public Result(Player player, Long count) {
	super();
	this.player = player;
	this.count = count;
  }

  public Player getPlayer() {
	return player;
  }
	
  public void setPlayer(Player player) {
	this.player = player;
  }
	
  public Long getWinCount() {
	return count;
  }
	
  public void setWinCount(Long count) {
	this.count = count;
  }
}