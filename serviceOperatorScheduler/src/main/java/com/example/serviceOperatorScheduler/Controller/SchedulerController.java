package com.example.serviceOperatorScheduler.Controller;

import com.example.serviceOperatorScheduler.Model.Appointment;
import com.example.serviceOperatorScheduler.Model.AppointmentRequest;
import com.example.serviceOperatorScheduler.Service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SchedulerController {

    @Autowired
    private SchedulerService schedulerService;


    @PostMapping(value = "/bookAppointment")
    public Appointment bookAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        validateAppointmentRequest(appointmentRequest);
        return schedulerService.bookAppointment(appointmentRequest);
    }

    private void validateAppointmentRequest(AppointmentRequest appointmentRequest) {
        if(appointmentRequest == null || StringUtils.isEmpty(appointmentRequest.getCustomerId()) || appointmentRequest.getTime() < 0 || appointmentRequest.getTime() > 23) {
            throw new IllegalArgumentException("Invalid appointment request");
        }
    }

    @GetMapping(value = "/getAllAppointments")
    public List<Appointment> getAllAppointments(@RequestParam(required = false) String operatorId) {
        if(StringUtils.hasText(operatorId)) {
            return schedulerService.getAllAppointmentsByOperatorId(operatorId);
        }
        return schedulerService.getAllAppointments();
    }

    @GetMapping(value = "/getAvailableSlots")
    public List<String> getAvailableSlots() {
        return schedulerService.getAvailableSlots();
    }

    @PutMapping(value = "/rescheduleAppointment")
    public Appointment rescheduleAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        validateAppointmentRequest(appointmentRequest);
        return schedulerService.rescheduleAppointment(appointmentRequest);
    }

    @DeleteMapping(value = "/deleteAppointment/{id}")
    public void deleteAppointment(@PathVariable String id) {
        schedulerService.deleteAppointment(id);
    }



}
