package com.bankapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clerks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Clerk extends Employee {

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    public Clerk(String employeeId, String name, String email, Manager manager) {
        super(employeeId, name, email);
        this.manager = manager;
    }
}
