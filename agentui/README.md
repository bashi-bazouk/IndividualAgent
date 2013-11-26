


Step 1) Download and install HaXe 3 (it is fairly benign install and installs HaXe)

Step 2) make sure haxe is available from the command line
 % haxe                                                                                                                               
Haxe Compiler 3.0.0 - (C)2005-2013 Haxe Foundation
 Usage : haxe.exe -main <class> [-swf|-js|-neko|-php|-cpp|-as3] <output> [options]
 Options :
  -cp <path> : add a directory to find source files
  -js <file> : compile code to JavaScript file
  -swf <file> : compile code to Flash SWF file
  -as3 <directory> : generate AS3 code into target directory
  -neko <file> : compile code to Neko Binary
  -php <directory> : generate PHP code into target directory
  -cpp <directory> : generate C++ code into target directory
  -cs <directory> : generate C# code into target directory
  -java <directory> : generate Java code into target directory
  -xml <file> : generate XML types description
  -main <class> : select startup class
  -lib <library[:version]> : use a haxelib library
  -D <var> : define a conditional compilation flag
  -v : turn on verbose mode
  -debug : add debug information to the compiled code
  -help  Display this list of options
  --help  Display this list of options

Step 3) goto your checkout/clone of the agentui code.  For me that is cd ~/code/agentui   From there go into the client directory cd client
        Note that a checkout of Emeris' haxe common (https://github.com/iruffner/haxe.git) will be needed as well, with a symlink from [agentui]client/haxe_src/m3 -> [haxe]/m3

Step 4) build it haxe build.hxml (here is my output)

 % haxe build.hxml                                                                                                     
haxe_src/ui/widget/LabelTree.hx:20: lines 20-69 : Warning : Warning : maybe loop in static generation of ui.widget.LabelTree

and then the pudding

 % ls -alp js/AgentUi.js                                                 
-rw-r--r--  1 glen  staff  247340 Mar 16 08:45 js/AgentUi.js



Things to note...

* Currently the client folder is the actual docroot so you can copy that to a web server and point your browser to agentui.html and have a running client
* Where can we share tidbits like this, do we have a wiki or a mailing list?
* If the build fails the  js/AgentUi.js is deleted.  So after running the build the existence of js/AgentUi.js is the best inidicator of success|failure
* We currently have people building and developing using HaXe on windows, max osx and linux.
* I think everyone's favorite dev environment is sublime http://www.sublimetext.com/ 
* We do have a unit test framework with unit tests on many of these components, it is just in another set of projects (and there is more plumbing since we run our unit tests in a browser over http, we have just found it better to test as close to the metal as possible)
