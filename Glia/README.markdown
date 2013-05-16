# Glia - is a nervous system of you cluster

* Glia is distributed system for cluster node communication.
* Glia is simple, compact, fast, scalable, highly available, supports POJO and EJB model.
* Glia could be run as a standalone console application or inside any container (Servlet, EJB, Spring & etc.)
* Glia could be utilized by any JVM based language (Groovy, Scala, Clojure, JRuby, Jython & etc.)


Wiki definition of the word glia.

Glia (Greek γλία, γλοία "glue"; pronounced in English as either /ˈɡliːə/ or /ˈɡlaɪə/), are non-neuronal cells that maintain homeostasis, form myelin, and provide support and protection for neurons in the brain, and for neurons in other parts of the nervous system such as in the autonomic nervous system

Glia using [Netty](http://netty.io/), [Kryo](https://code.google.com/p/kryo/), [Curator](https://github.com/Netflix/curator), [Zookeeper](http://zookeeper.apache.org/)


#### Artifacts

Right now only in local TTK Nexus

groupId: com.reversemind

artifactId: glia-core

version: 1.2.7-SNAPSHOT


<dependency>
  <groupId>com.reversemind</groupId>
  <artifactId>glia-core</artifactId>
  <version>1.2.7-SNAPSHOT</version>
</dependency>


#### Getting started


#### Documentation
Documentation and tutorials on the [Glia Wiki](https://github.com/dendrite/viscosity/wiki/Glia-Wiki)

#### Logging
 Glia uses [SLF4J](http://www.slf4j.org/) for logging. SLF4J is a facade over logging that allows you to plug in any (or no) logging framework.

#### License

The use and distribution terms for this software are covered by the Apache License, Version 2.0 (http://opensource.org/licenses/Apache-2.0) which can be found in the file LICENSE.html at the root of this distribution. By using this software in any fashion, you are agreeing to be bound by the terms of this license. You must not remove this notice, or any other, from this software.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

#### TODO



* [DONE] - but some shift to pure JavaBean in builder pattern - Need to fix Spring context loader


* Need Netflix Curator fake zookeeper instance - for Testing

* What about Snappy-java

* Remove Sigar into new abstraction - 'cause GliaServer is very affectable
* [DONE] - Make a GliaServer & GliaClient Builder or Factory - 'cause number of constructors grows very rapidly

* Detect server host name - tested in - but some troubles with selecting correct values if a lot of net interfaces is up
* ?? Add to GliaClientDiscoverer - select server each time before send method

* Additional options for client speed optimisation - http://docs.jboss.org/netty/3.2/api/org/jboss/netty/bootstrap/ClientBootstrap.html
* In ProxyFactory - use non-static GliaClient
* Replace in ProxyFactory Interface class into String name of that interface class
* Add a control to server through zookeeper
* How to get from server number of client connected??

* Need look through all cases - but first situation looks pretty good
* CASE: if Server will send but Client is down?
* Need to make a stress loading from a lot of clients

* Need to make an Arquillian test - ot examples for EJB Glia example

* [DONE] - Add CPU load to the server metrics
* [DONE] - Add to GliaClient or create a GliaClientDiscoverer - just automatically to find a necessary server for communication
* [DONE] - Update metrics dynamically in zookeeper
* [DONE] - Add to ServerMetadata a Metrics

* [DONE] - Add metrics to the server like a heartbeats & so on...

* [DONE] - Autodiscover server in zookeeper
* [DONE] - Glia server correct shutdown

* [DONE] Client correct wait - for a period - something like Future
* [DONE] for example a JSON sending - * 4 - Also made some pure POJO example integration





Fetures for JBoss 7.1.1 deployment

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




Look through - FutureTask

http://stackoverflow.com/questions/1247390/java-native-process-timeout/1249984#1249984

http://stackoverflow.com/questions/1234429/best-ways-to-handle-maximum-execution-time-for-threads-in-java

for jump start = http://habrahabr.ru/post/116363/

