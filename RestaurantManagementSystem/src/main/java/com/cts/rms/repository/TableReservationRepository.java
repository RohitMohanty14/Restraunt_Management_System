package com.cts.rms.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.rms.model.ReservationTable;

@Repository
public interface TableReservationRepository extends JpaRepository<ReservationTable, Long> {

   
}

