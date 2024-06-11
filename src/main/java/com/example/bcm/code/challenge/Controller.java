package com.example.bcm.code.challenge;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	private static final Logger log = LoggerFactory.getLogger(Controller.class);

	private final PlayerRepo repository;

	Controller(PlayerRepo repository) {
	  this.repository = repository;
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
	List<NamesAndAgeView> getAll(
			@RequestParam(value = "name", defaultValue = "", required = false) String name,
			@RequestParam(value = "age", defaultValue = "", required = false) String age) {
		
		if(name.isEmpty() && age.isEmpty()) {
			return repository.findAllProjectedBy();
		} else if(age.isEmpty()){
			return 	repository.findByName(name);
		} else if(name.isEmpty()) {
			return 	repository.findByAge(Integer.valueOf(age));			
		} else {
			return repository.findByNameAndAge(name, Integer.valueOf(age));
		}
	}	
}