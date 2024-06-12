package com.example.bcm.code.challenge.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bcm.code.challenge.entity.Player;


@Repository
public interface PlayerRepo 
  extends JpaRepository<Player, Long> {

	//Page<NamesAndAgeView> findByAge(int age, Pageable pageable);
	//Page<NamesAndAgeView> findByName(String name, Pageable pageable);
	//Page<NamesAndAgeView> findAllProjectedBy(Pageable pageable);
	//Page<NamesAndAgeView> findByNameAndAge(String name, int age, Pageable pageable);

	Page<Player> findByAge(int age, Pageable pageable);
	Page<Player> findByName(String name, Pageable pageable);
	Page<Player> findAll(Pageable pageable);
	Page<Player> findByNameAndAge(String name, int age, Pageable pageable);
}