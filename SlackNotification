package selautomation.common;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;


public class SlackNotification {
  
  /**
   *
   * @param baseUri
   * @param jsonString
   * @return
   */
  public static Response postSlackNotification(String baseUri, String jsonString) {
    RestAssured.baseURI = baseUri;
    RequestSpecification httpRequest = null;
    Response response = null;
    httpRequest = RestAssured.given().header("Content-Type", "application/json");
    try {
      System.out.println(jsonString);
      JSONObject json = new JSONObject(jsonString);
      response = httpRequest.body(jsonString).put();
      //response = httpRequest.body(json).put();
      System.out.println(response.getStatusLine());
    } catch (JSONException e) {
      e.printStackTrace();
    }
  
    return response;
  }
  
  /**
   * Constructs msg payload
   * @param suiteStats
   * @param allComponentStats
   * @param remoteExtentReportFilePath
   * @return
   */
  public static String constructPayload(HashMap<String, Integer> suiteStats,
                                        HashMap<String, HashMap<String, Integer>> allComponentStats,
                                        String remoteExtentReportFilePath) {
    String msgPayload = SlackPayload.msgPayload.replace("{realm}", Constants.realm);
    msgPayload = msgPayload.replace("{reportLink}", remoteExtentReportFilePath);
    msgPayload = msgPayload.replace("{totalTestcCases}", Integer.toString(suiteStats.get("total")));
    msgPayload = msgPayload.replace("{totalPassed}", Integer.toString(suiteStats.get("pass")));
    msgPayload = msgPayload.replace("{totalFailed}", Integer.toString(suiteStats.get("fail")));
    msgPayload = msgPayload.replace("{totalSkipped}", Integer.toString(suiteStats.get("skip")));
    String componentDetail = "";
    Set<String> componentNames = allComponentStats.keySet();
    for (String componentName : componentNames) {
      HashMap<String, Integer> componentStats = allComponentStats.get(componentName);
      componentDetail = componentDetail + new String(SlackPayload.originalComponentDetail);
      componentDetail = componentDetail.replace("{componentName}", componentName);
      
      componentDetail = componentDetail.replace("{total}", Integer.toString(componentStats.get("total")));
      componentDetail = componentDetail.replace("{pass}", Integer.toString(componentStats.get("pass")));
      componentDetail = componentDetail.replace("{fail}", Integer.toString(componentStats.get("fail")));
      componentDetail = componentDetail.replace("{skip}", Integer.toString(componentStats.get("skip")));
      
      //System.out.println("componentDetail : \n" + componentDetail);
    }
    
    msgPayload = msgPayload.replace("{componentDetails}", componentDetail);
    
    System.out.println("Slack message payload : \n" + msgPayload);
  
    SlackNotification.postSlackNotification(SlackPayload.testjenkinsJobsQaChannel, msgPayload);
    if (!System.getProperty("os.name").toLowerCase().contains("mac")) {
      SlackNotification.postSlackNotification(SlackPayload.engQAChannelUrl, msgPayload);
    }
    return msgPayload;
  }
  
  
}
