package BUSAN;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class API_XML_BUSAN_convenience {

    String key = "%2Feab3wgUxZR46s%2BAthE7A2Aydqs8Uyq6yUXt3XfJQZ55tF%2BdwhlKZ1mjLQOsE%2BEVBhaUhkOUJATDiam35tA6kA%3D%3D"; // 야닉1


    public String getData(String busId) throws IOException {

        StringBuilder urlBuilder = new StringBuilder("http://data.humetro.busan.kr/voc/api/open_api_convenience.tnn"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + key); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("act","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*xml, json, xls*/
        urlBuilder.append("&" + URLEncoder.encode("scode","UTF-8") + "=" + URLEncoder.encode(busId, "UTF-8")); /*역외부코드(ex.신평:101, 안평:414)*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"euc-kr"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(),"euc-kr"));
        }
        StringBuilder sb = new StringBuilder();


        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());

        return sb.toString();

    }
}