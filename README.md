# Description

Garden Center API is a REST API that can serve data to a customer facing mobile and web
applications. This is a crucial part of the web application that will allow everything to work in
unison. This project contains unit and integration testing that can be performed to make sure
everything is working as intended. It also contains a link to a PostMan collection to test each of
the endpoints.

## PostMan Collection Link

This is the link to the PostMan collection I used to test each of my endpoints.

## Testing

There are a number of unit test that have been written to test the implementation of each method.

To run test for each of the methods:

1. There will be a folder named test/java which will have another folder edu.midlands.training
2. Open the service folder, and you will be about to right-click or click the green arrow by the
   class you wish to test. For example UserServiceImplTest is testing the UserServiceImpl.
3. Once you run the tests at the bottom a dialog should appear and indicate if the tests passed or
   not.
4. If you wish to run all the test at once you can right-click on the classTest you wish to test or
   click the green arrow by it.

## Testing with Coverage

You can also run the test with coverage to see which of them are covered and maybe not covered. To
run the test with coverage follow these steps:

1. Open on the testing folder and go into one of the services testing files (for example
   userServiceImplTest)
2. Next to test you have clicked on there will be a green arrow click on this
3. Select Run 'testFileName' with Coverage
4. You can also do the same thing for each of the individual test it is the same process
5. You can do repeat these steps for each of the 4 testing classes to see the coverage

## Running Integration Tests

1. Start the container.
2. Look Under src\test\java\edu\midlands\training for the controllers package.
3. Right click controllers and run tests in edu.midlands.training.controllers. 

Integration tests cover payload, content type, and status code tests of all 2XX
Code coverage can be viewed by right-clicking on the controller package and selecting Run with
Coverage.
Coverage can also be run subsequently in the top right corner white and green shield symbol.
Real data is manipulated in my integration tests.

## Pre-requisites

Make sure that your postgres database is available and configured with the following options:

POSTGRES_USER=user
POSTGRES_PASSWORD=password123
PORT=5432

The DataLoader class in the data package will load a few examples of each entity into the
database after the service starts up.

You will need to also have IntelliJ installed and will need to import this as a maven project for
everything to run.

## Usage

This API will go hand in hand with our future web application that we will be creating to allow
customers to place orders without leaving the office. This REST API allows us to store and retrieve
all the important information we will need to make this possible. It will allow us to server data
to the customer facing mobile and web applications.

## Linting

To lint the project you can open up any of the Java files and git the following keys: crtl+alt+l.
This is assuming that you have the Google's IntelliJ linter, and it is configured.

To add the Google's IntelliJ Java style xml file:

1. Go to this link and download the
   file https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml
2. Open up IntelliJ and open the Java CodeStyle window under settings ~> editor ~> code style ~>
   Java (crtl+alt+s).
3. Click on the gear icon next to scheme
4. Select import shceme and pick IntelliJ IDEA code style XML
5. Find the Google's IntelliJ Java style xml and click apply.
