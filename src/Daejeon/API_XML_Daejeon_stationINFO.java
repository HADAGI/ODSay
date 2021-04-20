package Daejeon;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;

public class API_XML_Daejeon_stationINFO {

    String key = "%2Feab3wgUxZR46s%2BAthE7A2Aydqs8Uyq6yUXt3XfJQZ55tF%2BdwhlKZ1mjLQOsE%2BEVBhaUhkOUJATDiam35tA6kA%3D%3D"; // 야닉1

//    String url = "http://www.djet.co.kr/OpenAPI/service/StationNameSVC/getStationName"; // 대전지하철 - 역사별 정보
//    String url = "http://www.djet.co.kr/OpenAPI/service/TimeTableSVC/getAllTimeTable"; // 대전지하철 - 열차시각표
    String url = "http://www.djet.co.kr/OpenAPI/service/ToiletSVC/getToiletData"; // 대전지하철 - 화장실

    public String getData(String busId) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        StringBuilder urlBuilder = new StringBuilder(this.url);

        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + this.key); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("99999", "UTF-8")); /*한 페이지 결과 수*/
//        urlBuilder.append("&" + URLEncoder.encode("stationNo", "UTF-8") + "=" + URLEncoder.encode(busId, "UTF-8")); /*한 페이지 결과 수*/

        HttpGet request = new HttpGet(urlBuilder.toString());

        HttpResponse response = client.execute(request);

        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }
}