package com.merco.dealership.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.merco.dealership.entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {

}
