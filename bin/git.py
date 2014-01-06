from os import listdir, stat, path
from iautil import *
# general                                                                                                        
def assert_clean():
  tmp = "/tmp/check_git"
  with open(tmp, 'w+') as stdout:
    sh("git diff --exit-code", stdout=stdout)
    if stat(tmp)[6] != 0:
      # file is non-empty -> changes need to be committed                                                        
      error("Commit files before using this command.")

def quick_commit(*args):
  sh("git add -A")
  if len(args) is 0:
    sh("""git commit -m "quickcommit""")
  else:
    sh("git commit -m \"%s\"" % args.join(" "))

# remotes                                                                                                        
def remotes():
  return [f for f in listdir(".git/refs/remotes/") if not path.isfile(".git/refs/remotes/%s" % f)]

def assert_remote_exists(name):
  if not name in remotes():
    error("remote %s does not exist" % name)

def assert_remote_does_not_exist(name):
  if name in remotes():
    error("remote %s already exists" % name)

# sub_branches                                                                                                   
def current_branch():
  return open(".git/HEAD").read().strip().split("/")[-1]

def sub_branches():
  return [f for f in listdir(".git/refs/heads/") if f.startswith("ia_")]

def sub_branch_exists(name, sub_branch):
  return "ia_%s_%s" % (name, sub_branch) in sub_branches()

def assert_sub_branch_exists(name, sub_branch):
  if not sub_branch_exists(name, sub_branch):
    error("branch %s_%s does not exist" % (name, sub_branch))

def assert_sub_branch_does_not_exist(name, sub_branch):
  if sub_branch_exists(name, sub_branch):
    error("branch %s_%s already exists" % (name, sub_branch))

def create_or_update_sub_branch(name, sub_branch):
  branch = current_branch()
  if sub_branch_exists(name, sub_branch):
    sh("git checkout ia_%s_%s" % (name, sub_branch))
    sh("git pull")
  else:
    sh("git checkout -b ia_%s_%s %s/%s" % (name, sub_branch, name, sub_branch))
  sh("git checkout %s" % branch)

def remove_sub_branch(name, sub_branch):
  assert_sub_branch_exists(name, sub_branch)
  sh("git branch -D ia_%s_%s" % (name, sub_branch))
