
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

            for (int j = 0; j < 1; j++) {


                String urlStr = "https://api.odsay.com/v1/api/subwayTimeTable" + "?" + "apiKey=" + apikey + "&" + "lang=" + lang + "&" + "stationID=" + stationNo[j]; // 노선정보

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
                System.out.println("parse_result : " + parse_result);

                JSONObject parse_defaultInfo = (JSONObject) parse_result.get("defaultInfo"); // 기본 역 정보
                System.out.println("parse_defaultInfo : " + parse_defaultInfo);

                JSONObject parse_useInfo = (JSONObject) parse_result.get("useInfo"); // 이용정보
                System.out.println("parse_useInfo : " + parse_useInfo);

                JSONObject parse_exitInfo = (JSONObject) parse_result.get("exitInfo"); // 출구정보
                System.out.println("parse_exitInfo : " + parse_exitInfo);

                JSONArray parse_gate = (JSONArray) parse_exitInfo.get("gate"); // 출구번호 담기
                System.out.println("parse_gate : " + parse_gate);


                JSONObject parse_nextOBJ = (JSONObject) parse_result.get("nextOBJ"); // 전체 데이터에서 다음 정류장 리스트 담기
                System.out.println("parse_nextOBJ : " + parse_nextOBJ);

                JSONArray parse_nextStation = (JSONArray) parse_nextOBJ.get("station"); // 다음 정류장 담기
                System.out.println("parse_nextStation : " + parse_nextStation);

                JSONObject parse_prevOBJ = (JSONObject) parse_result.get("prevOBJ"); // 전체 데이터에서 이전 정류장 리스트 담기
                System.out.println("parse_prevOBJ : " + parse_prevOBJ);

                JSONArray parse_prevStation = (JSONArray) parse_prevOBJ.get("station"); // 이전 정류장 담기
                System.out.println("parse_station : " + parse_prevStation);

                JSONObject parse_exOBJ = (JSONObject) parse_result.get("exOBJ"); // 전체 데이터에서 환승 정류장 리스트 담기
                System.out.println("parse_exOBJ : " + parse_exOBJ);

                JSONArray parse_exOBJstation = (JSONArray) parse_prevOBJ.get("station"); // 환승 정류장 담기
                System.out.println("parse_exOBJstation : " + parse_exOBJstation);


                String txt = "";


                for (int i = 0; i < alldata.size(); i++) {

                    JSONObject prevOBJstationData = (JSONObject) parse_prevStation.get(i);
                    JSONObject nextOBJstationData = (JSONObject) parse_nextStation.get(i);
                    JSONObject exOBJstationData = (JSONObject) parse_exOBJstation.get(i);
                    JSONObject exitData = (JSONObject) parse_gate.get(i);

                    System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
//                  여기부터 해당역에 대한 정보
                    String stationInfo_stationName = (String) parse_result.get("stationName");
                    System.out.println("해당역 명 : " + stationInfo_stationName);

                    long stationInfo_stationID = (long) parse_result.get("stationID");
                    System.out.println("해당역ID : " + stationInfo_stationID);

                    long stationInfo_type = (long) parse_result.get("type");
                    System.out.println("노선종류 : " + stationInfo_type);

                    String stationInfo_laneName = (String) parse_result.get("laneName");
                    System.out.println("노선명 : " + stationInfo_laneName);

                    String stationInfo_laneCity = (String) parse_result.get("laneCity");
                    System.out.println("노선지역명 : " + stationInfo_laneCity);

                    long stationInfo_citycode = (long) parse_result.get("CityCode");
                    System.out.println("도시코드 : " + stationInfo_citycode);

                    double stationInfo_x = (double) parse_result.get("x");
                    System.out.println("x좌표(경위도) : " + stationInfo_x);

                    double stationInfo_y = (double) parse_result.get("y");
                    System.out.println("y좌표(경위도) : " + stationInfo_y);

                    String stationAddress = (String) parse_defaultInfo.get("address");
                    System.out.println("역 주소 : " + stationAddress);

                    String stationNewAddress = (String) parse_defaultInfo.get("new_address");
                    System.out.println("도로명 주소 : " + stationNewAddress);

                    String stationTel = (String) parse_defaultInfo.get("tel");
                    System.out.println("역 전화번호 : " + stationTel);

                    long station_platform = (long) parse_useInfo.get("platform"); // 0:기타, 1:중앙, 2:양쪽, 3:복선(국 철), 4:일방향
                    System.out.println("플랫폼 : " + station_platform);

                    long station_meetingPlace = (long) parse_useInfo.get("meetingPlace"); // 0:없음, 1:있음
                    System.out.println("만남의 장소 : " + station_meetingPlace);

                    long station_restroom = (long) parse_useInfo.get("restroom"); // 0:없음, 1:안쪽, 2:바깥, 3:환승역연 결, 4:안쪽,바깥쪽
                    System.out.println("화장실 : " + station_restroom);

                    long station_offDoor = (long) parse_useInfo.get("offDoor"); // 0:왼쪽, 1:오른쪽, 2:양쪽
                    System.out.println("내리는 문 위치 : " + station_offDoor);

                    long station_crossOver = (long) parse_useInfo.get("crossOver"); // 0:기타, 1:연결안됨, 2:연결됨, 3:환승역연결
                    System.out.println("반대편 횡단 : " + station_crossOver);

                    long station_publicPlace = (long) parse_useInfo.get("publicPlace"); // 0:없음, 1:있음
                    System.out.println("현장 사무소 : " + station_publicPlace);

                    long station_handicapCount = (long) parse_useInfo.get("handicapCount"); // 0:없음, 1:있음
                    System.out.println("장애인편의시설 : " + station_handicapCount);

                    long station_parkingCount = (long) parse_useInfo.get("parkingCount"); // 0:없음, 1:있음
                    System.out.println("환승주차장 : " + station_parkingCount);

                    long station_bicycleCount = (long) parse_useInfo.get("bicycleCount"); // 0:없음, 1:있음
                    System.out.println("자전거보관소 : " + station_bicycleCount);

                    long station_civilCount = (long) parse_useInfo.get("civilCount"); // 0:없음, 1:있음
                    System.out.println("민원안내 : " + station_civilCount);

                    String station_gateNo = (String) exitData.get("gateNo");
                    System.out.println("출구번호 : " + station_gateNo);


                    System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");

//                    여기부터 해당 역에 대한 다음역 리스트
                    String nextOBJ_stationName = (String) nextOBJstationData.get("stationName");
                    System.out.println("다음역 명 : " + nextOBJ_stationName);

                    long nextOBJ_stationID = (long) nextOBJstationData.get("stationID");
                    System.out.println("다음역ID : " + nextOBJ_stationID);

                    long nextOBJ_type = (long) nextOBJstationData.get("type");
                    System.out.println("노선종류 : " + nextOBJ_type);

                    String nextOBJ_laneName = (String) nextOBJstationData.get("laneName");
                    System.out.println("노선명 : " + nextOBJ_laneName);

                    String nextOBJ_laneCity = (String) nextOBJstationData.get("laneCity");
                    System.out.println("노선지역명 : " + nextOBJ_laneCity);

                    double nextOBJ_x = (double) nextOBJstationData.get("x");
                    System.out.println("x좌표(경위도) : " + nextOBJ_x);

                    double nextOBJ_y = (double) nextOBJstationData.get("y");
                    System.out.println("y좌표(경위도) : " + nextOBJ_y);

                    System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");


//                    여기부터 해당 역에 대한 이전역 리스트
                    String prevOBJ_stationName = (String) prevOBJstationData.get("stationName");
                    System.out.println("이전역 명 : " + prevOBJ_stationName);

                    long prevOBJ_stationID = (long) prevOBJstationData.get("stationID");
                    System.out.println("이전역ID : " + prevOBJ_stationID);

                    long prevOBJ_type = (long) prevOBJstationData.get("type");
                    System.out.println("노선종류 : " + prevOBJ_type);

                    String prevOBJ_laneName = (String) prevOBJstationData.get("laneName");
                    System.out.println("노선명 : " + prevOBJ_laneName);

                    String prevOBJ_laneCity = (String) prevOBJstationData.get("laneCity");
                    System.out.println("노선지역명 : " + prevOBJ_laneCity);

                    double prevOBJ_x = (double) prevOBJstationData.get("x");
                    System.out.println("x좌표(경위도) : " + prevOBJ_x);

                    double prevOBJ_y = (double) prevOBJstationData.get("y");
                    System.out.println("y좌표(경위도) : " + prevOBJ_y);

                    System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");


                    String exOBJ_stationName = (String) exOBJstationData.get("stationName");
                    System.out.println("환승역 명 : " + exOBJ_stationName);

                    long exOBJ_stationID = (long) exOBJstationData.get("stationID");
                    System.out.println("환승역ID : " + exOBJ_stationID);

                    long exOBJ_type = (long) exOBJstationData.get("type");
                    System.out.println("노선종류 : " + exOBJ_type);

                    String exOBJ_laneName = (String) exOBJstationData.get("laneName");
                    System.out.println("노선명 : " + exOBJ_laneName);

                    String exOBJ_laneCity = (String) exOBJstationData.get("laneCity");
                    System.out.println("노선지역명 : " + exOBJ_laneCity);

                    txt += stationInfo_stationName + "|" +
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


                    System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");

                    for (int k = 0; k < parse_gate.size(); k++) {
                        JSONObject tmp = (JSONObject) parse_gate.get(k);//인덱스 번호로 접근해서 가져온다.

                        JSONObject gateNo = (JSONObject) parse_gate.get(k);
                        String gateNo_data = (String) gateNo.get("gateNo");

//                        txt += gateNo_data + "번 출구" + "\n";

                        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
//                        System.out.println(gateNo_data + "번 게이트정보 : " + tmp);
                        System.out.println("출구개수 : " + parse_gate.size());


                        JSONArray gateLink = (JSONArray) tmp.get("gateLink");
                        for (int c = 0; c < ((JSONArray) tmp.get("gateLink")).size(); c++) {   // 출구주변 시설 갯수만큼 돌리기
                            String gateLink_data = (String) gateLink.get(c);
//                            System.out.println(gateLink_data + "번 출구 주변시설 : " + gateLink_data);
                            txt += gateLink_data + "\n";
                        }


                        JSONArray BUSSTOP = (JSONArray) tmp.get("BUSSTOP"); // 출구 주변 정류소 갯수만큼 돌리기
                        if (BUSSTOP == null) {
                            txt += null;
                        } else {

                            for (int w = 0; w < BUSSTOP.size(); w++) {

                                JSONObject STOPNAME = (JSONObject) BUSSTOP.get(w);
                                String stopname_data = (String) STOPNAME.get("StopName");
                                String stopID_data = (String) STOPNAME.get("StopID");
                                System.out.println(w + "번째 정류소명 : " + stopname_data);
                                System.out.println(w + "번째 정류소ID : " + stopID_data);


                                JSONObject BUS_BASE = (JSONObject) BUSSTOP.get(w); // 이시발 좆같은 대괄호 한단계씩 내려갈때마다 JSONObject 선언해주고 array 만들어야됨 ㅅㅂ
                                JSONArray Bus = (JSONArray) BUS_BASE.get("Bus");

                                for (int q = 0; q < Bus.size(); q++) { // 해당 정류소의 버스 개수만큼 돌리기


                                    if (BUSSTOP == null) { // BUSSTOP 데이터가 비어있는경우 처리
                                        txt += gateNo_data + "번 출구에는 BUSSTOP 데이터가 없습니다\n";
                                    } else {
                                        JSONObject BusINFO = (JSONObject) Bus.get(q);


                                        String BusINFO_Type = (String) BusINFO.get("Type");
                                        String BusINFO_BusCityName = (String) BusINFO.get("BusCityName");
                                        String BusINFO_BusNo = (String) BusINFO.get("BusNo");
                                        String BusINFO_BlID = (String) BusINFO.get("BlID");

                                        System.out.println(q + "번째 버스 유형 : " + BusINFO_Type);
                                        System.out.println(q + "번째 버스 운수회사 승인 도시 이름 : " + BusINFO_BusCityName);
                                        System.out.println(q + "번째 버스노선 번호 : " + BusINFO_BusNo);
                                        System.out.println(q + "번째 버스노선 ID : " + BusINFO_BlID);

                                        txt += gateNo_data + "번 출구" + "|" + "|" +stopname_data + "|" + stopID_data + "|";
                                        txt += BusINFO_Type + "|" + BusINFO_BusCityName + "|" + BusINFO_BusNo + "|" + BusINFO_BlID + "\n";


//                            System.out.println("BUSSTOP : " + BUSSTOP);
                                        System.out.println("버스정류장 개수 : " + BUSSTOP.size());

                                    }
                                }


                            }



                        }

                    }


                    FileWriter fw = new FileWriter("C:\\Users\\jiwon\\Desktop\\지하철 기본 데이터.txt", true);
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
