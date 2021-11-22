package nextstep.subway.line.domain;

import nextstep.subway.exception.IllegalStationDistanceException;
import nextstep.subway.station.domain.Station;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id")
    private Line line;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "up_station_id")
    private Station upStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "down_station_id")
    private Station downStation;

    private int distance;

    public static Section merge(Line line, Section upStationSection, Section downStationSection) {
        return new Section(
                line,
                downStationSection.upStation,
                upStationSection.downStation,
                upStationSection.distance + downStationSection.distance);
    }

    public Section(Line line, Station upStation, Station downStation, int distance) {
        this.line = line;
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    protected Section() {

    }

    public boolean isUpStation(Station upStation) {
        return this.upStation == upStation;
    }

    public boolean isDownStation(Station downStation) {
        return this.downStation == downStation;
    }

    public Station getUpStation() {
        return upStation;
    }

    public Station getDownStation() {
        return downStation;
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", line=" + line +
                ", upStation=" + upStation +
                ", downStation=" + downStation +
                ", distance=" + distance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return distance == section.distance && Objects.equals(id, section.id) && Objects.equals(line, section.line) && Objects.equals(upStation, section.upStation) && Objects.equals(downStation, section.downStation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, line, upStation, downStation, distance);
    }

    public void updateUpStation(Station station, int distance) {
        checkDistance(distance);
        this.upStation = station;
        this.distance -= distance;
    }

    public void updateDownStation(Station downStation, int distance) {
        checkDistance(distance);
        this.downStation = downStation;
        this.distance -= distance;
    }

    private void checkDistance(int distance) {
        if (this.distance <= distance) {
            throw new IllegalStationDistanceException();
        }
    }

    public int getDistance() {
        return distance;
    }
}