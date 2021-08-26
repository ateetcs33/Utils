#mvn clean install -Dtestsuitefile=CustomTestSuiteGenerator.xml -DtestType=ui -DtestGroup=modificationions -DtestClasses=Redirect,AutoSuggest
# testType="fe"
# testGroup="modification"
# testClasses="RD,AS"

# if [ "$testClasses" == "all" ]
# then
# 	if [[ "$testType" == "ui" && "$testGroup" == "modification" ]]
# 	then
# 	   echo "Run all UI modification tests"
# 	elif [[ "$testType" == "fe" && "$testGroup" == "modification" ]]
# 	then
# 	   echo "Run all FE modification tests"
# 	elif [[ "$testType" == "ui" && "$testGroup" == "creation"  ]]
# 	then
# 	   echo "Run all UI creation tests"
# 	elif [[ "$testType" == "fe" && "$testGroup" == "creation" ]]
# 	then
# 	   echo "Run all FE creation tests"
# 	else
# 		echo "Conditions didn't match"
# 	fi
# else
# 	echo "Run : " $testClasses
# fi


#!/usr/bin/env bash
testType="fe"
testGroup="creation"
testClasses="rd,as"
testsuitefile=""
if [ "$testClasses" == "all" ]
then
	if [[ "$testType" == "ui" && "$testGroup" == "modification" ]]
	then
	   testsuitefile="ModifyTestCasesUI.xml"
	   echo "Run :" $testsuitefile
	elif [[ "$testType" == "fe" && "$testGroup" == "modification" ]]
	then
	   testsuitefile="ModifyTestCasesFE.xml"
	   echo "Run :" $testsuitefile
	elif [[ "$testType" == "ui" && "$testGroup" == "creation" ]]
	then
	   testsuitefile="CreateTestCasesUI.xml"
	   echo "Run :" $testsuitefile
	elif [[ "$testType" == "fe" && "$testGroup" == "creation" ]]
	then
	   testsuitefile="CreateTestCasesFE.xml"
	   echo "Run :" $testsuitefile
	else
		echo "Conditions didn't match"
	fi
else
	echo "Run : " $testClasses
	echo "mvn clean install -Dtestsuitefile=CustomTestSuiteGenerator.xml -DtestType=$testType -DtestGroup=$testGroup -DtestClasses=$testClasses"
	testsuitefile="CustomTestSuite.xml"
	echo "Test type : " $testType " => Test Group : " $testGroup " => Test Classes : " $testClasses
	echo "Run :" $testsuitefile
fi
echo "Final Test suite : " $testsuitefile


