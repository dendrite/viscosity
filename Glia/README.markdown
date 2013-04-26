# Glia - is a nervous system of you cluster

* Glia is distributed system for cluster node communication.
* Glia is simple, compact, fast, scalable, highly available, supports POJO and EJB model.
* Glia could be run as a standalone console application or inside any container (Servlet, EJB, Spring & etc.)
* Glia could be utilized by any JVM based language (Groovy, Scala, Clojure, JRuby, Jython & etc.)


Wiki definition of the word glia.

Glia (Greek γλία, γλοία "glue"; pronounced in English as either /ˈɡliːə/ or /ˈɡlaɪə/), are non-neuronal cells that maintain homeostasis, form myelin, and provide support and protection for neurons in the brain, and for neurons in other parts of the nervous system such as in the autonomic nervous system

Glia using [Netty](http://netty.io/), [Kryo](https://code.google.com/p/kryo/), [Zookeeper](http://zookeeper.apache.org/)


#### Getting started


#### Documentation
Documentation and tutorials on the [Glia Wiki](https://github.com/dendrite/viscosity/wiki/Glia-Wiki)

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

* Glia server correct shutdown

* How to get from server number of client connected??

* Client correct wait - for a period - something like Future
** Need look through all cases - but first situation looks pretty good
** CASE: if Server will send but Clint is down?
** Need to make a stress loading from a lot of clients


* 3 - Need to make an Arquillian test - ot examples for EJB Glia example

* 4 - Also made some pure POJO example integration


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