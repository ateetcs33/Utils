#!/usr/bin/env bash
cd /Users/bloomreach/br_qa_automation/qa-automation/dashboard-qa/docker/dashboard-qa/docker
./start_docker.sh
./stop_docker.sh


#!/usr/bin/env bash

# Help function
if [[ ("$1" == "-h") || ("$1" == "--help") ]]; then
  echo "This script is used to run selenium integration tests for the dashboard using headless chrome"
  echo "You can run the suite by ./run_headless.sh <test suite name.xml>"
  exit 0
fi

#install maven
sudo pwd
if [ ! -e apache-maven-3.6.0-bin.tar.gz ]
then
    sudo wget https://www-us.apache.org/dist/maven/maven-3/3.6.0/binaries/apache-maven-3.6.0-bin.tar.gz
fi
sudo tar -xvzf apache-maven-3.6.0-bin.tar.gz
sudo mv apache-maven-3.6.0 maven
export M2_HOME=$PWD/maven
export PATH=${M2_HOME}/bin:${PATH}
echo updated maven Version =
mvn -version

#install Chrome
if [ ! -e 	google-chrome-stable_current_amd64.deb ]
then
    wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | sudo apt-key add -
fi
#sudo sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list'
sudo sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" | sudo tee /etc/apt/sources.list.d/google-chrome.list'
sudo apt-get update
sudo apt-get install google-chrome-stable

# Run the display driver
Xvfb -ac :99 -screen 0 1280x1024x16 &
disown $1
export DISPLAY=:99

# Run the test suite
echo "Test Suite name passed from shell script : " $XMLSUITEFILE
cd $WORKSPACE/src/qa/dashboard-qa/
mvn clean test -Dheadless=true -Dtestsuitefile="$XMLSUITEFILE" -Drealm="$REALM"