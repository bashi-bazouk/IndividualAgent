from git import *
from os import path

def assert_subtree_exists(name):
  if not path.exists(name):
    error("subtree %s does not exist" % name)

def assert_subtree_does_not_exist(name):
  if path.exists(name):
    error("subtree %s already exists" % name)

def sub_branches_of_subtree(name):
  return [f for f in sub_branches() if f.startswith("ia_%s" % name)]

def map_sub_branch_of_subtree(name, clv):
  sub_branches = sub_branches_of_subtree(name)
  if clv is None and len(sub_branches) is 1:
    print "using %s" % sub_branches[0][4+len(name):]
    return sub_branches[0][4+len(name):]
  else:
    return clv
