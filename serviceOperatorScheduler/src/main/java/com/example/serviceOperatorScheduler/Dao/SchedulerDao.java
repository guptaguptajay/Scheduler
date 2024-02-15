package com.example.serviceOperatorScheduler.Dao;

import com.example.serviceOperatorScheduler.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulerDao  extends JpaRepository<Appointment, String> {
}
