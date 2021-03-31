package org.elcicd.testcicd.testcicdstationdemo;

import org.elcicd.testcicd.testcicdstationdemo.station.Station;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;


@SpringBootApplication
public class TestCicdStationdemoApplication implements RepositoryRestConfigurer {

    public static void main(String[] args) {
	    SpringApplication.run(TestCicdStationdemoApplication.class, args);
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Station.class);
    }
}

