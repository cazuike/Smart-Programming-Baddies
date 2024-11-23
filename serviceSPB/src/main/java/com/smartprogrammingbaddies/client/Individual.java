package com.smartprogrammingbaddies.client;

import com.smartprogrammingbaddies.Donations;
import com.smartprogrammingbaddies.event.Event;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Individual implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "individual", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Donations> donations = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "individual_events",
            joinColumns = @JoinColumn(name = "individual_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<Event> events = new HashSet<>();

    protected Individual() {
        // Default constructor for JPA
    }

    public Individual(String name, String email, Client client) {
        this.name = name;
        this.email = email;
        this.client = client;
    }

    // Getters and setters omitted for brevity

    // Helper methods to manage bidirectional relationships
    public void addDonation(Donations donation) {
        donations.add(donation);
        donation.setIndividual(this);
    }

    public void removeDonation(Donations donation) {
        donations.remove(donation);
        donation.setIndividual(null);
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void removeEvent(Event event) {
        events.remove(event);
    }
}
