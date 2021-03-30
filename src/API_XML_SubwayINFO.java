
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.net.URLEncoder;
import org.apache.http.HttpResponse;

public class API_XML_SubwayINFO {

    String key = "$2a$10$zvcq7zFImyBXq4.Eh5ffs.Yl/Or.nNqNzaoO9bZD1qI.NQ7TaRJ/e"; // 야닉1
    String format = "xml";
    String railOprIsttCd = "AR";
    String lnCd = "M1"; // 선코드

    String url = "http://openapi.kric.go.kr/openapi/convenientInfo/stationInfo"; // 서울지하철 - 레일포털 역 정보

    public String getData(String busId) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        StringBuilder urlBuilder = new StringBuilder(this.url);

        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + this.key); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("format", "UTF-8") + "=" + URLEncoder.encode(format, "UTF-8")); /*페이지번호*/
//        urlBuilder.append("&" + URLEncoder.encode("railOprIsttCd", "UTF-8") + "=" + URLEncoder.encode(railOprIsttCd, "UTF-8")); /*한 페이지 결과 수*/
//        urlBuilder.append("&" + URLEncoder.encode("lnCd", "UTF-8") + "=" + URLEncoder.encode(lnCd, "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("stinCd", "UTF-8") + "=" + URLEncoder.encode(busId, "UTF-8")); /*한 페이지 결과 수*/

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