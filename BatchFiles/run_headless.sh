#!/usr/bin/env bash

# Help function
if [[ ("$1" == "-h") || ("$1" == "--help") ]]; then
  echo "This script is used to run selenium integration tests for the dashboard using headless chrome"
  echo "You can run the suite by ./run_headless.sh <test suite name.xml>"
  exit 0
fi

echo "Which user : "
whoami
#install maven
sudo pwd
if [ ! -e apache-maven-3.6.3-bin.tar.gz ]
then
    sudo wget https://www-us.apache.org/dist/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
fi
sudo tar -xvzf apache-maven-3.6.3-bin.tar.gz
sudo mv apache-maven-3.6.3 maven
export M2_HOME=$PWD/maven
export PATH=${M2_HOME}/bin:${PATH}
echo updated maven Version =
mvn -version

#echo "Docker test: "
#sudo ps -Af | grep chrome
#echo "Docker test end: "
#
#echo "launch Google chrome : "
#google-chrome --headless --disable-gpu
#whereis google-chrome

#echo "launch chrome : "
#chrome --headless --disable-gpu
#whereis chrome

#
#echo "Killing chrome"
#sudo killall chromedriver
#sudo killall google-chrome
#echo "Killed chrome"
#
#echo "After killing test: "
#sudo ps -Af | grep chrome
#echo "Docker test end: "

# Run the display driver
#sudo Xvfb :99 -ac -screen 0 1024x768x24 &
#disown $1
#export DISPLAY=:99

#sudo apt-get remove -y  xvfb
#
#sudo apt-get install -y xvfb --force-yes
#
#sudo apt-get install -y  xfonts-100dpi xfonts-75dpi xfonts-cyrillic  dbus-x11 --force-yes
#
#echo "##### Starting X virtual framebuffer (Xvfb) in background..."
#xdpyinfo -display :99 >/dev/null 2>&1 && echo "Xvfb @99 is in use" || echo "Xvfb @99 is free"
#Xvfb -ac :99 -screen 0 1280x1024x16 &
#export DISPLAY=:99
#xdpyinfo -display :99 >/dev/null 2>&1 && echo "Xvfb @99 is in use" || echo "Xvfb @99 is free"

#echo "Install Chrome Driver ....."
#cd /tmp/
#LATEST=$(wget -q -O - http://chromedriver.storage.googleapis.com/LATEST_RELEASE)
#sudo wget https://chromedriver.storage.googleapis.com/$LATEST/chromedriver_linux64.zip
#sudo unzip chromedriver_linux64.zip
#sudo mv chromedriver /usr/bin/chromedriver
#echo "Chrome Driver version : "
#echo $(chromedriver --version)
##echo "Fetched Chrome Driver version"
#
#cd ..
#echo "Un-install chromium"
#sudo apt-get remove google-chrome-stable
#rm -rf ~/.config/google-chrome-stable
#rm -rf ~/.cache/google-chrome-stable
#sudo rm -rf /etc/google-chrome-stable  

echo "Install Google Chrome ....."
#sudo curl https://intoli.com/install-google-chrome.sh | bash
#sudo mv /usr/bin/google-chrome-stable /usr/bin/google-chrome
#echo "Installed Google Chrome ..... "
#
#echo "Google Chrome version : "
#echo $(google-chrome --version && which google-chrome)

echo "Install Google Chrome ....."

#sudo apt-get install chromium-browser
#install Chrome
#if [ ! -e 	google-chrome-stable_current_amd64.deb ]
#then
#    wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | sudo apt-key add -
#fi
#wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | sudo apt-key add -
##sudo sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list'
#sudo sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" | sudo tee /etc/apt/sources.list.d/google-chrome.list'
#sudo apt-get update
#sudo apt-get install -f google-chrome-stable
#echo "Google Chrome version : "
#echo $(google-chrome --version && which google-chrome)

#echo "##### Downloading latest ChromeDriver..."
#LATEST=$(wget -q -O - http://chromedriver.storage.googleapis.com/LATEST_RELEASE)
#echo "Downloading Chrome version : " $LATEST
##if [ ! -e 	chromedriver_linux64.zip ]
##then
##    sudo wget http://chromedriver.storage.googleapis.com/$LATEST/chromedriver_linux64.zip
##fi
#sudo wget http://chromedriver.storage.googleapis.com/$LATEST/chromedriver_linux64.zip
#echo "##### Extracting and symlinking chromedriver to PATH so it's available globally"
#sudo unzip -o chromedriver_linux64.zip && sudo ln -sf $PWD/chromedriver /usr/local/bin/chromedriver

#echo "Deleting the Downloaded chrome zip file:"
#rm chromedriver_linux64.zip

# Run the display driver
#Xvfb -ac :99 -screen 0 1280x1024x16 &
#disown $1
#export DISPLAY=:99

#sudo Xvfb :0 -ac -screen 0 1024x768x24 &
#disown $1
#export DISPLAY=:0:0


#file="google-chrome-stable_current_amd64.deb"
#
#echo ""
#echo "Installing dependencies ...... Please wait..."
#echo ""
#
#sudo dpkg --configure -a
#sudo apt-get update
#
#sudo  sudo apt-get -f -y install
#
#echo ""
#echo "Installing XVFB for headless automation.."
#echo ""

#
#sudo apt-get remove -y  xvfb
#
#sudo apt-get install -y xvfb --force-yes
#
#sudo apt-get install -y  xfonts-100dpi xfonts-75dpi xfonts-cyrillic  dbus-x11 --force-yes

#echo "##### Starting X virtual framebuffer (Xvfb) in background..."
#xdpyinfo -display :99 >/dev/null 2>&1 && echo "Xvfb @99 is in use" || echo "Xvfb @99 is free"
#Xvfb -ac :99 -screen 0 1280x1024x16 &
#export DISPLAY=:99
#xdpyinfo -display :99 >/dev/null 2>&1 && echo "Xvfb @99 is in use" || echo "Xvfb @99 is free"
#
#echo ""
#echo "Installing dependencies for chrome ...."
#echo ""
#
#
#sudo apt-get install -y libxss1 libappindicator1 libindicator7 libxtst6 gconf-service gconf2-common libasound2 libgconf-2-4 --force-yes
#echo "Checking if  chrome exists...."
#
#if [ -f $file ] ; then
#    rm $file
#fi
#
#echo ""
#echo "Installing google chrome...."
#echo ""
#
#wget -q https://www.dropbox.com/s/zqeq8qewq9d2ol6/google-chrome-stable_current_amd64.deb
#
#sudo dpkg -i google-chrome*.deb
#
#
#
#export DBUS_SESSION_BUS_ADDRESS=/dev/null
#echo $DBUS_SESSION_BUS_ADDRESS

# Run the test suite
echo "Test Suite name passed from shell script : " $XMLSUITEFILE
echo "Realm Name from shell script : " $REALM
cd $WORKSPACE/dashboard-qa/
mvn clean test -Dheadless=true -Dtestsuitefile="$XMLSUITEFILE"