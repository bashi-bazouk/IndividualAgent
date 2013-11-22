.PHONY: clean print

workbench: ia
	./ia add project haxe_util https://github.com/iruffner/haxe.git
	./ia add project specialk https://github.com/leithaus/SpecialK.git
	./ia add project diesel https://github.com/rlamb/Agent-Service-ATI-IA.git
	./ia add project gloseval https://github.com/leithaus/GLoSEval.git
	./ia add project agentui https://github.com/iruffner/agentui.git
	./ia add subtree haxe_util
	./ia add subtree specialk
	./ia add subtree diesel cryptoRedo
	./ia add subtree gloseval cryptoRedo
	./ia add subtree agentui

clean: ia
	./ia remove project haxe_util
	./ia remove project specialk
	./ia remove project diesel
	./ia remove project gloseval
	./ia remove project agentui


make2: ia
	./ia add project specialk https://github.com/leithaus/SpecialK.git
	./ia add subtree specialk
	./ia add project gloseval https://github.com/leithaus/GLoSEval.git
	./ia add subtree gloseval

remove2: ia
	./ia remove project specialk
	./ia remove project gloseval