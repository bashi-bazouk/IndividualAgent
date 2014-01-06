.PHONY: clean workbench zmq mongrel2 ubuntu specialk diesel

IA=bin/ia

workbench: bin/ia
	$(IA) add project haxe_util https://github.com/iruffner/haxe.git
	$(IA) add project specialk https://github.com/bashi-bazouk/SpecialK.git
	$(IA) add project diesel https://github.com/bashi-bazouk/Agent-Service-ATI-IA.git
	$(IA) add project gloseval https://github.com/bashi-bazouk/GLoSEval.git
	$(IA) add project agentui https://github.com/iruffner/agentui.git
	$(IA) add subtree haxe_util
	$(IA) add subtree specialk
	$(IA) add subtree diesel cryptoRedo
	$(IA) add subtree gloseval cryptoRedo
	$(IA) add subtree agentui

clean: bin/ia
	$(IA) remove project haxe_util
	$(IA) remove project specialk
	$(IA) remove project diesel
	$(IA) remove project gloseval
	$(IA) remove project agentui

ubuntu:
	apt-get update
	apt-get upgrade
	apt-get install automake
	apt-get install autoconf
	apt-get install scala
	apt-get install python
	apt-get install python-dev
	apt-get install python-setuptools
	apt-get install maven
	apt-get install sqlite3
	apt-get install libsqlite3-dev

/usr/local/include/zmq.h:
	$(IA) add project zmq https://github.com/zeromq/libzmq.git
	$(IA) add subtree zmq
	cd zmq; ./autogen.sh; ./configure; make; sudo make install
	$(IA) remove subtree zmq

zmq: /usr/local/include/zmq.h
	easy_install pyzmq

mongrel2: /usr/local/include/zmq.h
	$(IA) add project mongrel2 https://github.com/bashi-bazouk/mongrel2.git
	$(IA) add subtree mongrel2
	cd mongrel2; make; sudo make install
	$(IA) remove subtree mongrel2

specialk:
	cd specialk; mvn clean install -Dskiptests

diesel:
	cd diesel/AgentServices-Store; mvn clean install -Dskiptests
