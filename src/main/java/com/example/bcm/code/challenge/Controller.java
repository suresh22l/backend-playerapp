package com.example.bcm.code.challenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class Controller {

	private static final Logger log = LoggerFactory.getLogger(Controller.class);

	private final PlayerRepo repository;

	Controller(PlayerRepo repository) {
	  this.repository = repository;
	}

	private Sort.Direction getSortDirection(String direction) {
	  if (direction.equals("asc")) {
	    return Sort.Direction.ASC;
	  } else if (direction.equals("desc")) {
	    return Sort.Direction.DESC;
	  }

	  return Sort.Direction.ASC;
	}
	/*
	 * @GetMapping("/byName") List<NamesAndAgeView> getByName() { return
	 * repository.findByName("name-2766"); }
	 */
	
	/*
	 * @GetMapping("/byNameAndAge") List<Player> getByNameAndAge() { return
	 * repository.findByNameAndAge("name-27085", 40); }
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
			pagePlayers = repository.findAll(pagingSort);
		} else if(age.isEmpty()){
			pagePlayers = repository.findByName(name, pagingSort);
		} else if(name.isEmpty()) {
			pagePlayers = repository.findByAge(Integer.valueOf(age), pagingSort);			
		} else {
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
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}	
}