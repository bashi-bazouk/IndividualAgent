import subprocess
import sys
import os

class IAException(Exception):
  pass

def error(msg):
  raise IAException(msg)

def sh(command, **kwargs):
  return subprocess.call(command.split(" "), **kwargs)

def cd(dir):
  os.chdir(dir)

def subcommand(commands):
  try:
    subcommand = commands
    index = 1
    #  Invariant: subcommand is a dictionary AND there exist more arguments
    while type(subcommand) is dict and index < len(sys.argv):
      subcommand = subcommand[sys.argv[index]]
      index += 1
    # ~Invariant: subcommand is not a dictionary OR there exist no more arguments

    if type(subcommand) is dict and index == len(sys.argv):
      # subcommand is a dictionary -> command is incomplete
      def pprint(item, indent="  "):
        if type(item) is dict:
          for key, value in item.iteritems():
            if type(value) is dict:
              print "%s%s:\n" % (indent, key)
              pprint(value, indent="  %s" % indent)
            else:
              print"%s%s\n" % (indent, key)

      print "Usage:"
      print " ".join(sys.argv[1:])
      pprint(subcommand)
    else:
      # subcommand is not a dictionary -> command is complete
      subcommand(*sys.argv[index:])
  except IAException as e:
    print "ERROR: %s" % e.args
