bash -c "cd /mnt/c/Users/joeri/eclipse-workspace/Doclet/ && javadoc -docletpath ./target/classes -doclet test.doclet.ListClasses -private -sourcepath /mnt/c/Users/joeri/Desktop/Doclet/openjdk-8u40-src-b25-10_feb_2015/openjdk/jdk/src/share/classes/ -subpackages com.oracle.net com.sun.awt com.sun.beans com.sun.beans.decoder com.sun.beans.editors com.sun.beans.finder com.sun.beans.infos com.sun.beans.util com.sun.provider com.sun.demo.jvmti com.sun.imageio.plugins.bmp com.sun.imageio.plugins.common com.sun.imageio.plugins.gif com.sun.imageio.plugins.jpeg com.sun.imageio.plugins.png com.sun.imageio.plugins.wbmp com.sun.imageio.spi com.sun.imageio.stream com.sun.jarsigner com.sun.java.browser.dom com.sun.java.browser.net com.sun.java.swing com.sun.java.swing.plaf.gtk com.sun.java.swing.plaf.motif com.sun.java.swing.plaf.nimbus com.sun.java.swing.plaf.windows com.sun.java.util.jar.pack com.sun.jdi com.sun.jdi.connect com.sun.jdi.connect.spi com.sun.jdi.event com.sun.jdi.request com.sun.jmx.defaults com.sun.jmx.intercepter com.sun.jmx.mbeanserver com.sun.jmx.remote.internal com.sun.jmx.remote.protocol.iiop com.sun.jmx.remote.protocol.rmi com.sun.jmx.remote.security com.sun.jmx.remote.util com.sun.jmx.snmp com.sun.jmx.snmp.agent com.sun.jmx.snmp.daemon com.sun.jmx.snmp.defaults com.sun.jmx.snmp.internal com.sun.jmx.snmp.IPAcl com.sun.jmx.snmp.mpm com.sun.jmx.snmp.tasks com.sun.jndi.cosnaming com.sun.jndi.dns com.sun.jndi.ldap com.sun.jndi.ldap.ext com.sun.jndi.ldap.pool com.sun.jndi.ldap.sasl com.sun.jndi.rmi.registry com.sun.jndi.toolkit.corba com.sun.jndi.toolkit.ctx com.sun.jndi.toolkit.dir com.sun.jndi.toolkit.url com.sun.jndi.url.corbaname com.sun.jndi.url.dns com.sun.jndi.url.iiop com.sun.jndi.url.iiopname com.sun.jndi.url.ldap com.sun.jndi.url.ldaps com.sun.jndi.url.rmi com.sun.management com.sun.media.sound com.sun.naming.internal com.sun.net.httpserver com.sun.net.httpserver.spi com.sun.net.ssl com.sun.net.ssl.internal.ssl com.sun.net.ssl.internal.www.protocol.https com.sun.nio.file com.sun.nio.sctp com.sun.org.apache.xml.internal.security com.sun.org.apache.xml.security.algorithms com.sun.org.apache.xml.security.algorithms.implementations com.sun.org.apache.xml.internal.security.c14n com.sun.org.apache.xml.internal.security.c14n.helper com.sun.org.apache.xml.internal.security.c14n.implementations com.sun.org.apache.xml.internal.security.encryption com.sun.org.apache.xml.internal.security.exceptions com.sun.org.apache.xml.internal.security.keys com.sun.org.apache.xml.internal.security.keys.content com.sun.org.apache.xml.internal.security.keys.content.keyvalues com.sun.org.apache.xml.internal.security.keys.content.x509 com.sun.org.apache.xml.internal.security.keys.keyresolver com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations com.sun.org.apache.xml.internal.security.keys.storage com.sun.org.apache.xml.internal.security.keys.storage.implementations com.sun.org.apache.xml.internal.security.signature com.sun.org.apache.xml.internal.security.signature.reference com.sun.org.apache.xml.internal.security.transforms com.sun.org.apache.xml.internal.security.transforms.implementations com.sun.org.apache.xml.internal.security.transforms.params com.sun.org.apache.xml.internal.security.utils com.sun.org.apache.xml.internal.security.utils.resolver com.sun.org.apache.xml.internal.security.utils.resolver.implementations com.sun.pept com.sun.pept.encoding com.sun.pept.ept com.sun.pept.presentation com.sun.pept.transport com.sun.rmi.rmid com.sun.rowset com.sun.rowset.internal com.sun.rowset.providers com.sun.security.auth com.sun.security.auth.callback com.sun.security.auth.login com.sun.security.auth.module com.sun.security.cert.internal.x509 com.sun.security.jgss com.sun.security.ntlm com.sun.security.sasl com.sun.security.sasl.digest com.sun.security.sasl.gsskerb com.sun.security.sasl.ntlm com.sun.security.sasl.util com.sun.tools.attach com.sun.tools.attach.spi com.sun.tools.example.debug.bdi com.sun.tools.example.debug.event com.sun.tools.example.debug.expr com.sun.tools.example.debug.gui com.sun.tools.example.debug.tty com.sun.tools.example.trace com.sun.tools.extcheck com.sun.tools.hat com.sun.tools.hat.internal.model com.sun.tools.hat.internal.oql com.sun.tools.hat.internal.parser com.sun.tools.hat.internal.server com.sun.tools.hat.internal.util com.sun.tools.jconsole com.sun.tools.jdi com.sun.tools.script.shell com.sun.tracing com.sun.tracing.dtrace java.applet java.awt java.awt.color java.awt.datatransfer java.awt.dnd java.awt.dnd.peer java.awt.event java.awt.font java.awt.geom java.awt.im java.awt.spi java.awt.spi java.awt.image java.awt.image.renderable java.awt.peer java.awt.print java.beans java.beans.beancontext java.io java.lang java.lang.annotation java.lang.instrument java.lang.invoke java.lang.management java.lang.ref java.lang.reflect java.math java.net java.nio java.nio.channels java.nio.channels.spi java.nio.charset java.nio.charset.spi java.nio.file java.nio.file.attribute java.nio.file.spi java.rmi.activation java.rmi.dgc java.rmi.registry java.rmi.server java.security java.security.acl java.security.cert java.security.interfaces java.security.spec java.sql java.text java.text.spi java.time java.time.chrono java.time.format java.time.temporal java.time.zone java.util java.util.concurrent java.util.concurrent.atomic java.util.concurrent.locks java.util.function java.util.jar java.util.logging java.util.prefs java.util.regex java.util.spi java.util.stream java.util.zip javax.accessibility javax.crypto javax.crypto.interfaces javax.crypto.interfaces javax.crypto.spec javax.imageio javax.imageio.event javax.imageio.metadata javax.imageio.plugins.bmp javax.imageio.plugins.jpeg javax.imageio.spi javax.imageio.stream javax.management javax.management.loading javax.management.modelmbean javax.management.monitor javax.management.openmbean javax.management.relation javax.management.remote javax.management.remote.rpi javax.management.timer javax.naming javax.naming.directory javax.naming.event javax.naming.ldap javax.naming.spi javax.net javax.net.ssl javax.print javax.print.attribute javax.print.attribute.standard javax.print.event javax.rmi.ssl javax.script javax.security.auth javax.security.auth.callback javax.security.auth.callback javax.security.auth.kerberos javax.security.auth.login javax.security.auth.spi javax.security.auth.x500 javax.security.cert javax.security.sasl javax.smartcardio javax.sound.midi javax.sound.midi.spi javax.sound.sampled javax.sound.sampled.spi javax.sql javax.sql.rowset javax.sql.rowset.serial java.sql.rowset.spi javax.swing javax.swing.border javax.swing.colorchooser javax.swing.event javax.swing.filechooser javax.swing.plaf javax.swing.plaf.basic javax.swing.plaf.metal javax.swing.plaf.multi javax.swing.plaf.nimbus javax.swing.plaf.synth javax.swing.table javax.swing.text javax.swing.text.html javax.swing.text.html.parser javax.swing.text.rtf javax.swing.tree javax.swing.undo javax.xml.crypto javax.xml.crypto.dom javax.xml.crypto.dsig javax.xml.crypto.dsig.dom javax.xml.crypto.dsig.keyinfo javax.xml.crypto.spec jdk.internal.org.objectweb.asm jdk.internal.org.objectweb.asm.commons jdk.internal.org.objectweb.asm.signature jdk.internal.org.objectweb.asm.tree jdk.internal.org.objectweb.asm.tree.analysis jdk.internal.org.objectweb.asm.util jdk.internal.org.xml.sax jdk.internal.org.xml.sax.helpers jdk.internal.util.xml jdk.internal.util.xml.impl jdk.net org.ietf.jgss org.jcp.xml.dsig.internal org.jcp.xml.dsig.internal.dom sun.applet sun.applet.resources sun.audio sun.awt sun.awt.datatransfer sun.awt.dnd sun.awt.event sun.awt.geom sun.awt.im sun.awt.image sun.awt.shell sun.awt.util sun.dc sun.font sun.instrument sun.invoke sun.invoke.anon sun.invoke.empty sun.invoke.util sun.java2d sun.java2d.lcms sun.java2d.loops sun.java2d.opengl sun.java2d.pipe sun.java2d.pipe.hw sun.java2d.pisces sun.jvmstat.monitor sun.jvmstat.monitor.event sun.jvmstat.monitor.remote sun.jvmstat.perfdata.monitor sun.jvmstat.perfdata.monitor.protocol.file sun.jvmstat.perfdata.monitor.protocol.local sun.jvmstat.perfdata.monitor.protocol.rmi sun.jvmstat.perfdata.monitor.v1_0 sun.jvmstat.perfdata.monitor.v2_0 sun.launcher sun.management sun.management.counter sun.management.counter.perf sun.management.jdp sun.management.jmxremote sun.management.snmp sun.management.snmp.jvminstr sun.management.snmp.jvmmib sun.management.snmp.util sun.misc sun.misc.resources sun.net sun.net.dns sun.net.ftp sun.net.ftp.impl sun.net.httpserver sun.net.idn sun.net.sdp sun.net.smtp sun.net.spi sun.net.spi.nameservice sun.net.spi.nameservice.dns sun.net.util sun.net.www sun.net.www.content.audio sun.net.www.content.image sun.net.www.content.text sun.net.www.http sun.net.www.protocol.file sun.net.www.protocol.ftp sun.net.www.protocol.http sun.net.www.protocol.http.logging sun.net.www.protocol.http.ntlm sun.net.www.protocol.http.spnego sun.net.www.protocol.https sun.net.www.protocol.jar sun.net.www.protocol.mailto sun.net.www.protocol.netdoc sun.nio sun.nio.ch sun.nio.ch.sctp sun.nio.cs sun.nio.cs.ext sun.nio.fs sun.print sun.reflect sun.reflect.annotation sun.reflect.generics.factory sun.reflect.generics sun.reflect.generics.parser sun.reflect.generics.reflectiveObjects sun.reflect.generics.repository sun.reflect.generics.scope sun.reflect.generics.tree sun.reflect.generics.visitor sun.reflect.misc sun.rmi.log sun.rmi.registry sun.rmi.rmic sun.rmi.rmic.newrmic sun.rmi.rmic.newrmic.jrmp sun.rmi.runtime sun.rmi.server sun.rmi.transport sun.rmi.transport.proxy sun.rmi.transport.tcp sun.security.acl sun.security.action sun.security.ec sun.security.internal.interfaces sun.security.internal.spec sun.security.jca sun.security.jgss sun.security.jgss.krb5 sun.security.jgss.spi sun.security.jgss.spnego sun.security.jgss.wrapper sun.security.krb5 sun.security.krb5.internal sun.security.krb5.internal.ccache sun.security.krb5.internal.crypto sun.security.krb5.internal.crypto.dk sun.security.krb5.internal.ktab sun.security.krb5.internal.rcache sun.security.krb5.internal.util sun.security.pkcs sun.security.pkcs10 sun.security.pkcs11 sun.security.pkcs11.wrapper sun.security.pkcs12 sun.security.provider sun.security.provider.certpath sun.security.provider.certpath.ldap sun.security.provider.certpath.ssl sun.security.rsa sun.security.smartcardio sun.security.ssl sun.security.ssl.krb5 sun.security.timestamp sun.security.tools sun.security.tools.jarsigner sun.security.tools.keytool sun.security.tools.policytool sun.security.util sun.security.validator sun.security.x509 sun.swing sun.swing.icon sun.swing.plaf sun.swing.plaf.synth sun.swing.plaf.windows sun.swing.table sun.swing.text sun.swing.text.html sun.text sun.text.bidi sun.text.normalizer sun.text.resources sun.text.resources.ar sun.text.resources.be sun.text.resources.bg sun.text.resources.ca sun.text.resources.cs sun.text.resources.da sun.text.resources.de sun.text.resources.el sun.text.resources.en sun.text.resources.es sun.text.resources.et sun.text.resources.fi sun.text.resources.fr sun.text.resources.ga sun.text.resources.hi sun.text.resources.hr sun.text.resources.hu sun.text.resources.in sun.text.resources.is sun.text.resources.it sun.text.resources.iw sun.text.resources.ja sun.text.resources.ko sun.text.resources.lt sun.text.resources.lv sun.text.resources.mk sun.text.resources.ms sun.text.resources.mt sun.text.resources.nl sun.text.resources.no sun.text.resources.pl sun.text.resources.pt sun.text.resources.ro sun.text.resources.ru sun.text.resources.sk sun.text.resources.sl sun.text.resources.sq sun.text.resources.sr sun.text.resources.sv sun.text.resources.th sun.text.resources.tr sun.text.resources.uk sun.text.resources.vi sun.text.resources.zh sun.tools.asm sun.tools.attach sun.tools.jar sun.tools.java sun.tools.javac sun.tools.jcmd sun.tools.jconsole sun.tools.jconsole.inspector sun.tools.jinfo sun.tools.jmap sun.tools.jps sun.tools.jstack sun.tools.jstat sun.tools.jstatd sun.tools.native2ascii sun.tools.native2ascii.resources sun.tools.serialver sun.tools.tree sun.tools.util sun.tracing sun.tracing.dtrace sun.util sun.util.calendar sun.util.cldr sun.util.locale sun.util.locale.provider sun.util.logging sun.util.resources sun.util.resources.de sun.util.resources.en sun.util.resources.es sun.util.resources.fr sun.util.resources.hi sun.util.resources.it sun.util.resources.ja sun.util.resources.ko sun.util.resources.pt sun.util.resources.sv sun.util.resources.zh sun.util.spi sun.util.xml java -exclude lang.doc-files > ./bash_output.txt"