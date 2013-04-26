## Glia - is a nervous system of you cluster

* Glia is distributed system for cluster node communication.
* Glia is simple, compact, fast, scalable, highly available, supports POJO and EJB model.
* Glia could be run as a standalone console application or inside any container (Servlet, EJB, Spring & etc.)
* Glia could be utilized by any JVM based language (Groovy, Scala, Clojure, JRuby, Jython & etc.)


Wiki definition of the word glia.

Glia (Greek γλία, γλοία "glue"; pronounced in English as either /ˈɡliːə/ or /ˈɡlaɪə/), are non-neuronal cells that maintain homeostasis, form myelin, and provide support and protection for neurons in the brain, and for neurons in other parts of the nervous system such as in the autonomic nervous system

Glia using [Netty](http://netty.io/), [Kryo](https://code.google.com/p/kryo/), [Zookeeper](http://zookeeper.apache.org/)


## Getting started


## Documentation


## Licence


## TODO

1 - Glia server correct shutdown
2 - Client correct wait - for a period - something like Future

Look through - FutureTask
http://stackoverflow.com/questions/1247390/java-native-process-timeout/1249984#1249984

http://stackoverflow.com/questions/1234429/best-ways-to-handle-maximum-execution-time-for-threads-in-java

for jump start
http://habrahabr.ru/post/116363/



3 - Need to make an Arquillian test - ot examples for EJB Glia example
4 - Also made some pure POJO integration




-- if You get something like that during deployment on Jboss 7.1.1.Final
 Service jboss.pojo."org.jboss.netty.internal.LoggerConfigurator".DESCRIBED is already registered

Just comment POJO modules in standalone.xml
https://community.jboss.org/wiki/JBPM-530FinalManualDeploymentGuideForBeginner#a_Remove_pojo_and_jpa_modules

a. Remove pojo and jpa modules
Pojo extension
<extension module="org.jboss.as.osgi"/>
<!-- Remove this line extension module="org.jboss.as.pojo"/-->
<extension module="org.jboss.as.remoting"/>
And pojo domain
<subsystem xmlns="urn:jboss:domain:naming:1.0" />
<!--subsystem xmlns="urn:jboss:domain:pojo:1.0" /-->
<subsystem xmlns="urn:jboss:domain:osgi:1.0" activation="lazy">