#The Individual Agent Workbench

The IA workbench is designed for making full-stack contributions to the Individual Agent project.

It is recommended to add the `bin` folder to your `PATH` when working with IAW.

### Commands
*  `make workbench` - load the official subprojects.

*  `ia add project <name> <git-repository>` - add any other repository as a subproject.

*  `ia pull subtree <name> <branch>` - download updates from a subproject repository

*  `ia push subtree <name> <branch>` - push updates to a subproject branch

*  `ia remove project <name>` - remove a subproject from the workbench (delete cache)

*  `ia remove subtree <name>` - remove a project from the workbench (keep cache)

*  `ia add subtree <name>` - restore a project to the workbench from cache.

*  `make clean` - removes all (official) subprojects from the workbench.


###TODO
*  `ia pull` and `ia push` need to be validated as working on a project (need commits)

*  Extend make clean to remove all subprojects.

*  Automatically open pull requests from subproject branch to subproject repository.
