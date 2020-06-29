<h1 align="center">WraithEngine</h1>
<p align="center"><i>WraithEngine is a free, open-source, Java-based game engine built on top of LWJGL. It is designed to use high-level API for creating games quickly and easily. It is designed to allow users to be able to browse and modify the engine to fit their needs, and only apply components which are relevant for their projects.</i></p>

<p align="center">
  <img src="https://img.shields.io/github/license/TheDudeFromCI/nodelib" />
  <img src="https://img.shields.io/github/repo-size/TheDudeFromCI/nodelib" />
  <img src="https://img.shields.io/github/issues/TheDudeFromCI/nodelib" />
  <img src="https://img.shields.io/github/v/release/TheDudeFromCI/nodelib?include_prereleases" />
  <a href="https://github.com/TheDudeFromCI/WraithEngine/actions"><img alt="Actions Status" src="https://github.com/TheDudeFromCI/WraithEngine/workflows/CI/badge.svg" /></a>
  <a href="https://sonarcloud.io/dashboard?id=TheDudeFromCI_WraithEngine"><img alt="Quality Gate Status" src="https://sonarcloud.io/api/project_badges/measure?project=TheDudeFromCI_WraithEngine&metric=alert_status" /></a>
  <a href="https://sonarcloud.io/dashboard?id=TheDudeFromCI_WraithEngine"><img alt="Bugs" src="https://sonarcloud.io/api/project_badges/measure?project=TheDudeFromCI_WraithEngine&metric=bugs" /></a>
  <a href="https://sonarcloud.io/dashboard?id=TheDudeFromCI_WraithEngine"><img alt="Coverage" src="https://sonarcloud.io/api/project_badges/measure?project=TheDudeFromCI_WraithEngine&metric=coverage" /></a>
  <a href="https://sonarcloud.io/dashboard?id=TheDudeFromCI_WraithEngine"><img alt="Maintainability Rating" src="https://sonarcloud.io/api/project_badges/measure?project=TheDudeFromCI_WraithEngine&metric=sqale_rating" /></a>
  <a href="https://sonarcloud.io/dashboard?id=TheDudeFromCI_WraithEngine"><img alt="Technical Debt" src="https://sonarcloud.io/api/project_badges/measure?project=TheDudeFromCI_WraithEngine&metric=sqale_index" /></a>
  <a href="https://bettercodehub.com/"><img alt="BCH compliance" src="https://bettercodehub.com/edge/badge/TheDudeFromCI/WraithEngine?branch=master" /></a>
</p>

---

<h2 align="center">A Super Special Thanks To</h2>
<p align="center">
  :star: Mika, Alora Brown, TapirLiu :star:
</p>

<br />

<h3 align="center">And a Warm Thank You To</h3>
<p align="center">
  :rocket: chezhead
 :rocket:
</p>

<br />
<br />

Thank you all for supporting me and helping this project to continue being developed.

<br />

<p>Want to support this project?</p>
<a href="https://www.patreon.com/thedudefromci"><img src="https://c5.patreon.com/external/logo/become_a_patron_button@2x.png" width="150px" /></a>
<a href='https://ko-fi.com/P5P31SKR9' target='_blank'><img height='36' style='border:0px;height:36px;' src='https://cdn.ko-fi.com/cdn/kofi2.png?v=2' border='0' alt='Buy Me a Coffee at ko-fi.com' /></a>

---

#### Java Version

Starting with build 27, Java 13 is now the targeted version being used to compile the project. (Previously version 8.)

## Screenshots

<img src="wiki/spinning-cube.webp" width="250px" />
<img src="wiki/terrain.webp" width="250px" />

## Getting Started

WraithEngine primarily uses Maven for package management, but for users who wish to use their own package management system, the jar file and dependencies can be found in the releases tab.

### Setting Up Maven

If your project uses a Maven workflow, the following dependency should be added to your pom.xml file:

```
<dependency>
    <groupId>net.whg</groupId>
    <artifactId>wraithengine</artifactId>
    <version>BUILD_NUMBER</version>
</dependency>
```

Replace **BUILD_NUMBER** with the build number you wish to use, in the format `build*#`. Or,`build_3` to use build 3, for example.

As WraithEngine uses the Github Package Repository, you must also add this repository to your project in order to access WraithEngine. This can be done by modifying your Maven settings.xml file. This file is a Maven user settings file, which tells Maven useful tips such as what servers to check to resolve dependencies, and account information for logging in to those servers to access them. This file is found at `USER_HOME_FOLDER/.m2/settings.xml`.

For individuals using Linux, this is usually: `/home/USERNAME/.m2/settings.xml`
<br>
For individuals using Windows, this is usually: `C:\Users\USERNAME\.m2\settings.xml`
<br>
For individuals using Mac, this is usually: `/Users/USERNAME/.m2/settings.xml`

If you're using a custom workflow, you can also create a separate settings.xml file specific to your current project. Simply use the `--settings PATH/TO/SETTINGS` flag when calling Maven commands to use a custom settings file.

If the file does not exist, create it. After creating it, you want to add the following repositories and server tags. You should modify your file to look like this. (See profiles and servers) This basically tells Maven where to look for the WraithEngine package at. Due to how Github is set up, you also must have a Github API token created to allow you to download packages remotely, even though they're open source. This can be seen below in the `<servers>` tag.

```
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">

  <activeProfiles>
    <activeProfile>github</activeProfile>
  </activeProfiles>

  <profiles>
    <profile>
      <id>github</id>
      <repositories>
        <repository>
          <id>central</id>
          <url>https://repo1.maven.org/maven2</url>
          <releases><enabled>true</enabled></releases>
          <snapshots><enabled>false</enabled></snapshots>
        </repository>

        <repository>
          <id>github</id>
          <name>GitHub wraithengine Apache Maven Packages</name>
          <url>https://maven.pkg.github.com/wraithengine/WraithEngine</url>
        </repository>
      </repositories>
    </profile>
  </profiles>

  <servers>
    <server>
      <id>github</id>
      <username>USERNAME</username>
      <password>API_TOKEN</password>
    </server>
  </servers>
</settings>
```

Replace `USERNAME` with your Github username in all lowercase letters. Replace `API_TOKEN` with your Github API token.

If you're unsure how to create a Github API token, go into your user settings, and select Developer settings. Select Personal Access Tokens, and generate a new token that specific to the device you are working on. Make sure to give it the "read:packages" flag.

**Note:**
Keep this token secret! This is a basically a password which allows software to access your account. This should be kept a secret! If your API token is ever compromised, delete it and create a new token.

### Jar Download

If your currently workflow does not use Maven, all builds, starting from build 14, can be found in the releases menu about. The libraries which WraithEngine relies on can be found next to the release zip.

### Building From Source

*Requires Maven*

You can build the project from source by simple cloning the project and running:
* `mvn -DskipTests dependency:copy-dependencies package`

in the project directory. The WraithEngine jar file can be found in the `target` folder. All of the dependencies for it will automatically be downloaded into the `target/dependency` folder.

## Project Demos

*Requires Maven*

Various demos for WraithEngine can be found within the `Demos` folder, showing off some of the various features of WraithEngine, as well as how they are used. If you'd like to run these projects yourself, you can clone the project, then run the following command from within the project directory:

* `mvn -DskipTests dependency:copy-dependencies install`
* `mvn -f Demos/pom.xml dependency:copy-dependencies package`

For **Unix** / **Mac**:
* `java -cp "Demos/target/*:Demos/target/dependency/*" DEMO_NAME`

For **Windows**:
* `java -cp "Demos/target/*;Demos/target/dependency/*" DEMO_NAME`

where **DEMO_NAME** is the name of the demo you wish to run, including the path. (I.e `graphics.SpinningCubeDemo` to run the spinning cube demo.)

## Contributing

WraithEngine is open-source and community-driven. All contributions are welcome and highly encouraged! If you see an area of the project which is lacking, feel free to open an issue or submit a pull request. If you want to help but aren't sure where to start:

- Test Coverage

  Extra tests are always awesome! They help prevent potential bugs and improve the stability of areas of the project which might have been overlooked in the past. If you think of some important tests which weren't originally added, feel free to submit them.

- Documentation

  Sometimes documentation is poorly worded or doesn't properly explain what's going on. If you think of a better way of explaining something, or just generally tweaking the grammar and formatting, go for it.

- Bug Fixes

  This is a pretty straight forward one. Bugs happen, and they suck, a lot. But if you have some time to squash a few of them, the community would greatly appreciate it!

- Features and Enhancements

  These are the really big ones, for the brave. If you see a crucial feature on the todo list, that's really taking far too long to be implemented, feel free to get the ball rolling. It keeps things moving and ensures that the most important features actually make it into the project.
