package Seoul;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

import org.xml.sax.InputSource;

public class XML_kric_subway_stationTimeTable {

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

            File file = new File("C:\\Users\\jiwon\\Desktop\\지하철\\수도권\\API\\레일포털\\편의시설\\서해선-SW.txt");
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

                    API_XML_kric_stationTimeTable apiExplorer_bus = new API_XML_kric_stationTimeTable();
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


                            // 레일포털 서울지하철 - 역사별 환승정보
                            System.out.println("도착시각 : " + getTagValue("arvTm", element));
                            System.out.println("요일코드 : " + getTagValue("dayCd", element));
                            System.out.println("요일명 : " + getTagValue("dayNm", element));
                            System.out.println("출발시각 : " + getTagValue("dptTm", element));
                            System.out.println("선코드 : " + getTagValue("lnCd", element));
                            System.out.println("시발 역 코드 : " + getTagValue("orgStinCd", element));
                            System.out.println("철도운영기관코드 : " + getTagValue("railOprIsttCd", element));
                            System.out.println("종착역코드 : " + getTagValue("tmnStinCd", element));
                            System.out.println("열차번호 : " + getTagValue("trnNo", element));

                            System.out.println("==========================================================");


                            String txt =
                                    busId[j] + "|" +
                                            getTagValue("arvTm", element) + "|" +
                                            getTagValue("dayCd", element) + "|" +
                                            getTagValue("dayNm", element) + "|" +
                                            getTagValue("dptTm", element) + "|" +
                                            getTagValue("lnCd", element) + "|" + "|" +
                                            getTagValue("orgStinCd", element) + "|" +
                                            getTagValue("railOprIsttCd", element) + "|" +
                                            getTagValue("tmnStinCd", element) + "|" +
                                            getTagValue("trnNo", element) + "\n";

//                            FileWriter fw = new FileWriter("C:\\Users\\jiwon\\Desktop\\1호선-KR 편의시설.txt", true);
                            FileWriter fw = new FileWriter("C:\\Users\\jiwon\\Desktop\\서해선GM - 평일시간표.txt", true);
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
