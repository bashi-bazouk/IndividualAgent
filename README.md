#The Individual Agent Workbench

The IA workbench is designed for making full-stack contributions to the Individual Agent project.

It is recommended to add the `bin` folder to your `PATH` when working with IAW.

### Commands
*  `make workbench` - load the official subprojects.

*  `ia add project <name> <git-repository>` - add any other repository as a subproject.

*  `ia add subtree <name> <branch>` - add a project to the workbench.

*  `ia pull subtree <name> <branch>` - download updates from a subproject repository

*  `ia push subtree <name> <branch>` - push updates to a subproject branch

*  `ia remove subtree <name>` - remove a project from the workbench (keep cache)

*  `ia remove project <name>` - remove a subproject from the workbench (delete cache)

*  `make clean` - removes all (official) subprojects from the workbench.

### IAW Dependencies
*  Python

### Official SubProject Dependencies
*  Maven

*  RabbitMQ

*  MongoDB

*  Scala

*  JDK


###TODO
*  `ia pull` and `ia push` need to be validated as working on a project (need commits)

*  Extend make clean to remove all subprojects.

*  Automatically open pull requests from subproject branch to subproject repository.

*  Make a front-page for the project.

*  Make a wiki for the project.

*  Make a .gitignore for the project.