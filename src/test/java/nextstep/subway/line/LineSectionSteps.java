package nextstep.subway.line;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.subway.line.dto.LineResponse;
import nextstep.subway.line.dto.SectionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class LineSectionSteps {

    public static ExtractableResponse<Response> 지하철_노선에_구간_등록_요청(SectionRequest sectionRequest, String uri) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(sectionRequest)
                .when()
                .post(uri)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 지하철_노선에_구간이_등록되어_있음(SectionRequest sectionRequest, String uri) {
        return 지하철_노선에_구간_등록_요청(sectionRequest, uri);
    }

    public static void 지하철_노선에_구간_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static void 지하철_노선에_구간_생성_실패됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    public static void 지하철_노선_순서대로_정렬됨(ExtractableResponse<Response> response, List<Long> expectedIds) {
        List<Long> responseIds = response.as(LineResponse.class)
                .getStations()
                .stream()
                .map(it -> it.getId())
                .collect(Collectors.toList());
        assertThat(responseIds).containsExactlyElementsOf(expectedIds);
    }

    public static ExtractableResponse<Response> 지하철_노선의_구간_삭제_요청(String uri) {
        return RestAssured
                .given().log().all()
                .when()
                .delete(uri)
                .then().log().all()
                .extract();
    }

    public static void 지하철_노선_구간_삭제_실패됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    public static void 지하철_노선의_목록_조회됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> 지하철_노선의_목록_조회_요청(String uri) {
        return RestAssured
                .given().log().all()
                .when()
                .get(uri)
                .then().log().all()
                .extract();
    }
}
