package com.cts.rms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cts.rms.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
