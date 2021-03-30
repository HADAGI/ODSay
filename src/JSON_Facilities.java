
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;

public class JSON_Facilities {

    public static void main(String[] args) {

        int count = 0;
        String[] stationNo = new String[100000];

        String serviceKey = "$2a$10$zvcq7zFImyBXq4.Eh5ffs.Yl/Or.nNqNzaoO9bZD1qI.NQ7TaRJ/e"; //서버 인증키
        String format = "json";
        String railOprIsttCd = "S1";
//        String railOprIsttCd = "S5";
//        String railOprIsttCd = "S9";
        int lnCd = 2; // 호선

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

            for (int j = 32; j < 300; j++) {


                String urlStr = "http://openapi.kric.go.kr/openapi/handicapped/stationCnvFacl" + "?" +
                        "serviceKey=" + serviceKey + "&" +
                        "format=" + format + "&" +
                        "railOprIsttCd=" + railOprIsttCd + "&" +
                        "lnCd=" + lnCd + "&" +
                        "stinCd=" + stationNo[j]; // 노선정보


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

                String txt = "";

                JSONArray Bus = (JSONArray) alldata.get("body");

                if (Bus == null) {
                    System.out.println("Bus : null");
                } else {


                    for (int i = 0; i < alldata.size(); i++) {

                        JSONObject facilities_data = (JSONObject) Bus.get(i);


                        System.out.println("facilities_data : " + facilities_data);

                        String gubun = (String) facilities_data.get("gubun");
                        System.out.println("시설 구분 : " + gubun); // 시설 구분(EV : 엘리베이터, ES : 에스컬레이터, WCLF : 휠체어 리프트,ELEC : 전동 휠체어 충전 설비, TOLT : 화장실, INFO : 고객센터, FEED : 수유실)

                        long stinFlor = (long) facilities_data.get("stinFlor");
                        if (facilities_data == null) {
                            System.out.println("stinFlor : null");
                        } else {
                            System.out.println("역층 : " + stinFlor); // 역층

                            String grndDvCd = (String) facilities_data.get("grndDvCd");
                            System.out.println("지상구분코드(1:지상 2:지하) : " + grndDvCd); // 지상구분코드(1:지상 2:지하)

                            String dtlLoc = (String) facilities_data.get("dtlLoc");
                            System.out.println("상세위치 : " + dtlLoc); // 상세위치

                            String trfcWeakDvCd = (String) facilities_data.get("trfcWeakDvCd");
                            System.out.println("교통약자 구분 : " + trfcWeakDvCd);

                            String mlFmlDvCd = (String) facilities_data.get("mlFmlDvCd");
                            System.out.println("남녀 구분 : " + mlFmlDvCd); // 1:남자, 2:여자 3:공용

                            txt += stationNo[j] + "|" +
                                    gubun + "|" +
                                    grndDvCd + "|" +
                                    stinFlor + "|" +
                                    dtlLoc + "|" +
                                    trfcWeakDvCd + "|" +
                                    mlFmlDvCd + "\n";


                            FileWriter fw = new FileWriter("C:\\Users\\jiwon\\Desktop\\2호선 지하철 편의시설 데이터2.txt", true);
                            fw.write(txt);
                            fw.flush();
                            fw.close();

                            txt = "";


                        }
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
