import com.cloudbees.hudson.plugins.folder.*
import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*

JobList=[]
void fetchFolder(Item folder) {
  folder.getItems().each{
    if(it instanceof Folder){
    fetchFolder(it)
    }else{
    fetchJob(it)
    }
  }
}
 void fetchJob(Item job){
    job_name=job.getName()
   JobList.add(job_name)
}

hudson.model.Hudson.instance.items.each{
  if(it instanceof Folder){
  fetchFolder(it)
  }else{
  fetchJob(it)
  }
}
return JobList.sort()
