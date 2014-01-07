#The Individual Agent Workbench

The IA workbench is designed for making full-stack contributions to the Individual Agent project.

It is recommended to add the `bin` folder to your `PATH` when working with IAW.

### Commands
*  `sbt "start server"` - start the client overlay service

*  `sbt "start client"` - start the UI client

*  `sbt "pull <subProject> [origin | upstream]"` - pull in sub-project changes from the upstream or origin repositories (see Application.conf)

*  `sbt "push <subProject>"` - push sub-project changes to origin

### Sub-Project Dependencies
*  Maven

*  RabbitMQ

*  MongoDB

*  Scala

*  JDK


###TODO
*  Automatically open pull requests from subproject branch to subproject repository.

*  Make a front-page for the project.