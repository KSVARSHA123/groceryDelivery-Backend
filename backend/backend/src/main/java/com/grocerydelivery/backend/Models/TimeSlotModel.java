package com.grocerydelivery.backend.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;

@Data
@Table(name = "TIMESLOT")
@Entity
public class TimeSlotModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SLOTID")
    private  Long SLOTID;

    @Column(name = "SLOTNAME")
    private String SLOTNAME;

    @Column(name = "STARTTIME")
    private Time STARTTIME;

    @Column(name = "ENDTIME")
    private Time ENDTIME;
}
