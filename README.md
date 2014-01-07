#The Individual Agent Workbench

The IA workbench is designed for making full-stack contributions to the Individual Agent project.  The Workbench standardizes routines for pulling in projects, making changes, and submitting reviews.

### Commands

*  `sbt <subProject>/compile` - build a subProject

*  `sbt compile` - Build the entire project.

*  `sbt clean` - Clean the project.

*  `sbt "start server"` - Start the client overlay.

*  `sbt "start client"` - Start the client.

*  `sbt "pull <subProject> [origin | upstream]"` - Pull in sub-project changes from the upstream or origin repositories (see Application.conf).

*  `sbt "push <subProject>"` - Push sub-project changes to origin.


### Workbench Iteration

The Workbench is designed around a standard team-based development procedure:

1.  The developer pulls in updates from a subproject using `sbt "pull <subProject> upstream"`.

2.  Developer integrates a new feature, and pushes it to a fork using `sbt "push <subProject>"`.

3.  The developer submits a Pull Request on Github, which notifies the team via e-mail.

4.  The team accepts the Pull Request, merging the code back into the upstream repository.

### Setting Up

The only thing you have to do to take ownership of the IA Workbench takes place in the `Application.conf` file.  There you will see that each sub-project has it's own description, containing `origin`, `upstream`, and `branch` items.  You'll need to set each `origin` to reflect a fork of it's sub-project which you'll have created on Github beforehand.  Right now, the origins point to the net-softarc forks, which are owned by bashi-bazouk.

### Sub-Project Dependencies
*  Maven

*  RabbitMQ

*  MongoDB

*  Scala

*  JDK


###TODO
*  Automatically open pull requests from subproject branch to subproject repository.

*  Make a front-page for the project.