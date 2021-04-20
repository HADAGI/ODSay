package BUSAN;

import com.google.gson.internal.bind.util.ISO8601Utils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URLEncoder;

import org.xml.sax.InputSource;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class XML_BUSAN_public {

    // tagname을 가져오고 nodeList에 저장하는 부분
    private static String getTagValue(String tag, Element element) {

        NodeList nodeList = element.getElementsByTagName(tag);

        Node node = nodeList.item(0);

        if (node == null) {
            return null;
        } else {
            NodeList childNode = node.getChildNodes();

            Node nValue = childNode.item(0);

            if (nValue == null) {
                return null;
            }
            return nValue.getNodeValue();
        }

    }

    public static void main(String[] args) {

        int count = 0;
        String[] busId = new String[100000];  // 버스 전체 배열크기

        try {
//       정류장번호를 불러오는 경로
//            File file = new File("C:\\Users\\neiandcompany\\IdeaProjects\\untitled1\\src\\Bus\\Incheon_bus_station_id(duplication";
            File file = new File("C:\\Users\\jiwon\\Desktop\\부산 역 코드.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufreader = new BufferedReader(fileReader);
            String line = "";

            while ((line = bufreader.readLine()) != null) {
                busId[count] = line;
//       System.out.println(a[count]);
//          if (count < 5) {
//             System.out.println("정류장번호 확인 : " + a[count]);
//          }
                count++;
            }
            bufreader.close();
//<OpenAPI_ServiceResponse>    <cmmMsgHeader>    <errMsg>SERVICE ERROR</errMsg>    <returnAuthMsg>HTTP_ERROR</returnAuthMsg>     <returnReasonCode>04</returnReasonCode>    </cmmMsgHeader></OpenAPI_ServiceResponse>
//itemList : http://apis.data.go.kr/6280000/busStationService/getBusStationIdList?serviceKey=Ob7C7H5BU9u4SqoFiGjEJZKZoIPUKQVKqtlEv2HfIxzE5xBzY2qBNZihHiLrnHZfkDWbvgCxpyepaHJoVhdDjQ%3D%3D&pageNo=1&numOfRows=10&bstopId=163000513


            for (int j = 0; j < busId.length; j++) { // 버스번호 범위

                while (true) {//http://apis.data.go.kr/6280000/busStationService/getBusStationIdList?serviceKey=%2Feab3wgUxZR46s%2BAthE7A2Aydqs8Uyq6yUXt3XfJQZ55tF%2BdwhlKZ1mjLQOsE%2BEVBhaUhkOUJATDiam35tA6kA%3D%3D&pageNo=1&numOfRows=10&bstopId=101000002


                    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

                    API_XML_BUSAN_public apiExplorer_bus = new API_XML_BUSAN_public();
                    String data = apiExplorer_bus.getData(busId[j]);     // ApiExplorer_bus 호출

                    Document doc = documentBuilder.parse(new InputSource(new StringReader(data)));  // String을 xml로  parse

                    doc.getDocumentElement().normalize();
                    //System.out.println("itemListURL : " + url);

                    NodeList nodeList = doc.getElementsByTagName("item");//itemList


//                    System.out.println("값 : " + getTagValue("sname", (Element) nodeList));


//             System.out.println("itemList : " + a[j] + "   " + nodeList);

                    System.out.println("리스트의 수  : " + nodeList.getLength());
//             버스리스트의 갯수

                    for (int i = 0; i < nodeList.getLength(); i++) {

                        Node node = nodeList.item(i);

                        if (node.getNodeType() == Node.ELEMENT_NODE) {

                            Element element = (Element) node;


                            System.out.println("==========================================================");

                            // 부산 - 기본역정보

                            System.out.println("역코드 : " + getTagValue("scode", element));
                            System.out.println("한글역명 : " + getTagValue("sname", element));
                            System.out.println("주륜장설치위치 : " + getTagValue("bicycle_location", element));
                            System.out.println("관리자 : " + getTagValue("bicycle_management", element));
                            System.out.println("주륜대수 : " + getTagValue("bicycle_ea", element));
                            System.out.println("주차면수 : " + getTagValue("parking", element));
                            System.out.println("주차장 면적(㎡) : " + getTagValue("parking_dimension", element));
                            System.out.println("주자창 관리주체 : " + getTagValue("parking_organization", element));
                            System.out.println("주차장 전화번호 : " + getTagValue("parking_tel", element));
                            System.out.println("주차장 비고 : " + getTagValue("parking_bigo", element));
                            System.out.println("주자창 관리주체 : " + getTagValue("cabinet_s", element));
                            System.out.println("물품보관함(소) : " + getTagValue("cabinet_s", element));
                            System.out.println("물품보관함(중) : " + getTagValue("cabinet_m", element));
                            System.out.println("물품보관함(대) : " + getTagValue("cabinet_l", element));
                            System.out.println("물품보관함 담당자 : " + getTagValue("cabinet_company", element));
                            System.out.println("물품보관함 요금 : " + getTagValue("cabinet_cost", element));
                            System.out.println("만남의장소 의자수량 : " + getTagValue("meet", element));
                            System.out.println("무인민원발급기 개수 : " + getTagValue("atm", element));
                            System.out.println("무인민원발급기 장소 : " + getTagValue("atm_locational", element));
                            System.out.println("무인민원발급기 설치기관 : " + getTagValue("atm_apparatus", element));

                            System.out.println("==========================================================");

//                          부산 - 기본역정보

                            String txt =
                                    getTagValue("scode", element) + "|" +
                                            getTagValue("sname", element) + "|" +
                                            getTagValue("bicycle_location", element) + "|" +
                                            getTagValue("bicycle_management", element) + "|" +
                                            getTagValue("bicycle_ea", element) + "|" +
                                            getTagValue("parking", element) + "|" +
                                            getTagValue("parking_dimension", element) + "|" +
                                            getTagValue("parking_organization", element) + "|" +
                                            getTagValue("parking_tel", element) + "|" +
                                            getTagValue("parking_bigo", element) + "|" +
                                            getTagValue("cabinet_s", element) + "|" +
                                            getTagValue("cabinet_m", element) + "|" +
                                            getTagValue("cabinet_l", element) + "|" +
                                            getTagValue("cabinet_company", element) + "|" +
                                            getTagValue("cabinet_cost", element) + "|" +
                                            getTagValue("meet", element) + "|" +
                                            getTagValue("atm", element) + "|" +
                                            getTagValue("atm_locational", element) + "|" +
                                            getTagValue("atm_apparatus", element) + "\n";

//                   txt로 출력하기
                            //FileWriter fw = new FileWriter("C:\\Users\\neiandcompany\\Desktop\\incheon_BUS_station2.txt", true);
                            FileWriter fw = new FileWriter("C:\\Users\\jiwon\\Desktop\\부산 - 도시철도 공공시설물 정보 2021-04-14.txt", true);
                            fw.write(txt);
                            fw.flush();
                            fw.close();

                        }
                    }

                    Thread.currentThread();
                    Thread.sleep(300);

                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}