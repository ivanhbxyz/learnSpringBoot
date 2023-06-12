package com.ivanhb.contractorman.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="client")
public class Client {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String email;


    public Client(String name, String email) {
        setName(name);
        setEmail(email);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

	public String getId() {
		
		return id.toString();
	}



    
    
}
