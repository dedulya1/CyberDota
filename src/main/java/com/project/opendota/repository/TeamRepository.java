package com.project.opendota.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.opendota.entity.Team;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

}
