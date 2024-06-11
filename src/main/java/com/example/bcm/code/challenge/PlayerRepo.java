package com.example.bcm.code.challenge;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerRepo 
  extends CrudRepository<Player, Long> {

	//List<Player> findByName(String name);
	//List<Player> findByAge(int age);
	//List<Player> findByNameAndAge(String name, int age);
	
	List<NamesAndAgeView> findByAge(int age);
	List<NamesAndAgeView> findByName(String name);
	List<NamesAndAgeView> findAllProjectedBy();
	List<NamesAndAgeView> findByNameAndAge(String name, int age);
}