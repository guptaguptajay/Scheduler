package com.example.serviceOperatorScheduler.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest {
    private int time;
    private String customerId;
    private String appointmentId;
}
