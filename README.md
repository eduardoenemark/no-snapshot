## no-snapshot Maven Plugin 

no-snapshot is a Maven plugin for remove *-SNAPSHOT* suffix of version into project tag, also in parent, dependency, and plugin tag. The idea is remove and put again suffix in version tag when always that need. I faced with the problem that I has evolved several project some time what was becoming SNAPSHOT until that all parts work together. I am tired remove and put again suffix in the artefact version in the pom file, I will wish immediate something for solve such problem, then here the *beta* version.

I will explain with used the plugin. First before of all put **no-snapshot.artifactId** tag in properties with artifact name that will remove or put suffix (SNAPSHOT). The no-snapshot.artifactId tag can appear one or many times, this of agreed with your need. Let's take pom example (this file name is pom.xml):

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>br.com.educode</groupId>
    <artifactId>project02</artifactId>
    <version>1.0-SNAPSHOT</version>
    <parent>
        <groupId>br.com.educode</groupId>
        <artifactId>project00</artifactId>
        <version>2.0-SNAPSHOT</version>
    </parent>
    <properties>
        <java.version>1.8</java.version>
        <no-snapshot.artifactId>project00</no-snapshot.artifactId>
        <no-snapshot.artifactId>project01</no-snapshot.artifactId>
        <no-snapshot.artifactId>project02</no-snapshot.artifactId>
        <no-snapshot.artifactId>project03</no-snapshot.artifactId>
        <no-snapshot.artifactId>project04</no-snapshot.artifactId>
        <no-snapshot.artifactId>project05</no-snapshot.artifactId>
    </properties>
    <dependencies>
        <dependency>
            <groupId>br.com.educode</groupId>
            <artifactId>project03</artifactId>
            <version>1.2.3-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>br.com.educode</groupId>
            <artifactId>project04</artifactId>
            <version>5.1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>br.com.educode</groupId>
                <artifactId>project05</artifactId>
                <version>2.20.1-SNAPSHOT</version>
                <dependencies>
                    <dependency>
                        <groupId>br.com.educode</groupId>
                        <artifactId>project01</artifactId>
                        <version>2.20.1-SNAPSHOT</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>
</project>
```

There are several projects in progressing. In the perfect world this not admissible, but I have that solve immediately into curt time. I not stress by this, only wish *"sair do outro lado"* (to out of the other side) how the peoples talk in my country.



#### remove goal

Let's go to examples! Remove all suffix (SNAPSHOT):
```
mvn br.com.educode:no-snapshot:1.0-beta:remove -DpomFile=pom.xml
```


The output above will a new file of name **no-snapshot-pom.xml** into current directory. The outputPomFile property default value is *no-snapshot-pom.xml*. This no-snapshot-pom.xml's result:
```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>br.com.educode</groupId>
    <artifactId>project02</artifactId>
    <version>1.0</version>
    <parent>
        <groupId>br.com.educode</groupId>
        <artifactId>project00</artifactId>
        <version>2.0</version>
    </parent>
    <properties>
        <java.version>1.8</java.version>
        <no-snapshot.artifactId>project00</no-snapshot.artifactId>
        <no-snapshot.artifactId>project01</no-snapshot.artifactId>
        <no-snapshot.artifactId>project02</no-snapshot.artifactId>
        <no-snapshot.artifactId>project03</no-snapshot.artifactId>
        <no-snapshot.artifactId>project04</no-snapshot.artifactId>
        <no-snapshot.artifactId>project05</no-snapshot.artifactId>
    </properties>
    <dependencies>
        <dependency>
            <groupId>br.com.educode</groupId>
            <artifactId>project03</artifactId>
            <version>1.2.3</version>
        </dependency>
        <dependency>
            <groupId>br.com.educode</groupId>
            <artifactId>project04</artifactId>
            <version>5.1.0</version>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>br.com.educode</groupId>
                <artifactId>project05</artifactId>
                <version>2.20.1</version>
                <dependencies>
                    <dependency>
                        <groupId>br.com.educode</groupId>
                        <artifactId>project01</artifactId>
                        <version>2.20.1</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>
</project>
```


Wow! Let's continue the examples. Whether you wish change filename of no-snapshot-pom.xml to any other use outputPomFile property:
```
mvn br.com.educode:no-snapshot:1.0-beta:remove -DpomFile=pom.xml -DoutputPomFile=pom-test.xml
```


You can overwrite own pom.xml file:
```
mvn br.com.educode:no-snapshot:1.0-beta:remove -DpomFile=pom.xml -DoutputPomFile=pom.xml
```


Only display at screen by printConsole property, when use such property not generate none file. The pomFile property default value is pom.xml file:
```
mvn br.com.educode:no-snapshot:1.0-beta:remove -DprintConsole=true
```


The default encode considered is *UTF-8*, but you change to other, then use encode property:
```
mvn br.com.educode:no-snapshot:1.0-beta:remove -Dencode=ISO-8859-1
```


no-snapshot-pom.xml file first line:
```
<?xml version="1.0" encoding="ISO-8859-1"?>
....
```


You can change suffix value. This example change -NO-SNAPSHOT to -beta, only occurrence of -beta will change: 
```
mvn br.com.educode:no-snapshot:1.0-beta:remove -Dsuffix=-beta
```



#### put-again goal

Now let's go view the opposite process. I let suppose what only project00, project02, and project04 continue in development, then other stables projects not need be referenced:
```
...
<properties>
    <java.version>1.8</java.version>
    <no-snapshot.artifactId>project00</no-snapshot.artifactId>
    <no-snapshot.artifactId>project02</no-snapshot.artifactId>
    <no-snapshot.artifactId>project04</no-snapshot.artifactId>
</properties>
...
```


Let's put again suffix into pom file:
```
mvn br.com.educode:no-snapshot:1.0-beta:put-again -DpomFile=no-snapshot-pom.xml -DoutputPomFile=put-again-pom.xml
```


Using different suffix:
```
mvn br.com.educode:no-snapshot:1.0-beta:put-again -Dsuffix=-beta -DpomFile=no-snapshot-pom.xml -DoutputPomFile=beta-suffix-pom.xml
```


You can also print console without need generate a new file:
```
mvn br.com.educode:no-snapshot:1.0-beta:put-again -DpomFile=no-snapshot-pom.xml -DprintConsole=true
```


You not forget encode property. This is! You can talk me by **eduardo@educode.com.br**. Thank you.
