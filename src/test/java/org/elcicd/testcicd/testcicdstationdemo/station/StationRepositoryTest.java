package org.elcicd.testcicd.testcicdstationdemo.station;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class StationRepositoryTest {
    
    @Autowired
    private StationRepository stationRepository;

    @Test
    public void whenFindByName_thenReturnStation() {
        String name = "Test The WRIF 101";
        Station found = stationRepository.findByName(name);
             
        assertEquals(found.getName(), name);
    }

    @Test
    public void whenCountByHdEnabledIsTrue_thenReturnHdStationCount() {
        long found = stationRepository.countByHdEnabledIsTrue();
             
        assertEquals(4, found);
    }

    @Test
    public void whenFindAllByHdEnabledIsTrue_thenReturnHdStations() {
        List<Station> stations = stationRepository.findAllByHdEnabledIsTrue();
             
        assertEquals(4, stations.size());
        assertArrayEquals(new Object[] {1l, 3l, 5l, 7l}, stations.stream().map(station -> { return station.getId(); }).collect(Collectors.toList()).toArray());
    }

    @Test
    public void whenFindAll_thenReturnAllStations() {
        Long[] ids = {1l, 2l, 3l, 4l, 5l, 6l, 7l};
        String[] names = {"Test The WRIF 101", "Test Wheelz 98.7", "Test WJR Detroit", "Test WJZZ The JAzz", "Test KISS FM", "Test San Marcos Talk", "Test The Croc"};
        Boolean[] hdEnableds = {true, false, true, false, true, false, true};
        String[] callSigns = {"WRIF", "WLLZ", "WJR", "WJZZ", "KISS", "KLBJ", "KROK"};
        
        List<Station> stations =  stationRepository.findAll();
        assertEquals(ids.length, stations.size());
        for (int i = 0; i < stations.size(); i++) {
            assertEquals(ids[i], (Long)stations.get(i).getId());
            assertEquals(names[i], stations.get(i).getName());
            assertEquals(hdEnableds[i], stations.get(i).isHdEnabled());
            assertEquals(callSigns[i], stations.get(i).getCallSign());
        }
    }

    @Test
    public void whenUsingSetters_thenAllValuesCommittedToDb() {
        Station station1 = new Station();
        station1.setId(0);
        station1.setName("Test Name 1");
        station1.setHdEnabled(true);
        station1.setCallSign("Test Call Sign 1");
        stationRepository.save(station1);

        Station station2 = new Station();
        station2.setId(0);
        station2.setName("Test Name 2");
        station2.setCallSign("Test Call Sign 2");
        stationRepository.save(station2);
        
        Station retrievedStation = stationRepository.findByName("Test Name 1");
        assertNotEquals(0l, retrievedStation.getId());
        assertNotEquals("Test Name 1", retrievedStation.getId());
        assertEquals(true, retrievedStation.isHdEnabled());
        assertEquals("Test Call Sign 1", retrievedStation.getCallSign());
        
        
        retrievedStation = stationRepository.findByName("Test Name 2");
        assertNotEquals(0l, retrievedStation.getId());
        assertNotEquals("Test Name 2", retrievedStation.getId());
        assertEquals(false, retrievedStation.isHdEnabled());
        assertEquals("Test Call Sign 2", retrievedStation.getCallSign());
    }

    @Test
    public void whenUsingAllArgsConstructor_thenAllValuesCommittedToDb() {
        Station station1 = new Station(0, "Test Name 1", true, "Test Call Sign 1");
        stationRepository.save(station1);

        Station station2 = new Station(0, "Test Name 2", false, "Test Call Sign 2");
        stationRepository.save(station2);
        
        Station retrievedStation = stationRepository.findByName("Test Name 1");
        assertNotEquals(0l, retrievedStation.getId());
        assertNotEquals("Test Name 1", retrievedStation.getId());
        assertEquals(true, retrievedStation.isHdEnabled());
        assertEquals("Test Call Sign 1", retrievedStation.getCallSign());
        
        
        retrievedStation = stationRepository.findByName("Test Name 2");
        assertNotEquals(0l, retrievedStation.getId());
        assertNotEquals("Test Name 2", retrievedStation.getId());
        assertEquals(false, retrievedStation.isHdEnabled());
        assertEquals("Test Call Sign 2", retrievedStation.getCallSign());
    }

}
