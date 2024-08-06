package com.grocerydelivery.backend.Repositories;

import com.grocerydelivery.backend.Models.TimeSlotModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlotModel,Long> {
}
