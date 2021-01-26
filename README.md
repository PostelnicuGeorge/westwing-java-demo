# Westwing java demo project

## Synopsis

This project is using Java, Maven and Cucumber to run the feature file for task 2.
The pdf for the task 1 can be found in src/test/resources/Task1.pdf

## Make
After cloning the repo to a machine, to make this project work there the following options:
- **_Run it locally with Maven + Java:_**
    ```
    mvn -s "/path/to/apache-maven-3.6.3/conf/settings.xml" -Dmaven.repo.local="/path/to/apache-maven-3.6.3/repository/westwing-java-demo" test -Dbrowser=chrome -DgridUrl=http://localhost:4444/wd/hub/
    ```
  - Where:
    - browser is chrome or firefox, any other string will default to chrome;
    - gridUrl is the endpoint of the selenium grid of any kind.

- **_On a machine with docker installed:_**
    ```
    sudo docker run -it --rm --name my-maven-project --net host -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven maven:3.3-jdk-8 mvn test -Dbrowser=chrome -DgridUrl=http://127.0.0.1:4444/wd/hub/
    ```
  - Where the same cli arguments are given, but everything is ran in a docker container on the standard [maven image](https://hub.docker.com/_/maven) 
  where we mount the local current directory $(pwd) to internal /usr/src/mymaven as a volume and set the working directory
  to the same
  
  - If the docker machine is linux and the grid is deployed locally --net host is necessary to access the host machine
  network and 127.0.0.1 instead of localhost
  
  - To also deploy a docker selenium grid before starting the tests we need to quickly execute the steps from the 
  [seleniumHQ official image](https://github.com/SeleniumHQ/docker-selenium/tree/selenium-3#selenium-grid-hub-and-nodes)
  ```
  sudo docker network create grid
  sudo docker run -d -p 4444:4444 --net grid --name selenium-hub selenium/hub:3.141.59-20210105
  sudo docker run -d --net grid -e HUB_HOST=selenium-hub -v /dev/shm:/dev/shm selenium/node-chrome:3.141.59-20210105
  sudo docker run -d --net grid -e HUB_HOST=selenium-hub -v /dev/shm:/dev/shm selenium/node-firefox:3.141.59-20210105
  sudo docker run -d --net grid -e HUB_HOST=selenium-hub -v /dev/shm:/dev/shm selenium/node-opera:3.141.59-20210105
  ```
## Apologies
I know the task 2 was supposed to be done in JavaScript + webdriver.io which is the closest to my skills but it would have
taken too long to setup and do the same code maturity as here.