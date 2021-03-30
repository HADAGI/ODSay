
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.*;

import org.xml.sax.InputSource;

public class XML_ODSay_subway_Timetable {

    // tagname을 가져오고 nodeList에 저장하는 부분

    /*
    private static String getTagValue(String tag, Element element) {

        NodeList OrdList = element.getElementsByTagName(tag);

        Node node = OrdList.item(0);

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

     */

    /*
    private static String getTagValue2(String tag, Element element) {

        NodeList SatList = element.getElementsByTagName(tag);

        Node node = SatList.item(0);

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

    private static String getTagValue3(String tag, Element element) {

        NodeList SunList = element.getElementsByTagName(tag);

        Node node = SunList.item(0);

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

     */


    public static void main(String[] args) {

        int count = 0;
        String[] busId = new String[100000];  // 버스 전체 배열크기
        String txt = "";

        try {

            File file = new File("C:\\Users\\jiwon\\Desktop\\외부 지하철코드.txt");
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
//                    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                    documentBuilderFactory.setNamespaceAware(true);
                    DocumentBuilder builder;
                    Document doc = null;

                    API_XML_ODSay_subway_Timetable apiExplorer_bus = new API_XML_ODSay_subway_Timetable();
                    String data = apiExplorer_bus.getData(busId[j]);     // ApiExplorer_bus 호출


                    InputSource is = new InputSource(new StringReader(data));
                    builder = documentBuilderFactory.newDocumentBuilder();
                    doc = builder.parse(is);

                    XPathFactory xPathFactory = XPathFactory.newInstance();
                    XPath xPath = xPathFactory.newXPath();

                    XPathExpression expr = xPath.compile("//OrdList/up/time");
                    XPathExpression expr2 = xPath.compile("//OrdList/down/time");
                    XPathExpression expr3 = xPath.compile("//SatList/up/time");
                    XPathExpression expr4 = xPath.compile("//SatList/down/time");
                    XPathExpression expr5 = xPath.compile("//SunList/up/time");
                    XPathExpression expr6 = xPath.compile("//SunList/down/time");

                    NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                    NodeList nodeList2 = (NodeList) expr2.evaluate(doc, XPathConstants.NODESET);
                    NodeList nodeList3 = (NodeList) expr3.evaluate(doc, XPathConstants.NODESET);
                    NodeList nodeList4 = (NodeList) expr4.evaluate(doc, XPathConstants.NODESET);
                    NodeList nodeList5 = (NodeList) expr5.evaluate(doc, XPathConstants.NODESET);
                    NodeList nodeList6 = (NodeList) expr6.evaluate(doc, XPathConstants.NODESET);

                    System.out.println("nodelist.getlength : " + nodeList.getLength());
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        NodeList child = nodeList.item(i).getChildNodes();

                        for (int f = 0; f < child.getLength(); f++) {
                            Node node = child.item(f);
                            System.out.println(node.getNodeName() + node.getTextContent());


                            txt += node.getTextContent() + "|" + "\n";

                        }


                    }


//                    Document doc = documentBuilder.parse(new InputSource(new StringReader(data)));  // String을 xml로  parse
//                    doc.getDocumentElement().normalize();


                    //System.out.println("itemListURL : " + url);
                    NodeList OrdList = doc.getElementsByTagName("result");//itemList
                    NodeList SatList = doc.getElementsByTagName("SatList");//itemList
                    NodeList SunList = doc.getElementsByTagName("SunList");//itemList
//             System.out.println("itemList : " + a[j] + "   " + nodeList);

                    System.out.println("리스트의 수  : " + OrdList.getLength());


//             버스리스트의 갯수
/*
                    for (int i = 0; i < OrdList.getLength(); i++) {  //     ★★★★★★★★★★★★★★★★★  OrdList ★★★★★★★★★★★★★★★

                        Node node = OrdList.item(i);

                        if (node.getNodeType() == Node.ELEMENT_NODE) {

                            Element element = (Element) node;

                            System.out.println("==========================================================");

                            // OrdList
                            System.out.println("stationName : " + getTagValue("stationName", element));
                            System.out.println("stationID : " + getTagValue("stationID", element));
                            System.out.println("laneName : " + getTagValue("laneName", element));
                            System.out.println("type : " + getTagValue("type", element));
                            System.out.println("laneCity : " + getTagValue("laneCity", element));
                            System.out.println("upWay : " + getTagValue("upWay", element));
                            System.out.println("downWay : " + getTagValue("downWay", element));
                            System.out.println("Idx : " + getTagValue("Idx", element));
                            System.out.println("list : " + getTagValue("list", element));


                            txt += getTagValue("stationName", element) + "|" +
                                    getTagValue("stationID", element) + "|" +
                                    getTagValue("laneName", element) + "|" +
                                    getTagValue("type", element) + "|" +
                                    getTagValue("laneCity", element) + "|" +
                                    getTagValue("upWay", element) + "|" +
                                    getTagValue("downWay", element) + "|" +
                                    getTagValue("Idx", element) + "|" +
                                    getTagValue("list", element) + "\n";


                        }
                    }

 */
                    FileWriter fw = new FileWriter("C:\\Users\\jiwon\\Desktop\\오디세이XML - 지하철 전체시간표.txt", true);
                    fw.write(txt);
                    fw.flush();
                    fw.close();

                    /*
                    for (int i = 0; i < SatList.getLength(); i++) {   //     ★★★★★★★★★★★★★★★★★  SatList ★★★★★★★★★★★★★★★

                        Node node = SatList.item(i);

                        if (node.getNodeType() == Node.ELEMENT_NODE) {

                            Element element = (Element) node;

                            System.out.println("==========================================================");

                            // SatList
                            System.out.println("Idx : " + getTagValue2("Idx", element));
                            System.out.println("list : " + getTagValue2("list", element));


                            txt += busId[j] + "|" +
                                    getTagValue("Idx", element) + "|" +
                                    getTagValue("list", element) + "\n";
                            FileWriter fw = new FileWriter("C:\\Users\\jiwon\\Desktop\\9호선 레일포털 - 서울 지하철 편의시설 정보.txt", true);
                            fw.write(txt);
                            fw.flush();
                            fw.close();



                        }
                    }

                    for (int i = 0; i < SunList.getLength(); i++) {  //     ★★★★★★★★★★★★★★★★★  SunList ★★★★★★★★★★★★★★★

                        Node node = SunList.item(i);

                        if (node.getNodeType() == Node.ELEMENT_NODE) {

                            Element element = (Element) node;

                            System.out.println("==========================================================");


                            // SunList
                            System.out.println("Idx : " + getTagValue3("Idx", element));
                            System.out.println("list : " + getTagValue3("list", element));


                            txt += busId[j] + "|" +
                                    getTagValue("Idx", element) + "|" +
                                    getTagValue("list", element) + "\n";

                            FileWriter fw = new FileWriter("C:\\Users\\jiwon\\Desktop\\9호선 레일포털 - 서울 지하철 편의시설 정보.txt", true);
                            fw.write(txt);
                            fw.flush();
                            fw.close();


                        }
                    }


                     */


                    Thread.currentThread();
                    Thread.sleep(500);

                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}