import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.*
import groovyx.net.http.ContentType
import static groovyx.net.http.Method.*
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import static groovy.json.JsonOutput.toJson
import java.text.SimpleDateFormat


//def project = 'app-ne-preprod-lls-atlassian'
//def topic = 'xyz_project_all_issue_events'
def access_token = '<token>'
def http = new HTTPBuilder("<url>")
def message = ""
Issue issue = issue
def customFieldManager = ComponentAccessor.getCustomFieldManager()
def assigneeEmail = issue.getAssignee().getEmailAddress()
def jenvironment = customFieldManager.getCustomFieldObjectByName("<env>")
def jenvironmentValue = issue.getCustomFieldValue(jenvironment).join('').toString()
def jstartTimeField = customFieldManager.getCustomFieldObjectByName("Start Date")
def jstartTimeValue = issue.getCustomFieldValue(jstartTimeField).toString().split('\\.')[0]
//String startTimeString = "${startTimeValue}".toString().split('\\.')[0]
def jendTimeField = customFieldManager.getCustomFieldObjectByName("End Date")
def jendTimeValue = issue.getCustomFieldValue(jendTimeField).toString().split('\\.')[0]
//Object endTimeValue = issue.getCustomFieldValue(endTimeField);
def MINUTE = 60000
def HOUR = 60 * MINUTE
def DAY = 24 * HOUR

SimpleDateFormat date12Format = new SimpleDateFormat("dd/MM/yy hh:mm aa");
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss")
Date startTimeDate = sdf.parse(jstartTimeValue)
Date endTimeDate = sdf.parse(jendTimeValue)
long startTimeInMS = startTimeDate.getTime()
long endTimeInMS = endTimeDate.getTime()
def duration = endTimeInMS - startTimeInMS
def durationDay = duration.intdiv(DAY)
def durationHour = duration.intdiv(HOUR).mod(24)
def durationMin = duration.intdiv(MINUTE).mod(60)
String outageDuration = "${durationDay} ${String.format("%02d",durationHour)}:${String.format("%02d",durationMin)}:00"
def jimpactField = customFieldManager.getCustomFieldObjectByName("Impact description")
def jimpactValue = issue.getCustomFieldValue(jimpactField)
def jbackoutField = customFieldManager.getCustomFieldObjectByName("Rollback Process")
def jbackoutValue = issue.getCustomFieldValue(jbackoutField)
def snnField = customFieldManager.getCustomFieldObjectByName("ServiceNowCR")
def snnValue = issue.getCustomFieldValue(snnField).toString()
def jpriorityValue = issue.getPriority().getName()
String snPriority = 'moderate'
if(jpriorityValue == 'Highest')
{
    snPriority = 'critical'
}
else if(jpriorityValue == 'High')
{
    snPriority = 'high'
}
else if((jpriorityValue == 'Lowest') || (jpriorityValue == 'Low'))
{
    snPriority = 'low'
}
else 
{
    snPriority = 'moderate'
}

if(snnValue.contains('CHG')) 
{
    def commentManager = ComponentAccessor.getCommentManager()
	def user = ComponentAccessor.getJiraAuthenticationContext().getUser()
	commentManager.create(issue, user, "service now ticket already created", false)
}
else{    
http.request(POST, JSON) { req ->
    headers.'Authorization' = "Bearer ${access_token}"
    headers.'Accept' = 'application/json'
    headers.'ContentType' = 'application/json'
    body = toJson(assignedto: "${assigneeEmail}",system: 'is-ng-misc',impact: "${jimpactValue}",outageduration:  "${outageDuration}",priority: "${snPriority}",environment: "${jenvironmentValue}",purpose: "${issue.getSummary()}",description: "${issue.getDescription()}",plannedstart: "${jstartTimeValue}",plannedend: "${jendTimeValue}",deploymentready: 'yes',type: 'standard',backoutplan: "${jbackoutValue}")
   response.success = { resp, JSON ->
       message = "${JSON.result.number.toString()}"
       
   }
   response.failure = { resp, JSON ->
       message = "Creation failed with ${JSON}"
   }
}

def commentManager = ComponentAccessor.getCommentManager()
def user = ComponentAccessor.getJiraAuthenticationContext().getUser()
log.debug("There are Unresolved sub-tasks")
commentManager.create(issue, user, "service now ticket has been created CR#:${message}", false)
issue.setCustomFieldValue(snnField, "${message}".toString())
}