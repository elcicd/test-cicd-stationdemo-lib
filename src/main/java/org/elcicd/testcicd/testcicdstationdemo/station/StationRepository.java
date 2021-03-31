package org.elcicd.testcicd.testcicdstationdemo.station;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface StationRepository extends JpaRepository<Station, Long> {

    public Station findByName(@Param("name") String name);

    public long countByHdEnabledIsTrue();

    public List<Station> findAllByHdEnabledIsTrue();

}
