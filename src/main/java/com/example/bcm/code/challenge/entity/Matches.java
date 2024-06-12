package com.example.bcm.code.challenge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Matches {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long matchid;

    //Foreign key (Player.ID)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner")
    private Player winner;

    //Foreign key (Player.ID)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loser")    
    private Player loser;

    public Matches() {
    }

    public Matches(Player winner, Player loser) {
        this.setWinner(winner);
        this.setLoser(loser);
    }

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public Player getLoser() {
		return loser;
	}

	public void setLoser(Player loser) {
		this.loser = loser;
	}
	
	@Override
	public String toString() {
	  return "Match [id=" + matchid + ", winner=" + winner.getId() + ", loser=" + loser.getId() + "]";
	}
}