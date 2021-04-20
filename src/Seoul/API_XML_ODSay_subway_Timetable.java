package Seoul;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;

public class API_XML_ODSay_subway_Timetable {

    String apiKey = "MUpfDp5LvQ4Unpu3gkIQBDfV9VO4/XPv6DIiajIAbAw"; // 오디세이 - server api key
    String output = "xml";
    int lang = 0; // 결과언어 (국문:0, 영문:1, 일문:2, 중문(간체):3, 중문(번체):4))
    int showExpressTime = 1; // 급행시간 표출여부 (1: 급행포함)


    String url = "https://api.odsay.com/v1/api/subwayTimeTable"; // 오디세이 - 지하철역 전체시간표

    public String getData(String busId) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        StringBuilder urlBuilder = new StringBuilder(this.url);

        urlBuilder.append("?" + URLEncoder.encode("apiKey", "UTF-8") + "=" + this.apiKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("output", "UTF-8") + "=" + URLEncoder.encode(output, "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("lang", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(lang), "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("showExpressTime", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(showExpressTime), "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("stationID", "UTF-8") + "=" + URLEncoder.encode(busId, "UTF-8")); /*한 페이지 결과 수*/

        HttpGet request = new HttpGet(urlBuilder.toString());
        System.out.println("url : " + request);

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