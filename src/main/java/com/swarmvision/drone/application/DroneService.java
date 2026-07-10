package com.swarmvision.drone.application;

import com.swarmvision.drone.api.mappers.DroneResponseMapper;
import com.swarmvision.drone.api.request.CreateDroneRequest;
import com.swarmvision.drone.api.request.UpdateDroneRequest;
import com.swarmvision.drone.api.response.DroneResponse;
import com.swarmvision.drone.db.DroneRepository;
import com.swarmvision.drone.domain.Drone;
import com.swarmvision.drone.domain.Status;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DroneService {

  private final DroneRepository droneRepository;
  private final DroneResponseMapper droneResponseMapper;

  public DroneService(DroneRepository droneRepository, DroneResponseMapper droneResponseMapper) {
    this.droneRepository = droneRepository;
    this.droneResponseMapper = droneResponseMapper;
  }

  public List<DroneResponse> getAllDrones() {

    List<Drone> drones = droneRepository.findAll();

    return drones.stream().map(d -> droneResponseMapper.toResponse(d)).toList();
  }

  public DroneResponse getDrone(UUID id) {
    Drone drone =
        droneRepository
            .findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone not found"));
    return droneResponseMapper.toResponse(drone);
  }

  public DroneResponse createDrone(CreateDroneRequest request) {

    Drone drone = new Drone();
    drone.setName(request.name());
    drone.setSize(request.size());
    drone.setColor(request.color());
    drone.setStatus(Status.IDLE);

    Drone savedDrone = droneRepository.save(drone);

    return droneResponseMapper.toResponse(savedDrone);
  }

  public DroneResponse updateDrone(UUID id, UpdateDroneRequest request) {

    Drone drone =
        droneRepository
            .findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone not found"));

    drone.setName(request.name());
    drone.setSize(request.size());
    drone.setColor(request.color());
    drone.setStatus(request.status());

    Drone savedDrone = droneRepository.save(drone);

    return droneResponseMapper.toResponse(savedDrone);
  }

  public void deleteDrone(UUID id) {
    droneRepository.deleteById(id);
  }
}
