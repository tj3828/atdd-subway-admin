package nextstep.subway.line.domain;

import static nextstep.subway.station.domain.StationTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SectionTest {

    @DisplayName("두개의 역으로 지하철 구간을 생성할 수 있다")
    @Test
    void 두개의_역으로_지하철_구간을_생성할_수_있다() {
        //given
        final Station upStation = 지하철역_생성_및_검증("강남");
        final Station downStation = 지하철역_생성_및_검증("삼성");

        //when
        final Section section = 지하철_구간_생성(upStation, downStation);

        //then
        지하철_구간_생성됨(section, upStation, downStation);
    }

    public static Section 지하철_구간_생성(final Station upStation, final Station downStation) {
        return new Section(upStation, downStation);
    }

    public static void 지하철_구간_생성됨(final Section section, final Station upStation, final Station downStation) {
        assertAll(
            () -> assertThat(section).isNotNull(),
            () -> assertThat(section.getUpStation()).isEqualTo(upStation),
            () -> assertThat(section.getDownStation()).isEqualTo(downStation)
        );
    }

    @DisplayName("같은 역으로 지하철 구간을 생성할 수 없다")
    @Test
    void 같은_역으로_지하철_구간을_생성할_수_없다() {
        final Station upStation = 지하철역_생성_및_검증("강남");
        final Station downStation = 지하철역_생성_및_검증("삼성");

        중복된_역으로_지하철_구간_생성할_수_없음(upStation, downStation);
    }

    public static void 중복된_역으로_지하철_구간_생성할_수_없음(final Station upStation, final Station downStation) {
        assertAll(
            () -> assertThatIllegalArgumentException().isThrownBy(() -> 지하철_구간_생성(upStation, upStation)),
            () -> assertThatIllegalArgumentException().isThrownBy(() -> 지하철_구간_생성(downStation, downStation))
        );
    }
}