# thesis-kafka-endec
Following are the steps to setup the Dev Environment
1. Download and install Intellij IDEA Open source edition
2. Install the Intellij IDEA Scala Plugin
a) Click on Menu Item File
b) Click on Settings
c) Click on Plugins
d) Click on Browse Repositories
e) Search for Scala and install the plugin
3. Check out Kafka source 1.1 at git@github.com:apache/kafka.git
4. Update libraries and generate Intellij project files
a) Go to the home directory of kafka project
b) Run the command Gradle
c) Run the script ./gradlew idea
5. Do a full build Gradle jar
