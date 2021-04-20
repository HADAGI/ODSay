package Seoul;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;

public class JSON_ODSay_schedule {

    public static void main(String[] args) {

        int count = 0;
        String[] stationNo = new String[100000];

        String apikey = "MUpfDp5LvQ4Unpu3gkIQBDfV9VO4/XPv6DIiajIAbAw"; //서버 인증키
        int lang = 0;
        int showExpressTime = 1;

        try {

            File file = new File("C:\\Users\\jiwon\\Desktop\\외부 지하철코드.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String fileline = "";

            while ((fileline = bufferedReader.readLine()) != null) {
                stationNo[count] = fileline;

                count++;
            }
            bufferedReader.close();

            for (int j = 0; j < 1; j++) {


                String urlStr = "https://api.odsay.com/v1/api/subwayTimeTable" + "?" +
                        "apiKey=" + apikey + "&" +
                        "lang=" + lang + "&" +
                        "stationID=" + stationNo[j] + "&" +
                        "showExpressTime=" + showExpressTime; // 노선정보


                System.out.println("url : " + urlStr);


                URL url = new URL(urlStr);
                BufferedReader bf;
                String line = "";
                String result = "";

                bf = new BufferedReader(new InputStreamReader(url.openStream()));

                while ((line = bf.readLine()) != null) {
                    result = result.concat(line);
//                        System.out.println("result : " + result);
                }

                JSONParser parser = new JSONParser();

                JSONObject alldata = (JSONObject) parser.parse(result);
//                System.out.println("alldata : " + alldata);

                JSONObject parse_result = (JSONObject) alldata.get("result");
//                System.out.println("parse_result : " + parse_result);
                JSONObject OrdList = (JSONObject) parse_result.get("OrdList");


                JSONObject OrdList_up = (JSONObject) OrdList.get("up");     // 상행선 리스트
                JSONArray OrdList_up_time = (JSONArray) OrdList_up.get("time");

                JSONObject OrdList_down = (JSONObject) OrdList.get("down"); // 하행선 리스트
                JSONArray OrdList_down_time = (JSONArray) OrdList_down.get("time");

                JSONObject SatList = (JSONObject) parse_result.get("SatList"); // --토요일--
                JSONObject SatList_up = (JSONObject) SatList.get("up");     // 토요일 상행선 리스트
                JSONArray SatList_up_time = (JSONArray) SatList_up.get("time");
                JSONObject SatList_down = (JSONObject) SatList.get("down"); // 토요일 하행선 리스트
                JSONArray SatList_down_time = (JSONArray) SatList_down.get("time");

                JSONObject SunList = (JSONObject) parse_result.get("SunList"); // --일요일--
                JSONObject SunList_up = (JSONObject) SunList.get("up");     // 일요일 상행선 리스트
                JSONArray SunList_up_time = (JSONArray) SunList_up.get("time");
                JSONObject SunList_down = (JSONObject) SunList.get("down"); // 일요일 하행선 리스트
                JSONArray SunList_down_time = (JSONArray) SunList_down.get("time");

                String txt = "";

                for (int i = 0; i < alldata.size(); i++) {

                    JSONObject parse_transitTotalInfo = (JSONObject) parse_result.get("transitTotalInfo"); // 기본 역 정보
                    System.out.println("parse_transitTotalInfo : " + parse_transitTotalInfo);

                    long type = (long) parse_result.get("type");
                    System.out.println("노선종류 : " + type);
                    String laneName = (String) parse_result.get("laneName");
                    System.out.println("노선명 : " + laneName);
                    String laneCity = (String) parse_result.get("laneCity");
                    System.out.println("노선지역명 : " + laneCity);
                    String upWay = (String) parse_result.get("upWay");
                    System.out.println("상행방향 : " + upWay);
                    String downWay = (String) parse_result.get("downWay");
                    System.out.println("하행방향 : " + downWay);
                    long stationID = (long) parse_result.get("stationID");
                    System.out.println("지하철역 ID : " + stationID);
                    String stationName = (String) parse_result.get("stationName");
                    System.out.println("지하철역 명 : " + stationName);

                    txt += stationName + "|" +
                            stationID + "|" +
                            laneName + "|" +
                            type + "|" +
                            laneCity + "|" +
                            upWay + "|" +
                            downWay;


                    for (int z = 0; z < OrdList_up_time.size(); z++) {     // 상행선 시간 데이터 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★

                        JSONObject OrdList_uptime_data = (JSONObject) OrdList_up_time.get(z);
                        long OrdList_uptime_data_Idx = (long) OrdList_uptime_data.get("Idx");
                        String OrdList_uptime_data_list = (String) OrdList_uptime_data.get("list");
                        String OrdList_uptime_data_expList = (String) OrdList_uptime_data.get("expList");
                        String OrdList_uptime_data_expSPList = (String) OrdList_uptime_data.get("expSPList");

                        System.out.println(z + "번째 데이터 : " + OrdList_uptime_data);
                        System.out.println(z + "번째 시간 : " + OrdList_uptime_data_Idx);
                        System.out.println(z + "번째 시간 data : " + OrdList_uptime_data_list);
                        System.out.println(z + "번째 시간 data : " + OrdList_uptime_data_expList);
                        System.out.println(z + "번째 시간 data : " + OrdList_uptime_data_expSPList);

                        txt += "|"+ OrdList_uptime_data_Idx + "|" + OrdList_uptime_data_list +
                                "|"+ "|"+ "|"+ "|"+ "|"+ "|"+ OrdList_uptime_data_Idx + "|" +
                                OrdList_uptime_data_list + "\n";

                    }


                    for (int x = 0; x < OrdList_down_time.size(); x++) { // 하행선 시간 데이터 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★

                        JSONObject OrdList_downtime_data = (JSONObject) OrdList_down_time.get(x);
                        long OrdList_downtime_data_Idx = (long) OrdList_downtime_data.get("Idx");
                        String OrdList_downtime_data_list = (String) OrdList_downtime_data.get("list");
                        String OrdList_downtime_data_expList = (String) OrdList_downtime_data.get("expList");
                        String OrdList_downtime_data_expSPList = (String) OrdList_downtime_data.get("expSPList");

                        System.out.println(x + "번째 데이터 : " + OrdList_downtime_data);
                        System.out.println(x + "번째 시간 : " + OrdList_downtime_data_Idx);
                        System.out.println(x + "번째 시간 data : " + OrdList_downtime_data_list);
                        System.out.println(x + "번째 시간 data : " + OrdList_downtime_data_expList);
                        System.out.println(x + "번째 시간 data : " + OrdList_downtime_data_expSPList);

                        txt += "|" + "|" + "|" + "|" + "|" + "|" + "|" +
                                "|" + "|" +
                                OrdList_downtime_data_Idx + "|" +
                                OrdList_downtime_data_list + "|" +
                                "|" + "|" + "|" + "|" + "|" + "|" + "|" + "|" + "|" + "|" +
                                OrdList_downtime_data_expList + "|" +
                                OrdList_downtime_data_expSPList + "\n";
                    }


                    for (int x = 0; x < SatList_up_time.size(); x++) { // 토요일 상행선 시간 데이터 ★★★★★★★★★★★★★★★★★★★★★★★★★★

                        JSONObject SatList_uptime_data = (JSONObject) SatList_up_time.get(x);
                        long SatList_uptime_data_Idx = (long) SatList_uptime_data.get("Idx");
                        String SatList_uptime_data_list = (String) SatList_uptime_data.get("list");
                        String SatList_uptime_data_expList = (String) SatList_uptime_data.get("expList");
                        String SatList_uptime_data_expSPList = (String) SatList_uptime_data.get("expSPList");

                        System.out.println(x + "번째 데이터 : " + SatList_uptime_data);
                        System.out.println(x + "번째 시간 : " + SatList_uptime_data_Idx);
                        System.out.println(x + "번째 시간 data : " + SatList_uptime_data_list);
                        System.out.println(x + "번째 시간 data : " + SatList_uptime_data_expList);
                        System.out.println(x + "번째 시간 data : " + SatList_uptime_data_expSPList);

                        txt += "|" + "|" + "|" + "|" + "|" + "|" + "|" +
                                "|" + "|" + "|" + "|" +
                                SatList_uptime_data_Idx + "|" +
                                SatList_uptime_data_list + "|" +
                                "|" + "|" + "|" + "|" + "|" + "|" + "|" + "|" + "|" + "|" +
                                SatList_uptime_data_expList + "|" +
                                SatList_uptime_data_expSPList + "\n";
                    }


                    for (int x = 0; x < SatList_down_time.size(); x++) { // 토요일 하행선 시간 데이터 ★★★★★★★★★★★★★★★★★★★★★★★★★★

                        JSONObject SatList_downtime_data = (JSONObject) SatList_down_time.get(x);
                        long SatList_downtime_data_Idx = (long) SatList_downtime_data.get("Idx");
                        String SatList_downtime_data_list = (String) SatList_downtime_data.get("list");
                        String SatList_downtime_data_expList = (String) SatList_downtime_data.get("expList");
                        String SatList_downtime_data_expSPList = (String) SatList_downtime_data.get("expSPList");

                        System.out.println(x + "번째 데이터 : " + SatList_downtime_data);
                        System.out.println(x + "번째 시간 : " + SatList_downtime_data_Idx);
                        System.out.println(x + "번째 시간 data : " + SatList_downtime_data_list);
                        System.out.println(x + "번째 시간 data : " + SatList_downtime_data_expList);
                        System.out.println(x + "번째 시간 data : " + SatList_downtime_data_expSPList);

                        txt += "|" + "|" + "|" + "|" + "|" + "|" + "|" +
                                "|" + "|" + "|" + "|" + "|" + "|" +
                                SatList_downtime_data_Idx + "|" +
                                SatList_downtime_data_list + "|" +
                                "|" + "|" + "|" + "|" + "|" + "|" + "|" + "|" + "|" + "|" +
                                SatList_downtime_data_expList + "|" +
                                SatList_downtime_data_expSPList + "\n";
                    }


                    for (int x = 0; x < SunList_up_time.size(); x++) { // 일요일 상행선 시간 데이터 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★

                        JSONObject SunList_uptime_data = (JSONObject) SunList_up_time.get(x);
                        long SunList_uptime_data_Idx = (long) SunList_uptime_data.get("Idx");
                        String SunList_uptime_data_list = (String) SunList_uptime_data.get("list");
                        String SunList_uptime_data_expList = (String) SunList_uptime_data.get("expList");
                        String SunList_uptime_data_expSPList = (String) SunList_uptime_data.get("expSPList");

                        System.out.println(x + "번째 데이터 : " + SunList_uptime_data);
                        System.out.println(x + "번째 시간 : " + SunList_uptime_data_Idx);
                        System.out.println(x + "번째 시간 data : " + SunList_uptime_data_list);
                        System.out.println(x + "번째 시간 data : " + SunList_uptime_data_expList);
                        System.out.println(x + "번째 시간 data : " + SunList_uptime_data_expSPList);

                        txt += "|" + "|" + "|" + "|" + "|" + "|" + "|" +
                                "|" + "|" + "|" + "|" + "|" + "|" + "|" + "|" +
                                SunList_uptime_data_Idx + "|" +
                                SunList_uptime_data_list + "|" +
                                "|" + "|" + "|" + "|" + "|" + "|" + "|" + "|" + "|" + "|" +
                                SunList_uptime_data_expList + "|" +
                                SunList_uptime_data_expSPList + "\n";
                    }


                    for (int x = 0; x < SunList_down_time.size(); x++) { // 일요일 하행선 시간 데이터 ★★★★★★★★★★★★★★★★★★★★★★★★★

                        JSONObject SunList_downtime_data = (JSONObject) SunList_down_time.get(x);
                        long SunList_downtime_data_Idx = (long) SunList_downtime_data.get("Idx");
                        String SunList_downtime_data_list = (String) SunList_downtime_data.get("list");
                        String SunList_downtime_data_expList = (String) SunList_downtime_data.get("expList");
                        String SunList_downtime_data_expSPList = (String) SunList_downtime_data.get("expSPList");

                        System.out.println(x + "번째 데이터 : " + SunList_downtime_data);
                        System.out.println(x + "번째 시간 : " + SunList_downtime_data_Idx);
                        System.out.println(x + "번째 시간 data : " + SunList_downtime_data_list);
                        System.out.println(x + "번째 시간 data : " + SunList_downtime_data_expList);
                        System.out.println(x + "번째 시간 data : " + SunList_downtime_data_expSPList);

                        txt += "|" + "|" + "|" + "|" + "|" + "|" + "|" +
                                "|" + "|" + "|" + "|" + "|" + "|" + "|" + "|" + "|" + "|" +
                                SunList_downtime_data_Idx + "|" +
                                SunList_downtime_data_list + "|" +
                                "|" + "|" + "|" + "|" + "|" + "|" + "|" + "|" + "|" + "|" +
                                SunList_downtime_data_expList + "|" +
                                SunList_downtime_data_expSPList + "\n";
                    }


                    System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");

                    txt += "\n" + "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" +
                            "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" +
                            "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" +
                            "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ" + "|" + "ㅡ\n";

                    System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");

                    FileWriter fw = new FileWriter("C:\\Users\\jiwon\\Desktop\\지하철 환승 데이터.txt", true);
                    fw.write(txt);
                    fw.flush();
                    fw.close();


                }
            }

        } catch (
                NullPointerException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (
                ParseException e) {
            e.printStackTrace();
        }


    }

}
