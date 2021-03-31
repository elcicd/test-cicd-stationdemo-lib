package org.elcicd.testcicd.testcicdstationdemo.station;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = { @Index(name = "index_name", columnList = "station_name, hd_enabled") })
public class Station {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    @JsonProperty
    private long id;

    @Column(name="station_name", nullable=false, unique=true)
    @JsonProperty
    private String name;

    @Column(name="hd_enabled", nullable=false)
    @JsonProperty
    private boolean hdEnabled;

    @Column(name="call_sign", nullable=false, unique=true)
    @JsonProperty
    private String callSign;

}
