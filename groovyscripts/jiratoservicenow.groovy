import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.*
import groovyx.net.http.ContentType
import static groovyx.net.http.Method.*
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import static groovy.json.JsonOutput.toJson

def url = '<url>'
def access_token = '<access token>'
def http = new HTTPBuilder("${url}")
def message = ""
Issue issue = issue
def customFieldManager = ComponentAccessor.getCustomFieldManager()
def assigneeEmail = issue.getAssignee().getEmailAddress()
def jenvironment = customFieldManager.getCustomFieldObjectByName("<environment name>")
def jenvironmentValue = issue.getCustomFieldValue(jenvironment).toString()

def jpriorityValue = issue.getPriority().getName()
String snPriority = 'moderate'
switch(jpriorityValue){
case ['High', 'Highest']:
    snPriority = 'critical'
    break
case ['Low', 'Lowest']:
    snPriority = 'critical'
    break
}
    
http.request(POST, JSON) { req ->
    headers.'Authorization' = "Bearer ${access_token}"
    headers.'Accept' = 'application/json'
    headers.'ContentType' = 'application/json'
    body = toJson(assignedto: "${assigneeEmail}",system: '<system>',impact: 'some text',outageduration:  '00:30:00',priority: "${snPriority}",environment: "${jenvironmentValue}",purpose: "${issue.getSummary()}",description: "${issue.getDescription()}",plannedstart: '2020-08-30 10:00:00',plannedend: '2020-08-30 11:00:00',deploymentready: 'yes',type: 'standard',backoutplan: 'backout plan text')
   response.success = { resp, JSON ->
       message = "${JSON.result.number.toString()}"
       
   }
   response.failure = { resp, JSON ->
       message = "${jenvironmentValue}Creation failed with ${JSON}"
   }
}
