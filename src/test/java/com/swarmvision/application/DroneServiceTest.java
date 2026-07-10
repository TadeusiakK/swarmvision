package com.swarmvision.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.swarmvision.drone.api.mappers.DroneResponseMapper;
import com.swarmvision.drone.api.request.CreateDroneRequest;
import com.swarmvision.drone.api.request.UpdateDroneRequest;
import com.swarmvision.drone.api.response.DroneResponse;
import com.swarmvision.drone.application.DroneService;
import com.swarmvision.drone.db.DroneRepository;
import com.swarmvision.drone.domain.Drone;
import com.swarmvision.drone.domain.Status;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class DroneServiceTest {

  @Mock private DroneRepository droneRepository;
  @Mock private DroneResponseMapper droneResponseMapper;

  @InjectMocks private DroneService droneService;

  @Test
  public void shouldCreateDroneTest() {

    when(droneRepository.save(any(Drone.class))).thenReturn(new Drone());

    ArgumentCaptor<Drone> captor = ArgumentCaptor.forClass(Drone.class);
    CreateDroneRequest request = new CreateDroneRequest("test-drone", 2, "#FF0000");
    droneService.createDrone(request);

    verify(droneRepository).save(captor.capture());
    Drone captured = captor.getValue();

    assertThat(captured.getName()).isEqualTo("test-drone");
    assertThat(captured.getSize()).isEqualTo(2.0);
    assertThat(captured.getColor()).isEqualTo("#FF0000");
    assertThat(captured.getStatus()).isEqualTo(Status.IDLE);
  }

  @Test
  public void shouldGetDroneTest() {

    UUID id = UUID.randomUUID();
    Drone drone = new Drone();
    DroneResponse expected =
        new DroneResponse(id, "test-drone", 2.0, "#FF0000", Status.IDLE, null, null);

    when(droneRepository.findById(id)).thenReturn(Optional.of(drone));
    when(droneResponseMapper.toResponse(drone)).thenReturn(expected);

    DroneResponse result = droneService.getDrone(id);

    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void shouldNotGetDroneTest() {

    UUID id = UUID.randomUUID();
    when(droneRepository.findById(id)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> droneService.getDrone(id)).isInstanceOf(ResponseStatusException.class);
  }

  @Test
  public void shouldGetAllDronesTest() {

    Drone drone1 = new Drone();
    Drone drone2 = new Drone();

    when(droneRepository.findAll()).thenReturn(List.of(drone1, drone2));

    DroneResponse resp1 =
        new DroneResponse(UUID.randomUUID(), "d1", 1.0, "#FF0000", Status.IDLE, null, null);
    DroneResponse resp2 =
        new DroneResponse(UUID.randomUUID(), "d2", 2.0, "#00FF00", Status.ACTIVE, null, null);
    when(droneResponseMapper.toResponse(drone1)).thenReturn(resp1);
    when(droneResponseMapper.toResponse(drone2)).thenReturn(resp2);

    List<DroneResponse> result = droneService.getAllDrones();

    assertThat(result).containsExactly(resp1, resp2);
  }

  @Test
  public void shouldNotGetAllDronesTest() {

    when(droneRepository.findAll()).thenReturn(List.of());
    assertThat(droneService.getAllDrones()).isEmpty();
  }

  @Test
  public void shouldUpdateDroneTest() {

    UUID id = UUID.randomUUID();
    Drone drone = new Drone();
    when(droneRepository.findById(id)).thenReturn(Optional.of(drone));
    when(droneRepository.save(any(Drone.class))).thenReturn(new Drone());

    UpdateDroneRequest request =
        new UpdateDroneRequest("test-drone-updated", 3.0, "#00FF00", Status.ACTIVE);
    droneService.updateDrone(id, request);

    ArgumentCaptor<Drone> captor = ArgumentCaptor.forClass(Drone.class);
    verify(droneRepository).save(captor.capture());
    Drone captured = captor.getValue();

    assertThat(captured.getName()).isEqualTo("test-drone-updated");
    assertThat(captured.getSize()).isEqualTo(3.0);
    assertThat(captured.getColor()).isEqualTo("#00FF00");
    assertThat(captured.getStatus()).isEqualTo(Status.ACTIVE);
  }

  @Test
  void shouldDeleteDrone() {
    UUID id = UUID.randomUUID();

    droneService.deleteDrone(id);

    verify(droneRepository).deleteById(id);
  }
}
