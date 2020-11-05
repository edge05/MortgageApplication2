import com.zos.groovy.utilities.ZosAppBuild
import com.ibm.dbb.build.BuildProperties
import com.zos.java.utilities.*

	def GroovyObject zBuild = (GroovyObject) ZosAppBuild.newInstance()
	// parse command line arguments and load build properties
	def usage = "build.groovy [options] buildfile"
 	def build = zBuild.execute(args, usage)
	 
	 //def subJob = new SubmitJob()
	 //def subPDS = "${properties.jclPDS}(TESTJOB)"
	 //subJob.sub("//'$subPDS'")
	 
	 
	 