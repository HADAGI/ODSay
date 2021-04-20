package Seoul;

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

//            File file = new File("C:\\Users\\jiwon\\Desktop\\지하철\\지하철 고유번호\\서울 지하철 전철코드.txt");
            File file = new File("C:\\Users\\jiwon\\Desktop\\100~1099.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String fileline = "";

            while ((fileline = bufferedReader.readLine()) != null) {
                stationNo[count] = fileline;

                count++;
            }
            bufferedReader.close();

            String txt1 = "";
            String txt2 = "";


            for (int j = 0; j < 1000; j++) {



                String urlStr = "https://api.odsay.com/v1/api/subwayTransitInfo" + "?" +
                        "apiKey=" + apikey + "&" +
                        "lang=" + lang + "&" +
                        "stationID=" + stationNo[j]; // 노선정보

                System.out.println("url : " + urlStr);


                URL url = new URL(urlStr);
                BufferedReader bf;
                String line = "";
                String result = "";

                bf = new BufferedReader(new InputStreamReader(url.openStream()));

                System.out.println("Bf : " + bf);


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

                    txt1 += stationNo[j] + "\n";

                    FileWriter fileWriter = new FileWriter("C:\\Users\\jiwon\\Desktop\\정보가 없는 역.txt");
                    fileWriter.write(txt1);
                    fileWriter.flush();
                    fileWriter.close();


                } else {


                    System.out.println("parse_result : " + parse_result);


                    JSONArray transitTotalInfo = (JSONArray) parse_result.get("transitTotalInfo");
                    System.out.println("transitTotalInfo : " + transitTotalInfo);

                    for (int f = 0; f < transitTotalInfo.size(); f++) {


                        JSONObject transitTotalInfo_data = (JSONObject) transitTotalInfo.get(f); // 한단계씩 내려갈때마다 JSONObject 선언해주고 array 만들어야됨
                        JSONArray transitTotalInfo_data_real = (JSONArray) transitTotalInfo_data.get("transitTotalInfo");

                        long takeStationID = (long) transitTotalInfo_data.get("takeStationID");
                        System.out.println("타고온 역 ID : " + takeStationID);
                        String takeLaneName = (String) transitTotalInfo_data.get("takeLaneName");
                        System.out.println("타고온 노선명 : " + takeLaneName);
                        long takeLaneID = (long) transitTotalInfo_data.get("takeLaneID");
                        System.out.println("타고온 노선 ID : " + takeLaneID);
                        String takeLaneDirection = (String) transitTotalInfo_data.get("takeLaneDirection");
                        System.out.println("타고온 노선 방면 : " + takeLaneDirection);
                        long exStationID = (long) transitTotalInfo_data.get("exStationID");
                        System.out.println("환승 할 역 ID : " + exStationID);
                        String exLaneName = (String) transitTotalInfo_data.get("exLaneName");
                        System.out.println("환승 할 노선명 : " + exLaneName);
                        long exLaneID = (long) transitTotalInfo_data.get("exLaneID");
                        System.out.println("환승 할 노선 ID : " + exLaneID);
                        String exLaneDirection = (String) transitTotalInfo_data.get("exLaneDirection");
                        System.out.println("환승 할 노선 방면 : " + exLaneDirection);
                        String fastTrainInfo = (String) transitTotalInfo_data.get("fastTrainInfo");
                        System.out.println("환승 정보 : " + fastTrainInfo);
                        long FastTrain = (long) transitTotalInfo_data.get("FastTrain");
                        System.out.println("빠른환승 열차번호 : " + FastTrain);
                        long FastFastDoor = (long) transitTotalInfo_data.get("FastFastDoor");
                        System.out.println("빠른환승 문 번호 : " + FastFastDoor);
                        long FastTrainNum = (long) transitTotalInfo_data.get("FastTrainNum");
                        System.out.println("전체 열차 칸 수 : " + FastTrainNum);
                        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");


                        txt2 += takeStationID + "|" +
                                takeLaneName + "|" +
                                takeLaneID + "|" +
                                takeLaneDirection + "|" +
                                exStationID + "|" +
                                exLaneName + "|" +
                                exLaneID + "|" +
                                exLaneDirection + "|" +
                                fastTrainInfo + "|" +
                                FastTrain + "|" +
                                FastFastDoor + "|" +
                                FastTrainNum + "\n";

                        FileWriter fileWriter = new FileWriter("C:\\Users\\jiwon\\Desktop\\100~1099지하철 환승 정보.txt");
                        fileWriter.write(txt2);
                        fileWriter.flush();
                        fileWriter.close();
                    }

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