package selautomation.common;



import java.util.HashMap;
import java.util.Set;

public class SlackPayload {
  public static String engQAChannelUrl =
      "https://hooks.slack.com/services/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";
  
  public static String testjenkinsJobsQaChannel =
      "https://hooks.slack.com/services/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";
  
  public static String msgPayload =
      "{\"attachments\":[{\"color\":\"#f2c744\",\"blocks\":[{\"type\":\"divider\"},{\"type\":\"header\",\"text\":"
          + "{\"type\":\"plain_text\",\"text\":\"Dashboard Regression Test Suite Execution Results.\",\"emoji\":"
          + "true}},{\"type\":\"divider\"},{\"type\":\"section\",\"fields\":[{\"type\":\"mrkdwn\",\"text\":\"*"
          + "Realm  :  {realm}*\"},{\"type\":\"mrkdwn\",\"text\":\"*Reportportallink  :  <{reportLink}|Viewresult>"
          + "*\"}]},{\"type\":\"section\",\"fields\":[{\"type\":\"mrkdwn\",\"text\":\":white_large_square:  "
          + "*Total Test Cases  :  {totalTestcCases}*\"},{\"type\":\"mrkdwn\",\"text\":\":white_check_mark:  *"
          + "Passed  :  {totalPassed}*\"},{\"type\":\"mrkdwn\",\"text\":\":x:  *Failed  :  {totalFailed}*\"},"
          + "{\"type\":\"mrkdwn\",\"text\":\":warning:  *Other  :  {totalSkipped}*\"}]},{\"type\":\"divider\"},"
          + "{\"type\":\"divider\"},{\"type\":\"header\",\"text\":{\"type\":\"plain_text\",\"text\":\"---------"
          + "------------------  Components details  ---------------------------\",\"emoji\":true}},{\"type\":"
          + "\"divider\"},{\"type\":\"divider\"},{componentDetails}{\"type\":\"divider\"}]}]}";
  public static String originalComponentDetail =
      "{\"type\":\"section\",\"text\":{\"type\":\"mrkdwn\",\"text\":\"•  *_{componentName}_*\"}},"
          + "{\"type\":\"actions\",\"elements\":[{\"type\":\"button\",\"text\":{\"type\":\"plain_text\",\"text\":"
          + "\":white_large_square:  Total  :  {total}\",\"emoji\":true},\"value\":\"details\"},{\"type\":\"button\","
          + "\"style\":\"primary\",\"value\":\"approve\",\"text\":{\"type\":\"plain_text\",\"text\":\":"
          + "white_check_mark:  Pass  :  {pass}\",\"emoji\":true}},{\"type\":\"button\",\"text\":{\"type\":"
          + "\"plain_text\",\"text\":\":x:  Fail  :  {fail}\",\"emoji\":true},\"style\":\"danger\",\"value\":"
          + "\"decline\"},{\"type\":\"button\",\"text\":{\"type\":\"plain_text\",\"text\":\":warning:  Other  :"
          + "  {skip}\",\"emoji\":true},\"value\":\"details\"}]},";
  
  
  
  
}
