package com.swarmvision.drone.api;

import com.swarmvision.drone.api.request.CreateDroneRequest;
import com.swarmvision.drone.api.request.UpdateDroneRequest;
import com.swarmvision.drone.api.response.DroneResponse;
import com.swarmvision.drone.application.DroneService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/drones")
public class DroneController {

  private final DroneService droneService;

  public DroneController(DroneService droneService) {
    this.droneService = droneService;
  }

  @GetMapping("/{id}")
  public DroneResponse getDrone(@PathVariable UUID id) {
    return droneService.getDrone(id);
  }

  @PostMapping()
  public DroneResponse createDrone(@Valid @RequestBody CreateDroneRequest request) {
    return droneService.createDrone(request);
  }

  @PutMapping("/{id}")
  public DroneResponse updateDrone(
      @PathVariable UUID id, @Valid @RequestBody UpdateDroneRequest request) {
    return droneService.updateDrone(id, request);
  }

  @DeleteMapping("/{id}")
  public void deleteDrone(@PathVariable UUID id) {
    droneService.deleteDrone(id);
  }
}
