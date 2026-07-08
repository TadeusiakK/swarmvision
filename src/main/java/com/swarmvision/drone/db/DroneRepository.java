package com.swarmvision.drone.db;

import com.swarmvision.drone.domain.Drone;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<Drone, UUID> {}
