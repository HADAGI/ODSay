package Seoul;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

import org.xml.sax.InputSource;

public class XML_kric_stationLostPropertyOffice {

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

            File file = new File("C:\\Users\\jiwon\\Desktop\\지하철\\수도권\\API\\레일포털\\편의시설\\인천2호선-IC.txt");
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

            for (int j = 0; j < busId.length; j++) { // 버스번호 범위

                while (true) {//http://apis.data.go.kr/6280000/busStationService/getBusStationIdList?serviceKey=%2Feab3wgUxZR46s%2BAthE7A2Aydqs8Uyq6yUXt3XfJQZ55tF%2BdwhlKZ1mjLQOsE%2BEVBhaUhkOUJATDiam35tA6kA%3D%3D&pageNo=1&numOfRows=10&bstopId=101000002

                    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

                    API_XML_kric_stationLostPropertyOffice apiExplorer_bus = new API_XML_kric_stationLostPropertyOffice();
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


                            // 레일포털 서울지하철 - 역사별 인접 계단 차량번호
                            System.out.println("상세위치 : " + getTagValue("dtlLoc", element));
                            System.out.println("거리 : " + getTagValue("exitNo", element));
                            System.out.println("출구번호 : " + getTagValue("grndDvCd", element));
                            System.out.println("주요시설명 : " + getTagValue("grndDvNm", element));
                            System.out.println("라인 코드 : " + getTagValue("lnCd", element));
                            System.out.println("철도운영기관코드 : " + getTagValue("railOprIsttCd", element));
                            System.out.println("전화번호 : " + getTagValue("stinCd", element));
                            System.out.println("전화번호 : " + getTagValue("stinFlor", element));
                            System.out.println("전화번호 : " + getTagValue("telNo", element));
                            System.out.println("전화번호 : " + getTagValue("utlPsbHr", element));

                            System.out.println("==========================================================");


                            String txt =
                                            getTagValue("dtlLoc", element) + "|" +
                                            getTagValue("exitNo", element) + "|" +
                                            getTagValue("grndDvCd", element) + "|" +
                                            getTagValue("grndDvNm", element) + "|" +
                                            getTagValue("lnCd", element) + "|" +
                                            getTagValue("railOprIsttCd", element) + "|" +
                                            getTagValue("stinCd", element) + "|" +
                                            getTagValue("stinFlor", element) + "|" +
                                            getTagValue("telNo", element) + "|" +
                                            getTagValue("utlPsbHr", element) + "\n";

//                            FileWriter fw = new FileWriter("C:\\Users\\jiwon\\Desktop\\1호선-KR 편의시설.txt", true);
                            FileWriter fw = new FileWriter("C:\\Users\\jiwon\\Desktop\\지하철\\수도권\\API\\레일포털\\역사별 유실물센터 정보\\인천2호선-IC 역사별 유실물센터 정보.txt", true);
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