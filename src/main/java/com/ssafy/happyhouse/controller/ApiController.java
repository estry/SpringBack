package com.ssafy.happyhouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ssafy.happyhouse.model.service.APIServiceImpl;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private APIServiceImpl apiService;

	@GetMapping(value = "/news")
	public ResponseEntity<String> news(@RequestParam("search") String search) {

		String clientId = "1hvlMxworvI95lRLb1yZ"; // 애플리케이션 클라이언트 아이디값"
		String clientSecret = "djF2ROg8RE"; // 애플리케이션 클라이언트 시크릿값"

		String text = null;
		text = search;
		try {
			text = URLEncoder.encode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("검색어 인코딩 실패", e);
		}

		String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + text; // json 결과
		// String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text;
		// // xml 결과

		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("X-Naver-Client-Id", clientId);
		requestHeaders.put("X-Naver-Client-Secret", clientSecret);
		String responseBody = get(apiURL, requestHeaders);

//		System.out.println(responseBody);
		return new ResponseEntity<String>(responseBody, HttpStatus.OK);
	}

	@GetMapping(value = "/deal")
	public ResponseEntity<String> aptDeal(@RequestParam Map<String, String> map) throws IOException {
		StringBuilder urlBuilder = new StringBuilder(
				"http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev"); /*
																															 * URL
																															 */
		urlBuilder
				.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + map.get("serviceKey")); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "="
				+ URLEncoder.encode(map.get("serviceKey"), "UTF-8")); /* 공공데이터포털에서 받은 인증키 */
		urlBuilder
				.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode("24", "UTF-8")); /* 한 페이지 결과 수 */
		urlBuilder.append("&" + URLEncoder.encode("LAWD_CD", "UTF-8") + "="
				+ URLEncoder.encode(map.get("LAWD_CD"), "UTF-8")); /* 지역코드 */
		urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD", "UTF-8") + "="
				+ URLEncoder.encode(map.get("DEAL_YMD"), "UTF-8")); /* 계약월 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		String response = xmlToJson(sb.toString());
//		System.out.println(sb.toString());
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@GetMapping("/weather")
	public ResponseEntity<String> weather(@RequestParam Map<String, String> map) throws IOException {
//		map.forEach((k, v) -> System.out.println(k + "," + v));
		Map<String, Integer> result = apiService.getPoint(map.get("lat"), map.get("lng"));
		int x = result.get("x");
		int y = result.get("y");

		int base = Integer.parseInt(map.get("base_time"));
		int cur = Integer.parseInt(map.get("cur_time"));
		if (cur < base)
			cur += 2400;
		int total = ((cur - base) / 100) * 12 + 156;

		StringBuilder urlBuilder = new StringBuilder(
				"http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /* URL */
		urlBuilder
				.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + map.get("serviceKey")); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "="
				+ URLEncoder.encode(map.get("serviceKey"), "UTF-8")); /* 공공데이터포털에서 받은 인증키 */
		urlBuilder
				.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
				+ URLEncoder.encode(Integer.toString(total), "UTF-8")); /* 한 페이지 결과 수 */
		urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "="
				+ URLEncoder.encode("xml", "UTF-8")); /* 요청자료형식(XML/JSON) Default: XML */
		urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "="
				+ URLEncoder.encode(map.get("base_date"), "UTF-8")); /* ‘21년 6월 28일 발표 */
//        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("20211121", "UTF-8")); /*‘21년 6월 28일 발표*/
		urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "="
				+ URLEncoder.encode(map.get("base_time"), "UTF-8")); /* 06시 발표(정시단위) */
//        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("1400", "UTF-8")); /*06시 발표(정시단위) */
//		urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode("55", "UTF-8")); /* 예보지점의 X 좌표값 */
		urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "="
				+ URLEncoder.encode(Integer.toString(x), "UTF-8")); /* 예보지점의 X 좌표값 */
//		urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode("127", "UTF-8")); /* 예보지점의 Y 좌표값 */
		urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "="
				+ URLEncoder.encode(Integer.toString(y), "UTF-8")); /* 예보지점의 Y 좌표값 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		String response = apiService.xmlToJson(sb.toString(), map.get("cur_time"), map.get("base_date"));
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@GetMapping("/chart")
	public ResponseEntity<String> getAptChart(@RequestParam Map<String, String> map) throws IOException {
//		map.forEach((k, v) -> System.out.println(k + "," + v));
//		String serviceKey = "IyWYhBeIPnWXE9tRYNBE5NEy7uKxF11xG3tRl6DaNzRi%2FB0YjoReqhll%2F8Bq6u9OCgEeOwJnWQPxbwDWSg4t7A%3D%3D";

		URL url = new URL(
				"http://api.odcloud.kr/api/15044284/v1/uddi:3d6f4e5a-be0b-4a45-beca-ddec4e2f7d92?"
				+ "page=1&perPage=114&serviceKey=" + map.get("serviceKey"));
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
//		System.out.println(sb.toString());

//		String response = xmlToJson(sb.toString());
		
		String response = apiService.cutChartData(sb.toString(), map.get("location"));
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	private static String get(String apiUrl, Map<String, String> requestHeaders) {
		HttpURLConnection con = connect(apiUrl);
		try {
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-type", "application/json");
			for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
				return readBody(con.getInputStream());
			} else { // 에러 발생
				return readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API 요청과 응답 실패", e);
		} finally {
			con.disconnect();
		}
	}

	private static HttpURLConnection connect(String apiUrl) {
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
		}
	}

	private static String readBody(InputStream body) {
		InputStreamReader streamReader = new InputStreamReader(body);

		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}

			return responseBody.toString();
		} catch (IOException e) {
			throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
		}
	}

	private String xmlToJson(String str) {
		String output = "";
		try {
			String xml = str;
			JSONObject jObj = XML.toJSONObject(xml);
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			Object json = mapper.readValue(jObj.toString(), Object.class);
			output = mapper.writeValueAsString(json);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return output;
	}

}