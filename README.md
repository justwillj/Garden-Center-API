# Description

Garden Center API is a REST API that can serve data to a customer facing mobile and web applications. This is a crucial part of the web application that will allow everything to work in unison. This project contains unit and integration testing that can be performed to make sure everything is working as intended. It also contains a link to a PostMan collection to test each of the endpoints.
t

## PostMan Collection Link

This is the link to the PostMan collection I used to test each of my endpoints.

## Testing

There are a number of unit test that have been written to test the implementation of each method.

To run test for each of the methods:
1. There will be a folder named test/java which will have another folder io.catalyte.training
2. Open the LogicProblemslmplTest and you will be about to right-click or click the green arrow by the method you wish to test. For example averageGoodTest is testing the average method.
3. Once you run the test at the bottom a dialog should appear and indicate if the test passed or not.
4. If you wish to run all the test at once you can right-click on the LogicProblemsImplTest or click the green arrow by it.

## Testing with Coverage
You can also run the test with coverage to see which of them are covered and maybe not covered. To
run the test with coverage follow these steps:
1. Open on the testing folder and go into LogicProblemsImplTest file
2. Next to LogicProblemsImplTest there will be a green arrow click on this
3. Select Run 'LogicProblemsImplTest' with Coverage
4. You can also do the same thing for each of the individual test it is the same process


## Pre-requisites


## Usage


## Linting
To lint the project you can open up any of the Java files and git the following keys: crtl+alt+l.
This is assuming that you have the Google's IntelliJ linter, and it is configured.

To add the Google's IntelliJ Java style xml file:
1. Go to this link and download the file https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml
2. Open up IntelliJ and open the Java CodeStyle window under settings ~> editor ~> code style ~>Java (crtl+alt+s).
3. Click on the gear icon next to scheme
4. Select import shceme and pick IntelliJ IDEA code style XML
5. Find the Google's IntelliJ Java style xml and click apply.
