package com.cts.rms.service;

import com.cts.rms.model.ReservationTable;
import com.cts.rms.model.User;
import com.cts.rms.repository.TableReservationRepository;
import com.cts.rms.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableReservationService {

    @Autowired
    private TableReservationRepository reservationRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<ReservationTable> getAllReservations() {
        return reservationRepository.findAll();
    }
    public ReservationTable getById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }
    public ReservationTable saveReservation(ReservationTable table) {
    	User user = userRepository.findById(table.getUser().getUserId()).orElse(null);
    	
    	table.setUser(user);
        return reservationRepository.save(table);
    }

    public Boolean deleteReservation(Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ReservationTable updateReservation(ReservationTable updatedTable, Long id) {
    	List<ReservationTable> Tables = reservationRepository.findAll();
        for (ReservationTable u : Tables) {
            if (u.getId().equals(id)) {
              u.setReservationTime(updatedTable.getReservationTime());
              u.setPeopleNumber(updatedTable.getPeopleNumber());
                return reservationRepository.save(u);
            }
        }
        return null;
    		   
    }
}
