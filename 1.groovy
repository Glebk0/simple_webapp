@Grapes([
        @Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7'),
        @GrabConfig(systemClassLoader=true)
])
import groovyx.net.http.RESTClient
import org.apache.http.entity.*
import hudson.model.*

String[] pomParsing(def workspace) {
    def pom = new XmlSlurper().parse(new File("pom.xml"))
    def list = []
    list.add(pom.groupId)
    list.add(pom.artifactId)
    list.add(pom.version)
    return list
}

void push() {
    def buildNumber = System.getenv('BUILD_NUMBER')
    def repo = "maven-artifacts"
    def workspace = System.getenv('WORKSPACE')
    def gav = pomParsing(workspace)
    def restClient = new RESTClient("http://nexus/repository/${repo}/")
    restClient.auth.basic 'admin', 'admin123'
    restClient.encoder.'application/zip' = this.&encodingZipFile
    def correct_path = gav[0].replaceAll('\\.','/')
    def artifactId = gav[1]
    def version = gav[2].split('-')[0]
    def launch = restClient.put(
            path: "http://nexus/repository/${repo}/${correct_path}/${version}/${buildNumber}/${artifactId}-${buildNumber}.war",
            body: new File("target/${artifactId}-1.${buildNumber}.war"),
            requestContentType: 'application/zip'
    )
}

def encodingZipFile(Object data) throws UnsupportedEncodingException {
    def entity = new FileEntity((File) data, 'application/zip')
    entity.setContentType('application/zip')
    return entity
}

void pull() {
    def artifactName = System.getenv('ARTIFACT_NAME')
    def buildNumber = artifactName.split('-')[1]
    def repo = "maven-artifacts"
    def workspace = System.getenv('WORKSPACE')
    def gav = pomParsing(workspace)
    def correct_path = gav[0].replaceAll('\\.','/')
    def artifactId = gav[1]
    def version = gav[2].split('-')[0]
    def restClient = new RESTClient("http://nexus/repository/${repo}/")
    restClient.auth.basic 'admin', 'admin123'
    def launch = restClient.get(path: "http://nexus/repository/${repo}/${correct_path}/${version}/${buildNumber}/${artifactName}.war"
    )
    new File("./${artifactId}.war") << launch.data
}

void main() {
    def artifact_name = System.getenv('ARTIFACT_NAME')
    (artifact_name == null) ? push() : pull()
}

main()
