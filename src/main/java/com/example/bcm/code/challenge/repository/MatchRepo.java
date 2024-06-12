package com.example.bcm.code.challenge.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.bcm.code.challenge.entity.Matches;
import com.example.bcm.code.challenge.entity.Player;


@Repository
public interface MatchRepo 
  extends JpaRepository<Matches, Long> {

	// Get players who haevn't lost a match
	@Query("select distinct winner from Matches where winner not in (Select loser from Matches group by loser)")
	List<Player> unbeatenPlayers();
	
	// Get the winning count of players in Match table	
	@Query("select new com.example.bcm.code.challenge.repository.Result(winner, count(*)) from Matches group by winner")
	public List<Result> findWinCount();
	
}