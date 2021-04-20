package Daejeon;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

import org.xml.sax.InputSource;

public class XML_Daejeon_stationINFO {

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

            File file = new File("C:\\Users\\jiwon\\Desktop\\지하철\\서울\\API\\레일포털\\편의시설\\인천2호선-IC.txt");
//            File file = new File("C:\\Users\\jiwon\\Desktop\\지하철\\서울\\각종 라인 코드\\경강선 코드.txt");
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

            for (int j = 0; j < 1; j++) { // 버스번호 범위

                while (true) {//http://apis.data.go.kr/6280000/busStationService/getBusStationIdList?serviceKey=%2Feab3wgUxZR46s%2BAthE7A2Aydqs8Uyq6yUXt3XfJQZ55tF%2BdwhlKZ1mjLQOsE%2BEVBhaUhkOUJATDiam35tA6kA%3D%3D&pageNo=1&numOfRows=10&bstopId=101000002

                    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

                    API_XML_Daejeon_stationINFO apiExplorer_bus = new API_XML_Daejeon_stationINFO();
                    String data = apiExplorer_bus.getData(busId[j]);     // ApiExplorer_bus 호출
                    Document doc = documentBuilder.parse(new InputSource(new StringReader(data)));  // String을 xml로  parse

                    doc.getDocumentElement().normalize();
                    //System.out.println("itemListURL : " + url);
                    NodeList nodeList = doc.getElementsByTagName("item");//itemList
//             System.out.println("itemList : " + a[j] + "   " + nodeList);

                    System.out.println("리스트의 수  : " + nodeList.getLength());
//             버스리스트의 갯수

                    for (int i = 0; i < nodeList.getLength(); i++) {

                        Node node = nodeList.item(i);

                        if (node.getNodeType() == Node.ELEMENT_NODE) {

                            Element element = (Element) node;

                            System.out.println("==========================================================");


                            // 대전지하철 - 역사별정보
                            /*
                            System.out.println("역번호 : " + getTagValue("stationNo", element));
                            System.out.println("역명(한글) : " + getTagValue("stationNmKor", element));
                            System.out.println("역주소 : " + getTagValue("stationAddr", element));


                             */
/*
                            대전지하철 - 열차시각표
                            System.out.println("평일/휴일구분 : " + getTagValue("dayType", element));
                            System.out.println("상행/하행구분 : " + getTagValue("drctType", element));
                            System.out.println("역번호 : " + getTagValue("stNum", element));
                            System.out.println("열차출발시각 : " + getTagValue("tmList", element));
                            System.out.println("시간대 : " + getTagValue("tmZone", element));

 */
//                            대전지하철 - 화장실
                            System.out.println("데이터기준일자 : " + getTagValue("creationDate", element));
                            System.out.println("위도 : " + getTagValue("latitude", element));
                            System.out.println("경도 : " + getTagValue("longitude", element));
                            System.out.println("관리기관명 : " + getTagValue("manageOrg", element));
                            System.out.println("관리기관전화번호 : " + getTagValue("managePhone", element));
                            System.out.println("화장실명 : " + getTagValue("name", element));
                            System.out.println("도로명주소 : " + getTagValue("roadAddress", element));
                            System.out.println("구분 : " + getTagValue("type", element));
                            System.out.println("남녀공용화장실여부 : " + getTagValue("unisex", element));

                            System.out.println("==========================================================");

/*
                            대전지하철 - 역사별정보
                            String txt =
                                            getTagValue("stationNo", element) + "|" +
                                            getTagValue("stationNmKor", element) + "|" +
                                            getTagValue("stationAddr", element) + "\n";

 */

                            /*
                            대전지하철 - 열차시각표
                            String txt =
                                            getTagValue("dayType", element) + "|" +
                                            getTagValue("drctType", element) + "|" +
                                            getTagValue("stNum", element) + "|" +
                                            getTagValue("tmList", element) + "|" +
                                            getTagValue("tmZone", element) + "\n";

                             */

//                            대전 지하철 - 화장실
                            String txt =
                                            getTagValue("creationDate", element) + "|" +
                                            getTagValue("latitude", element) + "|" +
                                            getTagValue("longitude", element) + "|" +
                                            getTagValue("manageOrg", element) + "|" +
                                            getTagValue("managePhone", element) + "|" +
                                            getTagValue("name", element) + "|" +
                                            getTagValue("roadAddress", element) + "|" +
                                            getTagValue("type", element) + "|" +
                                            getTagValue("unisex", element) + "\n";

//                            FileWriter fw = new FileWriter("C:\\Users\\jiwon\\Desktop\\1호선-KR 편의시설.txt", true);
                            FileWriter fw = new FileWriter("C:\\Users\\jiwon\\Desktop\\대전 - 화장실.txt", true);
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