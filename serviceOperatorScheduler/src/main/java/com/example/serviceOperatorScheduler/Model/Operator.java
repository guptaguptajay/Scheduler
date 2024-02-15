package com.example.serviceOperatorScheduler.Model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operator {
    private String operatorId;
    private String operatorName;
    private List<Appointment> appointments;

}
