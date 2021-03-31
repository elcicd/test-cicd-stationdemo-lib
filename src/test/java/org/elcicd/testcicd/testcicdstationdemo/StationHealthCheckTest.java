package org.elcicd.testcicd.testcicdstationdemo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.elcicd.testcicd.testcicdstationdemo.station.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class StationHealthCheckTest {
    
    @Autowired
    private StationRepository stationRepository;
    
    @Test
    public void testHealth() {
        long total = stationRepository.count();
        long hdCount = stationRepository.countByHdEnabledIsTrue();

        StationHealthCheck stationHealthCheck = new StationHealthCheck(stationRepository);
        Health health = stationHealthCheck.health();

        assertEquals((Long)total, (Long)health.getDetails().get("Total number of stations"));
        assertEquals((Long)hdCount, (Long)health.getDetails().get("HD enabled stations"));
    }

}
