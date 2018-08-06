## no-snapshot Maven Plugin 

no-snapshot is a Maven plugin for removing the **SNAPSHOT** suffix from version within project tag, as in parent, dependency, and plugin tag. The idea is to remove and put the suffix again into version tag whenever needed. I faced this problem while I was evolved with several project at the same time, where the versions were SNAPSHOT until all parts work together correctly. I am tired of removing and put the suffix again in the artefact version into the pom file, I wished a fast solution for solving such problem, then I present this *beta* version.

I will explain how to use this plugin. First off before put the **no-snapshot.artifactId** tag into properties with the artifact name where the suffix(SNAPSHOT) will be removed or placed. The no-snapshot.artifactId tag may appear one or more times, according to your necessity. Let's take this pom.xml file as example:

```xml
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

There are several projects in snapshot stage in progress, which are interdependent. In the perfect world this is not admissible, but I must solve this immediately in short term. I won't get stressed by this, I only wish *"sair do outro lado"* (get it through) as people say in my country.


## remove goal

Let's go through the examples! Remove all the suffixes (SNAPSHOT):
> mvn br.com.educode:no-snapshot:1.0-beta:remove


The output above will be a new file of name **no-snapshot-pom.xml** within the current directory. The outputPomFile property default value is *no-snapshot-pom.xml*. This no-snapshot-pom.xml's result:

```xml
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

Wow! Let's continue  through the examples. Whether you wish to change the filename of no-snapshot-pom.xml to any other use outputPomFile property:
> mvn br.com.educode:no-snapshot:1.0-beta:remove -DpomFile=pom.xml -DoutputPomFile=pom-test.xml


You can overwrite the pom.xml file:
> mvn br.com.educode:no-snapshot:1.0-beta:remove -DpomFile=pom.xml -DoutputPomFile=pom.xml


To only display the content on the screen use the printConsole property, when such property is used none file is generated. The default pomFile property value is pom.xml file:
> mvn br.com.educode:no-snapshot:1.0-beta:remove -DprintConsole=true


The default encode considered is *UTF-8*, but you change to any other, then use the encode property:
> mvn br.com.educode:no-snapshot:1.0-beta:remove -Dencode=ISO-8859-1


no-snapshot-pom.xml file first line:
```xml
<?xml version="1.0" encoding="ISO-8859-1"?>
....
```


You can change the suffix value. This example change the suffix value from SNAPSHOT to beta: 
> mvn br.com.educode:no-snapshot:1.0-beta:remove -Dsuffix=-beta



## put-again goal

Now let's go through the opposite process. Let's suppose which only project00, project02, and project04 continue in development, then other stables projects don't need to be referenced:

```xml
...
<properties>
    <no-snapshot.artifactId>project00</no-snapshot.artifactId>
    <no-snapshot.artifactId>project02</no-snapshot.artifactId>
    <no-snapshot.artifactId>project04</no-snapshot.artifactId>
</properties>
...
```


Let's put the suffix again into the pom file:
> mvn br.com.educode:no-snapshot:1.0-beta:put-again -DpomFile=no-snapshot-pom.xml -DoutputPomFile=put-again-pom.xml


Using different suffix:
> mvn br.com.educode:no-snapshot:1.0-beta:put-again -Dsuffix=-beta -DpomFile=no-snapshot-pom.xml -DoutputPomFile=beta-suffix-pom.xml


You can also print console without needing to generate a new file:
> mvn br.com.educode:no-snapshot:1.0-beta:put-again -DpomFile=no-snapshot-pom.xml -DprintConsole=true


Don't forget the encode property. This is it! You can contact me by **eduardo@educode.com.br**. Thank you.

--

Text revision by Lucas Matias (github.com/lucasjmatias or lucasmatias.87@gmail.com).

