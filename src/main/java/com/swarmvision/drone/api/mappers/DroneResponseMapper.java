package com.swarmvision.drone.api.mappers;

import com.swarmvision.drone.api.response.DroneResponse;
import com.swarmvision.drone.domain.Drone;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DroneResponseMapper {
  DroneResponse toResponse(Drone drone);
}
