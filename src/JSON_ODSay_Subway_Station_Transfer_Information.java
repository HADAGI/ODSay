
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;

public class JSON_ODSay_Subway_Station_Transfer_Information {

    public static void main(String[] args) {

        int count = 0;
        String[] stationNo = new String[100000];

        String apikey = "MUpfDp5LvQ4Unpu3gkIQBDfV9VO4/XPv6DIiajIAbAw"; //서버 인증키
        int lang = 0;

        try {

            File file = new File("C:\\Users\\jiwon\\Desktop\\지하철\\지하철 고유번호\\서울 지하철 전철코드.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String fileline = "";

            while ((fileline = bufferedReader.readLine()) != null) {
                stationNo[count] = fileline;

                count++;
            }
            bufferedReader.close();


            for (int j = 0; j < 62; j++) {

                String txt2 = "";
                String txt5 = "";

                String urlStr = "http://api.odsay.com/v1/api/subwayTransitInfo" + "?" + "apiKey=" + apikey + "&" + "lang=" + lang + "&" + "stationID=" + stationNo[j]; // 노선정보

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
                System.out.println("alldata : " + alldata);


                JSONObject parse_result = (JSONObject) alldata.get("result");

                if (parse_result == null) {
                    System.out.println(stationNo[j] + "해당역은 정보가 없습니다");

                    txt5 += stationNo[j] + "\n";

                    FileWriter fileWriter = new FileWriter("C:\\Users\\jiwon\\Desktop\\정보가 없는 역.txt");
                    fileWriter.write(txt5);
                    fileWriter.flush();
                    fileWriter.close();


                } else {


                    System.out.println("parse_result : " + parse_result);

                    JSONObject parse_defaultInfo = (JSONObject) parse_result.get("defaultInfo"); // 기본 역 정보
                    System.out.println("parse_defaultInfo : " + parse_defaultInfo);

                    JSONObject parse_useInfo = (JSONObject) parse_result.get("useInfo"); // 이용정보
                    System.out.println("parse_useInfo : " + parse_useInfo);

                    JSONObject BUS_BASE = (JSONObject) BUSSTOP.get(w); // 대괄호 한단계씩 내려갈때마다 JSONObject 선언해주고 array 만들어야됨
                    JSONArray Bus = (JSONArray) BUS_BASE.get("Bus");


                    txt1 += stationInfo_stationName + "|" +
                            stationInfo_stationID + "|" +
                            stationInfo_type + "|" +
                            stationInfo_laneName + "|" +
                            stationInfo_citycode + "|" +
                            stationInfo_x + "|" +
                            stationInfo_y + "|" +
                            stationAddress + "|" +
                            stationNewAddress + "|" +
                            stationTel + "|" +
                            station_platform + "|" +
                            station_meetingPlace + "|" +
                            station_restroom + "|" +
                            station_offDoor + "|" +
                            station_crossOver + "|" +
                            station_publicPlace + "|" +
                            station_handicapCount + "|" +
                            station_parkingCount + "|" +
                            station_bicycleCount + "|" +
                            station_civilCount + "|" +
                            nextOBJ_stationName + "|" +
                            nextOBJ_stationID + "|" +
                            nextOBJ_type + "|" +
                            nextOBJ_laneName + "|" +
                            nextOBJ_laneCity + "|" +
                            nextOBJ_x + "|" +
                            nextOBJ_y + "|" +
                            prevOBJ_stationName + "|" +
                            prevOBJ_stationID + "|" +
                            prevOBJ_type + "|" +
                            prevOBJ_laneName + "|" +
                            prevOBJ_laneCity + "|" +
                            prevOBJ_x + "|" +
                            prevOBJ_y + "|" +
                            exOBJ_stationName + "|" +
                            exOBJ_stationID + "|" +
                            exOBJ_type + "|" +
                            exOBJ_laneName + "|" +
                            exOBJ_laneCity + "\n";


                    FileWriter fw = new FileWriter("C:\\Users\\jiwon\\Desktop\\환승역 지하철 기본 데이터.txt", true);
                    fw.write(txt2);
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