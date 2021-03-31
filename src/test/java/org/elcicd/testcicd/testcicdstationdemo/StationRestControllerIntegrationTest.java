package org.elcicd.testcicd.testcicdstationdemo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.elcicd.testcicd.testcicdstationdemo.station.Station;
import org.elcicd.testcicd.testcicdstationdemo.station.StationRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StationRestControllerIntegrationTest { 

    @Autowired
    private TestRestTemplate testRestTemplate;
 
    @Autowired
    private StationRepository stationRepository;
    
    @Test
    public void givenStation1_thenStatus200() throws Exception {
        Station station1 = stationRepository.findById(1l).get();
        
        ResponseEntity<String> response = testRestTemplate.getForEntity("/stations/1", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        JSONObject json = new JSONObject(response.getBody());
        assertEquals(station1.getName(), json.get("name"));
    }
    
    @Test
    public void givenFindAllByHdEnabledIsTrue_thenStatus200() throws Exception {
        List<Station> stations = stationRepository.findAllByHdEnabledIsTrue();
        
        ResponseEntity<String> response = testRestTemplate.getForEntity("/stations/search/findAllByHdEnabledIsTrue", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        JSONObject json = new JSONObject(response.getBody());
        JSONArray responseArray = json.getJSONObject("_embedded").getJSONArray("stations");
        for (int i = 0; i < responseArray.length(); i++) {
            Station station = stations.get(i);
            JSONObject respObject = responseArray.getJSONObject(i);
            
            assertTrue(respObject.has("_links"));
            assertEquals(station.getId(), respObject.getLong("id"));
            assertEquals(station.getName(), respObject.getString("name"));
            assertEquals(station.isHdEnabled(), respObject.getBoolean("hdEnabled"));
            assertEquals(station.getCallSign(), respObject.getString("callSign"));
        }
    }
    
    @Test
    public void givenCountByHdEnabledIsTrue_thenStatus200() throws Exception {
        long hdCount = stationRepository.countByHdEnabledIsTrue();
        
        ResponseEntity<String> response = testRestTemplate.getForEntity("/stations/search/countByHdEnabledIsTrue", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        assertEquals(hdCount, Long.valueOf(response.getBody()).longValue());
    }
    
    @Test
    public void givenfindByNameStation_thenStatus200() throws Exception {
        Station station1 = stationRepository.findById(2l).get();
        
        ResponseEntity<String> response = testRestTemplate.getForEntity("/stations/search/findByName?name=Test Wheelz 98.7", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        JSONObject json = new JSONObject(response.getBody());
        assertEquals(json.get("name"), station1.getName());
    }
}
