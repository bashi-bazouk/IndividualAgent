.PHONY: clean workbench zmq mongrel2

IA=bin/ia

workbench: bin/ia
	$(IA) add project haxe_util https://github.com/iruffner/haxe.git
	$(IA) add project specialk https://github.com/leithaus/SpecialK.git
	$(IA) add project diesel https://github.com/rlamb/Agent-Service-ATI-IA.git
	$(IA) add project gloseval https://github.com/leithaus/GLoSEval.git
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


zmq:
	$(IA) add project zmq https://github.com/zeromq/libzmq.git
	$(IA) add subtree zmq
	cd zmq; ./autogen.sh; ./configure; make; sudo make install
	$(IA) remove subtree zmq

mongrel2:
	$(IA) add project mongrel2 https://github.com/bashi-bazouk/mongrel2.git
	$(IA) add subtree mongrel2
	cd mongrel2
	make
	sudo make install
	cd ..
	$(IA) remove subtree mongrel2

