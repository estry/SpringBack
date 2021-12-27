package com.ssafy.happyhouse.model.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class APIServiceImpl {
	private final int NX = 149;
	private final int NY = 253;

	private int x, y;

	@Autowired
	private SqlSession sqlSession;

	public Map<String, Integer> getPoint(String la, String ln) {
		LamcParameter map = new LamcParameter();
		double lat, lng;
		lat = Double.parseDouble(la);
		lng = Double.parseDouble(ln);

		map.Re = 6371.00877; // 지도반경
		map.grid = 5.0; // 격자간격 (km)
		map.slat1 = 30.0; // 표준위도 1
		map.slat2 = 60.0; // 표준위도 2
		map.olon = 126.0; // 기준점 경도
		map.olat = 38.0; // 기준점 위도
		map.xo = 210 / map.grid; // 기준점 X좌표
		map.yo = 675 / map.grid; // 기준점 Y좌표
		map.first = 0;

		mapConv(lat, lng, map);

		Map<String, Integer> mapper = new HashMap<String, Integer>();
		mapper.put("x", x);
		mapper.put("y", y);
		return mapper;
	}

	private double x1, y1;

	private void mapConv(double lat, double lng, LamcParameter map) {
		// TODO Auto-generated method stub

		lamcproj(lat, lng, map);
		x = (int) (x1 + 1.5);
		y = (int) (y1 + 1.5);
//		System.out.println(x + "," + y);
	}

	double PI, DEGRAD, RADDEG;
	double re, olng, olat, sn, sf, ro;

	private void lamcproj(double lat2, double lng2, LamcParameter map) {
		// TODO Auto-generated method stub
		double slat1, slat2, alng, alat, xn, yn, ra, theta;
		if (map.first == 0) {
			PI = Math.asin(1.0) * 2.0;
			DEGRAD = PI / 180.0;
			RADDEG = 180.0 / PI;

			re = map.Re / map.grid;
			slat1 = map.slat1 * DEGRAD;
			slat2 = map.slat2 * DEGRAD;
			olng = map.olon * DEGRAD;
			olat = map.olat * DEGRAD;

			sn = Math.tan(PI * 0.25 + slat2 * 0.5) / Math.tan(PI * 0.25 + slat1 * 0.5);
			sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
			sf = Math.tan(PI * 0.25 + slat1 * 0.5);
			sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
			ro = Math.tan(PI * 0.25 + olat * 0.5);
			ro = re * sf / Math.pow(ro, sn);
			map.first = 1;
		}

		ra = Math.tan(PI * 0.25 + lat2 * DEGRAD * 0.5);
		ra = re * sf / Math.pow(ra, sn);
		theta = lng2 * DEGRAD - olng;
		if (theta > PI)
			theta -= 2.0 * PI;
		if (theta < -PI)
			theta += 2.0 * PI;
		theta *= sn;
		x1 = (ra * Math.sin(theta)) + map.xo;
		y1 = (ro - ra * Math.cos(theta)) + map.yo;
	}

	public String xmlToJson(String str, String curTime, String baseDate) {
		String output = "";
		try {
			String xml = str;
			JSONObject jObj = XML.toJSONObject(xml);
			JSONObject jobj = jObj.getJSONObject("response").getJSONObject("body").getJSONObject("items");
//			System.out.println(jobj);
			JSONArray jArray = (JSONArray) jobj.get("item");
//			System.out.println(jArray);
			JSONArray jarr = this.concatJson(jArray, curTime, baseDate);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("item", jarr);

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			Object json = mapper.readValue(jsonObject.toString(), Object.class);
			output = mapper.writeValueAsString(json);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return output;
	}

	public JSONArray concatJson(JSONArray jarr, String curTime, String baseDate) {
		JSONArray arr = new JSONArray();
		for (int i = 0; i < jarr.length(); i = i + 12) {
			JSONObject jobj = new JSONObject();
			JSONObject temp = jarr.getJSONObject(i);
			if (temp.get("baseDate").toString().equals(baseDate)
					&& temp.get("fcstTime").toString().compareTo(curTime) < 0)
				continue;
			for (int j = i; j < i + 12; j++) {
				JSONObject obj = (JSONObject) jarr.get(j);
				jobj.put("baseDate", obj.get("baseDate"));
				jobj.put("baseTime", obj.get("baseTime"));
				jobj.put("fcstDate", obj.get("fcstDate"));
				jobj.put("fcstTime", obj.get("fcstTime"));
				if (obj.get("category").equals("TMP"))
					jobj.put("tmp", obj.get("fcstValue"));
				else if (obj.get("category").equals("VEC")) {
					int degree = obj.getInt("fcstValue");
					if (degree >= 0 && degree < 45)
						jobj.put("windDir", "북-북동");
					else if (degree >= 45 && degree < 90)
						jobj.put("windDir", "북동-동");
					else if (degree >= 90 && degree < 135)
						jobj.put("windDir", "동-남동");
					else if (degree >= 135 && degree < 180)
						jobj.put("windDir", "남동-남");
					else if (degree >= 180 && degree < 225)
						jobj.put("windDir", "서-서남");
					else if (degree >= 225 && degree < 270)
						jobj.put("windDir", "서남-서");
					else if (degree >= 270 && degree < 315)
						jobj.put("windDir", "서-북서");
					else if (degree >= 315 && degree < 360)
						jobj.put("windDir", "북서-북");
				} else if (obj.get("category").equals("WSD")) {
					float spd = obj.getFloat("fcstValue");
					if (spd < 4.0f)
						jobj.put("windDesc", "약");
					else if (spd >= 4.0f && spd < 9.0f)
						jobj.put("windDesc", "약강");
					else if (spd >= 9.0f && spd < 14.0f)
						jobj.put("windDesc", "강");
					else if (spd >= 14)
						jobj.put("windDesc", "매우강");
					jobj.put("windSpd", obj.get("fcstValue"));
				} else if (obj.get("category").equals("SKY")) {
					String f = obj.get("fcstValue").toString();
					switch (f) {
					case "1":
						jobj.put("skyState", "sunny");
						break;
					case "3":
						jobj.put("skyState", "cloudy");
						break;
					case "4":
						jobj.put("skyState", "foggy");
						break;
					}
				} else if (obj.get("category").equals("PTY")) {
					String f = obj.get("fcstValue").toString();
					switch (f) {
					case "0":
						jobj.put("skyState", "sunny");
						break;
					case "1":
						jobj.put("skyState", "rainy");
						break;
					case "2":
						jobj.put("skyState", "snowAndRain");
						break;
					case "3":
						jobj.put("skyState", "snow");
						break;
					case "4":
						jobj.put("skyState", "heavyRain");
						break;
					}
				} else if (obj.get("category").equals("PCP"))
					jobj.put("rainAmount", obj.get("fcstValue"));
				else if (obj.get("category").equals("REH"))
					jobj.put("humidity", obj.get("fcstValue"));
				else if (obj.get("category").equals("SNO"))
					jobj.put("snowAmount", obj.get("fcstValue"));
			}
			arr.put(jobj);
		}
//		System.out.println(arr);
		return arr;
	}

	public String cutChartData(String json, String location) {
		String ret = "";
		try {
			JSONObject jobj = new JSONObject(json);
			JSONArray jArray = (JSONArray) jobj.get("data");
			JSONObject jsonObject = new JSONObject();
			JSONArray datasets = new JSONArray();
			ArrayList<String> keys = new ArrayList<>();
			String loc = "";

			for (int i = 0; i < jArray.length(); i++) {
				JSONObject temp = jArray.getJSONObject(i);
				if (temp.getString("지역").equals(location)) {
					loc = temp.getString("지역");
					Set<String> it = temp.keySet();
					for (String key : it) {
						if(!key.equals("지역") && key.split(" ")[0].compareTo("2010년") > 0)
							keys.add(key);
					}
					Collections.sort(keys);
					for (String key : keys) {
						JSONObject target = new JSONObject();
//						System.out.println(key.split(" ")[0]);
//						System.out.println(key.split(" ")[0].compareTo("2010년"));
						if (!key.equals("지역") && key.split(" ")[0].compareTo("2010년") > 0) {
							target.put("label", key);
							target.put("backgroundColor", "#41B883");
							target.put("data", temp.get(key));
						}
						datasets.put(target);
					}
					break;
				}
			}
			System.out.println(keys);
			jsonObject.put("labels", keys);
			jsonObject.put("datasets", datasets);
			jsonObject.put("loc", loc);

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			Object jsonData = mapper.readValue(jsonObject.toString(), Object.class);
			ret = mapper.writeValueAsString(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	class LamcParameter {
		double Re; /* 사용할 지구반경 [ km ] */
		double grid; /* 격자간격 [ km ] */
		double slat1; /* 표준위도 [degree] */
		double slat2; /* 표준위도 [degree] */
		double olon; /* 기준점의 경도 [degree] */
		double olat; /* 기준점의 위도 [degree] */
		double xo; /* 기준점의 X좌표 [격자거리] */
		double yo; /* 기준점의 Y좌표 [격자거리] */
		int first; /* 시작여부 (0 = 시작) */
	}
}
