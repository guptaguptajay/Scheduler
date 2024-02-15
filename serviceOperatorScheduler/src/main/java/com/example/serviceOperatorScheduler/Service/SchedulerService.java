package com.example.serviceOperatorScheduler.Service;

import com.example.serviceOperatorScheduler.Model.Appointment;
import com.example.serviceOperatorScheduler.Model.AppointmentRequest;

import java.util.List;

public interface SchedulerService {

    public List<Appointment> getAllAppointments();

    Appointment bookAppointment(AppointmentRequest appointmentRequest);

    List<String> getAvailableSlots();

    List<Appointment> getAllAppointmentsByOperatorId(String operatorId);

    Appointment rescheduleAppointment(AppointmentRequest appointmentRequest);

    void deleteAppointment(String id);
}
