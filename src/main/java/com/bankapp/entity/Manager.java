package com.bankapp.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "managers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Manager extends Employee {

	@OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
	private List<Clerk> team = new ArrayList<>();

	public Manager(String employeeId, String name, String email) {
		super(employeeId, name, email);
	}

	public void addClerk(Clerk clerk) {
		team.add(clerk);
		clerk.setManager(this);
	}
}
