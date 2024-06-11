package com.example.bcm.code.challenge;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerRepo 
  extends JpaRepository<Player, Long> {

	//List<Player> findByName(String name);
	//List<Player> findByAge(int age);
	//List<Player> findByNameAndAge(String name, int age);
	
	//Page<NamesAndAgeView> findByAge(int age, Pageable pageable);
	//Page<NamesAndAgeView> findByName(String name, Pageable pageable);
	//Page<NamesAndAgeView> findAllProjectedBy(Pageable pageable);
	//Page<NamesAndAgeView> findByNameAndAge(String name, int age, Pageable pageable);

	Page<Player> findByAge(int age, Pageable pageable);
	Page<Player> findByName(String name, Pageable pageable);
	Page<Player> findAll(Pageable pageable);
	Page<Player> findByNameAndAge(String name, int age, Pageable pageable);
}