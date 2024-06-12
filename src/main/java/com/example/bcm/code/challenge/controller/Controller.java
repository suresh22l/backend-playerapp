package com.example.bcm.code.challenge.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bcm.code.challenge.entity.Player;
import com.example.bcm.code.challenge.repository.MatchRepo;
import com.example.bcm.code.challenge.repository.PlayerRepo;
import com.example.bcm.code.challenge.repository.Result;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/*
 * Rest Controller exposing API's for getting 
 * Player details and Game Stats.
 */
@RestController
public class Controller {

	private static final Logger log = LoggerFactory.getLogger(Controller.class);
	private final PlayerRepo repository;
	private final MatchRepo matchRepo;
	
	Controller(PlayerRepo repository, MatchRepo matchRepository) {
	  this.repository = repository;
	  this.matchRepo = matchRepository;
	}

	/**
	 * Get the sorting direction (ASC/DESC). 
	 * Default is ASC
	 * 
	 * @return - Returns the sorting direction
	 */	
	private Sort.Direction getSortDirection(String direction) {
	  if (direction.equals("asc")) {
	    return Sort.Direction.ASC;
	  } else if (direction.equals("desc")) {
	    return Sort.Direction.DESC;
	  }
	  return Sort.Direction.ASC;
	}
	
	
	/**
	 * Returns List of players who
	 * haven't lost a match
	 * @return - Players List
	 */	
	@GetMapping("/getUnbeaten") 
	List<Player> getUnbeaten() { 
		return matchRepo.unbeatenPlayers(); 
	}

	
	/**
	 * Returns Win count for players
	 * in the Match table
	 * 
	 * @return - Players List with count 
	 * of winning games
	 */	
	@GetMapping("/getWinCnt") 
	List<Result> getWinCnt() { 
		return matchRepo.findWinCount(); 
	}

	
	/**
	 * Returns the following from Matches table:
	 * -----------------------
	 * result[0] - list of player ids who have not lost any match 
	 * result[1] - list of player ids who have won exactly one match
	 * result[2] - list of player ids who have won exactly two matches and so on.
	 * 
	 * @return - ArrayList of Players Ids
	 */	
	@GetMapping("/getResults") 
	ArrayList<ArrayList<Long>> getResults() {
		
		List<Player> unbeatenList = matchRepo.unbeatenPlayers();
		ArrayList<Long> aList = new ArrayList<Long>();
		ArrayList<ArrayList<Long>> res = new ArrayList<ArrayList<Long>>();

		// Get the list of player ids who have not lost any match
		for(Player p: unbeatenList) {
			aList.add(p.getId());
		}
		res.add(0, aList);

		// Get the list of player ids who have won one or more matches.
		List<Result> winList = matchRepo.findWinCount();

		for(Result r: winList) {
			// Skip the players who haven't lost any match
			if(res.get(0).contains(r.getPlayer().getId())) {
				continue;
			}
			try {
				res.get(r.getWinCount().intValue()).add(r.getPlayer().getId());
			} catch (IndexOutOfBoundsException e) {
				//populate the ArrayList if empty
				for(int itr=res.size(); itr<=r.getWinCount().intValue(); itr++) {
					aList = new ArrayList<Long>();
					res.add(itr, aList);
				}
				res.get(r.getWinCount().intValue()).add(r.getPlayer().getId());
			}			
		}
		return  res;
	}

	
	/**
	 * API has the following functionality :
	 * -------------------------------------
	 * Display all Players in a table view (Name, Age)
	 * Filter the users by last name and age
	 * Sort by Players or Age
	 *   
	 * @return - ArrayList of Players
	 * 
	 */	
	@GetMapping("/players")
	public ResponseEntity<Map<String, Object>> getAll(
			@RequestParam(defaultValue = "", required = false) String name,
			@RequestParam(defaultValue = "", required = false) String age, 
			@RequestParam(defaultValue = "name,asc", required = false) String[] sort,
			@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "15") int size) {
		
	  try {
        List<Order> orders = new ArrayList<Order>();
		
        if (sort[0].contains(",")) {
            // will sort more than 2 fields
            // sortOrder="field, direction"
            for (String sortOrder : sort) {
              String[] _sort = sortOrder.split(",");
              orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Order(getSortDirection(sort[1]), sort[0]));
        }

        List<Player> players = new ArrayList<Player>();
        Pageable pagingSort = PageRequest.of(page-1, size, Sort.by(orders));
        
        Page<Player> pagePlayers;
		
		if(name.isEmpty() && age.isEmpty()) {
			//no filter; only sort
			pagePlayers = repository.findAll(pagingSort);
		} else if(age.isEmpty()){
			//filter by name and sort
			pagePlayers = repository.findByName(name, pagingSort);
		} else if(name.isEmpty()) {
			//filter by age and sort
			pagePlayers = repository.findByAge(Integer.valueOf(age), pagingSort);			
		} else {
			//filter by both name and age with sort functionality
			pagePlayers = repository.findByNameAndAge(name, Integer.valueOf(age), pagingSort);
		}
		
		players = pagePlayers.getContent();

	    if (players.isEmpty()) {
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

	    Map<String, Object> response = new HashMap<>();
	    response.put("players", players);
	    response.put("currentPage", pagePlayers.getNumber()+1);
	    response.put("totalItems", pagePlayers.getTotalElements());
	    response.put("totalPages", pagePlayers.getTotalPages());

	    return new ResponseEntity<>(response, HttpStatus.OK);
	  } catch (Exception e) {
		  e.printStackTrace();
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}	
}