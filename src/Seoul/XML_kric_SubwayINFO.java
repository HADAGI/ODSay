package Seoul;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

import org.xml.sax.InputSource;

public class XML_kric_SubwayINFO {

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

//            File file = new File("C:\\Users\\jiwon\\Desktop\\지하철\\서울\\각종 라인 코드\\ 코드.txt");
            File file = new File("C:\\Users\\jiwon\\Desktop\\지하철\\서울\\API\\레일포털\\편의시설\\인천2호선-IC.txt");
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

                    API_XML_kric_SubwayINFO apiExplorer_bus = new API_XML_kric_SubwayINFO();
                    String data = apiExplorer_bus.getData(busId[j]);     // ApiExplorer_bus 호출
                    Document doc = documentBuilder.parse(new InputSource(new StringReader(data)));  // String을 xml로  parse

                    doc.getDocumentElement().normalize();
                    //System.out.println("itemListURL : " + url);
                    NodeList nodeList = doc.getElementsByTagName("item");//itemList
//             System.out.println("itemList : " + a[j] + "   " + nodeList);

                    System.out.println("리스트의 수  : " + nodeList.getLength());
//             버스리스트의 갯수

                    String txt = "";
                    for (int i = 0; i < nodeList.getLength(); i++) {


                        Node node = nodeList.item(i);

                        if (node.getNodeType() == Node.ELEMENT_NODE) {

                            Element element = (Element) node;

                            System.out.println("==========================================================");


                            // 서울지하철 - 레일포털 역 정보
                            System.out.println("stinNm : " + getTagValue("stinNm", element));
                            System.out.println("stinCd : " + getTagValue("stinCd", element));
                            System.out.println("railOprIsttCd : " + getTagValue("railOprIsttCd", element));
                            System.out.println("lnCd : " + getTagValue("lnCd", element));
                            System.out.println("lonmAdr : " + getTagValue("lonmAdr", element));
                            System.out.println("stinLocLat : " + getTagValue("stinLocLat", element));
                            System.out.println("stinLocLon : " + getTagValue("stinLocLon", element));
                            System.out.println("mapCordX : " + getTagValue("mapCordX", element));
                            System.out.println("mapCordY : " + getTagValue("mapCordY", element));
                            System.out.println("strkZone : " + getTagValue("strkZone", element));

                            System.out.println("==========================================================");


                            // 서울지하철 - 레일포털 역 정보
                            txt += getTagValue("stinCd", element) + "|" +
                                    getTagValue("stinNm", element) + "|" +
                                    getTagValue("railOprIsttCd", element) + "|" +
                                    getTagValue("lnCd", element) + "|" + "|" +
                                    getTagValue("lonmAdr", element) + "|" +
                                    getTagValue("roadNmAdr", element) + "|" +
                                    getTagValue("stinLocLat", element) + "|" +
                                    getTagValue("stinLocLon", element) + "|" +
                                    getTagValue("mapCordX", element) + "|" +
                                    getTagValue("mapCordY", element) + "|" +
                                    getTagValue("strkZone", element) + "\n";

                        }
                    }
                    FileWriter fw = new FileWriter("C:\\Users\\jiwon\\Desktop\\인천1호선 기본정보 2021-04-16.txt", true);
                    fw.write(txt);
                    fw.flush();
                    fw.close();

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