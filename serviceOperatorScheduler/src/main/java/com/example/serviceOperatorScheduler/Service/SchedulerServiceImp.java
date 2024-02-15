package com.example.serviceOperatorScheduler.Service;

import com.example.serviceOperatorScheduler.Dao.SchedulerDao;
import com.example.serviceOperatorScheduler.Model.Agency;
import com.example.serviceOperatorScheduler.Model.Appointment;
import com.example.serviceOperatorScheduler.Model.AppointmentRequest;
import com.example.serviceOperatorScheduler.Model.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class SchedulerServiceImp  implements SchedulerService {
    @Autowired
    private SchedulerDao schedulerDao;



    @Override
    public List<Appointment> getAllAppointments() {
        return schedulerDao.findAll();
    }

    @Override
    public Appointment bookAppointment(AppointmentRequest appointmentRequest) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(UUID.randomUUID().toString());
        appointment.setCustomerId(appointmentRequest.getCustomerId());
        List<Appointment> appointments =  schedulerDao.findAll();
        if (CollectionUtils.isEmpty(appointments)) {
            appointment.setOperatorId("1");
            appointment.setTime(appointmentRequest.getTime());
            schedulerDao.save(appointment);
            return appointment;
        }
        int availableOperator = getAvailableOperator(appointments, appointmentRequest.getTime());
        if(availableOperator < 4) {
            Operator operator = Agency.getOperators().get(availableOperator);
            appointment.setOperatorId(operator.getOperatorId());
            appointment.setTime(appointmentRequest.getTime());
            schedulerDao.save(appointment);
            return appointment;
        }

        throw new RuntimeException("No available operator for the given time");
    }

    @Override
    public List<String> getAvailableSlots() {
        List<Appointment> appointments =  schedulerDao.findAll();
        int[] myArray = new int[24];
        for (Appointment appointment : appointments) {
            myArray[appointment.getTime()] = myArray[appointment.getTime()] + 1;
        }
        List<String> availableSlots = new ArrayList<>();
        int start =-1;

        for (int i = 0; i < myArray.length; i++) {
            if(myArray[i] < 4) {
                if(start == -1) {
                    start = i;
                }
                if (i == myArray.length -1) {
                    availableSlots.add(start + " - " + i);
                }
            } else {
                if(start != -1 || i == myArray.length -1) {

                    availableSlots.add(start + " - " + i);
                    start = -1;
                }
            }

        }
        return availableSlots;


    }

    @Override
    public List<Appointment> getAllAppointmentsByOperatorId(String operatorId) {
        List<Appointment> appointments =  schedulerDao.findAll();
        List<Appointment> operatorAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if(appointment.getOperatorId().equals(operatorId)) {
                operatorAppointments.add(appointment);
            }
        }
        return operatorAppointments;

    }

    @Override
    public Appointment rescheduleAppointment(AppointmentRequest appointmentRequest) {
        if (!StringUtils.isEmpty(appointmentRequest.getAppointmentId())) {
            schedulerDao.findById(appointmentRequest.getAppointmentId()).ifPresent(appointment -> {
                appointment.setTime(appointmentRequest.getTime());
                schedulerDao.save(appointment);
            });
        }
        throw new RuntimeException("Appointment not found");

    }

    @Override
    public void deleteAppointment(String id) {
        try {
            schedulerDao.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Appointment not found");
        }
    }

    private int getAvailableOperator(List<Appointment> appointments, int time) {
        int count = 0;
        for (Appointment appointment : appointments) {
            if(time == appointment.getTime()) {
                count++;

            }

        }
        return count;
    }
}
