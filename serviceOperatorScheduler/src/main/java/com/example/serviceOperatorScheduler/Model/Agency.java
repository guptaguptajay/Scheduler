package com.example.serviceOperatorScheduler.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;


public class Agency {

    static List<Operator> operators;

    public static List<Operator> getOperators() {
        if (CollectionUtils.isEmpty(operators)) {
            operators = List.of(
                    new Operator("1", "Operator 1", List.of()),
                    new Operator("2", "Operator 2", List.of()),
                    new Operator("3", "Operator 3", List.of()),
                    new Operator("4", "Operator 4", List.of())
            );
        }
        return operators;

    }
}
