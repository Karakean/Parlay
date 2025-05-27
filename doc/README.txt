In order to run it:
1. Run ericsson_nrgsdk_R5A02.exe file to install NRG server and other components.
2. Java 1.3 will be installed along with it (xD).
3. Set JAVA_HOME environment variable to point to the JDK directory (not to the bin).
4. Add %JAVA_HOME%\bin to your PATH environment variable.
5. Add groupcommunicator module to the examples/ directory.
6. Now we need to compile out groupcommunicator module. We can do it either by:
6.1 Ant
    If you manage to find java 1.3-compliant version, you can do it simply via ant.
    First, replace build.xml with example.build.xml from the doc directory here.
    Then run "ant compile-groupcommunicator" command from within the root directory.
6.2 Manual compilation
    Simply compile it using the following command:
    javac -d out -classpath "./lib/3PP/activation.jar:./lib/3PP/mail.jar:./lib/corba/sun/lib/corba_sun.jar:./lib/coreapi/lib/coreapi.jar:./lib/utilityapi/lib/utilityapi.jar:./examples/tools/cls" examples/groupcommunicator/src/com/ericsson/nrgsdk/examples/applications/groupcommunicator/*.java
    And then run it:
    java -classpath "out:./lib/3PP/activation.jar:./lib/3PP/mail.jar:./lib/corba/sun/lib/corba_sun.jar:./lib/coreapi/lib/coreapi.jar:./lib/utilityapi/lib/utilityapi.jar:./examples/tools/cls" com.ericsson.nrgsdk.examples.applications.groupcommunicator.Main
7. Good luck! (XD)
