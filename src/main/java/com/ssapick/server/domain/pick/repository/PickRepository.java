package com.ssapick.server.domain.pick.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssapick.server.domain.pick.entity.Pick;

public interface PickRepository extends JpaRepository<Pick, Long> {


}
