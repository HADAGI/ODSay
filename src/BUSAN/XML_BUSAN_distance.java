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


public class XML_BUSAN_distance {

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

                    API_XML_BUSAN_distance apiExplorer_bus = new API_XML_BUSAN_distance();
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

                            System.out.println("출발역명 : " + getTagValue("startSn", element));
                            System.out.println("출발역코드 : " + getTagValue("startSc", element));
                            System.out.println("도착역명 : " + getTagValue("endSn", element));
                            System.out.println("도착역코드 : " + getTagValue("endSc", element));
                            System.out.println("이동거리 : " + getTagValue("dist", element));
                            System.out.println("이동시간 : " + getTagValue("time", element));
                            System.out.println("정차시간 : " + getTagValue("stoppingTime", element));
                            System.out.println("환승구분 : " + getTagValue("exchange", element));

                            System.out.println("==========================================================");

//                          부산 - 기본역정보

                            String txt =
                                    getTagValue("startSn", element) + "|" +
                                            getTagValue("startSc", element) + "|" +
                                            getTagValue("endSn", element) + "|" +
                                            getTagValue("endSc", element) + "|" +
                                            getTagValue("dist", element) + "|" +
                                            getTagValue("time", element) + "|" +
                                            getTagValue("stoppingTime", element) + "|" +
                                            getTagValue("exchange", element) + "\n";

//                   txt로 출력하기
                            //FileWriter fw = new FileWriter("C:\\Users\\neiandcompany\\Desktop\\incheon_BUS_station2.txt", true);
                            FileWriter fw = new FileWriter("C:\\Users\\jiwon\\Desktop\\부산 - 운행정보 2021-04-13.txt", true);
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