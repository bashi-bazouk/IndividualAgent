.PHONY: clean print

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


make2: bin/ia
	$(IA) add project specialk https://github.com/leithaus/SpecialK.git
	$(IA) add subtree specialk
	$(IA) add project gloseval https://github.com/leithaus/GLoSEval.git
	$(IA) add subtree gloseval

remove2: bin/ia
	$(IA) remove project specialk
	$(IA) remove project gloseval