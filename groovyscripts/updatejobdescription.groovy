import com.cloudbees.hudson.plugins.folder.*
import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*

// get job name from the parameter
def job
def jobname = build.buildVariableResolver.resolve("jobname")
println("Job to freeze/thaw = ${jobname}")

//check if job is inside  jenkins folders 
def nested_folder_job
hudson.model.Hudson.instance.items.each{
  if(it instanceof Folder){
    it.getAllItems().each {
     if(it.toString().contains("${jobname}"))
       nested_folder_job = it.getFullName() 
    }
  }
}

// get the details of the job to be frozen/thawed

if (nested_folder_job) {
 job = jenkins.model.Jenkins.instance.getItemByFullName(nested_folder_job)
}else {
  job = hudson.model.Hudson.instance.getItem(jobname)
}


// set description for current build
def desc = job.getDescription();
if (desc.contains('FREEZE')) {
  // Currently frozen, so thaw it out
  println("Thawing job ${jobname}")
  job.setDescription(desc.replaceFirst(/FREEZE-.*/, ""));
} else {
  // get user email
def build = hudson.model.Hudson.instance.getItem("jenkins-freeze-thaw-job-test").getLastBuild()
  def id = build.getCause(hudson.model.Cause.UserIdCause.class).getUserId()
def email = hudson.model.User.get(id).getProperty(hudson.tasks.Mailer.UserProperty.class).getAddress()
  println("ID = ${id}")
  println("Email = ${email}")
  // Not currently frozen, so freeze it
  println("Freezing job ${jobname}")
  job.setDescription("${desc}\nFREEZE-${email}");	
 }


// loop over jobs and save them
println("Saving jobs...")
 for (itemsave in hudson.model.Hudson.instance.items) {
  //println("Saving " + itemsave);
  itemsave.save();
}
