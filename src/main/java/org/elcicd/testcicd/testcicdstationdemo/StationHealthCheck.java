package org.elcicd.testcicd.testcicdstationdemo;

import org.elcicd.testcicd.testcicdstationdemo.station.StationRepository;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("StationHealthCheck")
public class StationHealthCheck implements HealthIndicator {

    private StationRepository stationRepository;

    public StationHealthCheck(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public Health health() {
        long count = stationRepository.count();
        long hdEnabled = stationRepository.countByHdEnabledIsTrue();
        return Health.up().withDetail("Total number of stations", count).withDetail("HD enabled stations", hdEnabled).build();
    }

}
