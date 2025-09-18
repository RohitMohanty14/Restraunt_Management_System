package com.cts.rms.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class ReservationTable {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id" , referencedColumnName = "userId")
    private User user;
    @Future(message = "Reservation time must be in the future")
    @NotNull(message = "Reservation time cannot be null")
    private LocalDateTime reservationTime;

    @Min(value = 1, message = "At least 1 person required")
    @Max(value = 20, message = "Maximum 20 people allowed")
    private Integer peopleNumber;
    
    
    public ReservationTable() {
    }

    public ReservationTable(Long id, User user, LocalDateTime reservationTime, Integer peopleNumber) {
        this.id = id;
        this.user = user;
        this.reservationTime = reservationTime;
        this.peopleNumber = peopleNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }
}
