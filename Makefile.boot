# Copyright 1997-1998, 2005-2010 Free Software Foundation, Inc.
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 3, or (at your option)
# any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.

# Define for DOS/WIN (not including DJGPP):
#OBJEXT = obj
#EXEEXT = .exe
EXEEXT =
OBJEXT = o

# Source of grep.
grep_OBJS = \
      grep.$(OBJEXT) \
      dfasearch.$(OBJEXT) \
      kwsearch.$(OBJEXT) \
      pcresearch.$(OBJEXT) \
      main.$(OBJEXT) \
      kwset.$(OBJEXT) \
      dfa.$(OBJEXT) \
      searchutils.$(OBJEXT) \
      $(LIB_OBJS)
egrep_OBJS = \
      egrep.$(OBJEXT) \
      dfasearch.$(OBJEXT) \
      kwsearch.$(OBJEXT) \
      pcresearch.$(OBJEXT) \
      main.$(OBJEXT) \
      kwset.$(OBJEXT) \
      dfa.$(OBJEXT) \
      searchutils.$(OBJEXT) \
      $(LIB_OBJS)
fgrep_OBJS = \
      fgrep.$(OBJEXT) \
      kwsearch.$(OBJEXT) \
      main.$(OBJEXT) \
      kwset.$(OBJEXT) \
      searchutils.$(OBJEXT) \
      $(LIB_OBJS)

# Supporting routines.
LIB_OBJS_core =  \
      ../lib/argmatch.$(OBJEXT) \
      ../lib/c-ctype.$(OBJEXT) \
      close-stream.$(OBJEXT) \
      ../lib/closeout.$(OBJEXT) \
      ../lib/error.$(OBJEXT) \
      ../lib/exclude.$(OBJEXT) \
      ../lib/exitfail.$(OBJEXT) \
      ../lib/hard-locale.$(OBJEXT) \
      ../lib/hash.$(OBJEXT) \
      ../lib/isdir.$(OBJEXT) \
      ../lib/mbchar.$(OBJEXT) \
      ../lib/mbscasecmp.$(OBJEXT) \
      ../lib/progname.$(OBJEXT) \
      ../lib/quotearg.$(OBJEXT) \
      ../lib/quote.$(OBJEXT) \
      ../lib/regex.$(OBJEXT) \
      ../lib/savedir.$(OBJEXT) \
      ../lib/strnlen1.$(OBJEXT) \
      ../lib/xalloc-die.$(OBJEXT) \
      ../lib/xmalloc.$(OBJEXT) \
      ../lib/xstrtol.$(OBJEXT) \
      ../lib/xstrtoul.$(OBJEXT)

# Comment out functions already supported as needed.
#LIB_OBJ_atexit   =  ../lib/atexit.$(OBJEXT)
#LIB_OBJ_alloca   =  ../lib/alloca.$(OBJEXT)
#LIB_OBJ_fnmatch  =  ../lib/fnmatch.$(OBJEXT)
LIB_OBJ_getopt   =  ../lib/getopt.$(OBJEXT) ../lib/getopt1.$(OBJEXT)
#LIB_OBJ_memchr   =  ../lib/memchr.$(OBJEXT)
LIB_OBJ_obstack  =  ../lib/obstack.$(OBJEXT)
#LIB_OBJ_strtoul  =  ../lib/strtoul.$(OBJEXT)

LIB_OBJS = $(LIB_OBJS_core) $(LIB_OBJ_atexit) $(LIB_OBJ_alloca) \
           $(LIB_OBJ_fnmatch) $(LIB_OBJ_getopt) $(LIB_OBJ_memchr) \
           $(LIB_OBJ_obstack) $(LIB_OBJ_strtoul)

# Where is DIR and opendir/readdir defined.
#  or -DHAVE_DIRENT_H
#  or -DHAVE_SYS_NDIR_H
#  or -DHAVE_SYS_DIR_H
#  or -DHAVE_NDIR_H
#

# default flags
DEFS_core = \
           -DHAVE_DIRENT_H \
           -Dconst= \
           -D__restrict= -D__restrict__= \
           -Dintmax_t='long' -Dxstrtoumax=xstrtoul

# Solaris
#DEFS_solaris = -DSTDC_HEADERS -DHAVE_DIRENT_H -DHAVE_STRERROR

# DOS/WIN (change also OBJEXT/EXEEXT, see above)
# DOS/DJGPP
#DEFS_dos = -DSTDC_HEADERS -DHAVE_DIRENT_H \
#           -DHAVE_DOS_FILE_CONTENTS -DHAVE_DOS_FILE_NAMES -DHAVE_UNISTD_H

# No wchar support
# DEFS_wchar =  -Dwchar_t=int -Dmbstate_t=int
# DEFS_wchar =  -DHAVE_WCHAR_H

# Is strtol() and strtoul()  declarared ?
#DEFS_strtol = -DHAVE_DECL_STRTOULL=0 -DHAVE_DECL_STRTOUL=0
DEFS_strtol = -DHAVE_DECL_STRTOULL=1 -DHAVE_DECL_STRTOUL=1

DEFS = $(DEFS_core) $(DEFS_wchar) $(DEFS_strtol) \
       -DCHAR_BIT=8 -DSTDOUT_FILENO=1 -D_GNU_SOURCE \
       -D_REGEX_LARGE_OFFSETS \
       -D_GL_UNUSED= -D_UNUSED_PARAMETER_= \


####

CFLAGS = $(DEFS) -I. -I../lib -I../build-aux \
	 -DVERSION=\"bootstrap\" -DPACKAGE=\"grep\" \
	 -DPACKAGE_STRING=\"grep\ bootstrap\" \
	 -DPACKAGE_BUGREPORT=\"bug-grep@gnu.org\" \

PROGS = grep$(EXEEXT) egrep$(EXEEXT) fgrep$(EXEEXT)

CLEANFILES = configmake.h config.h getopt.h fpending.h close-stream.c

all : $(PROGS)

configmake.h:
	:> configmake.h

config.h:
	:> config.h

getopt.h: ../lib/getopt.in.h
	sed '/# *if/ s/@[^@]*@/0/g; /@[^@]*@/d' ../lib/getopt.in.h > getopt.h

fpending.h:
	echo '#define __fpending(f) 0' > fpending.h

close-stream.c: ../lib/close-stream.c
	cp ../lib/close-stream.c close-stream.c

$(grep_OBJS): config.h \
	configmake.h \
	getopt.h \
	fpending.h

grep$(EXEEXT)  :  $(grep_OBJS)
	$(CC)     $(grep_OBJS) -o  grep

egrep$(EXEEXT) : $(egrep_OBJS)
	$(CC)    $(egrep_OBJS) -o egrep

fgrep$(EXEEXT) : $(fgrep_OBJS)
	$(CC)    $(fgrep_OBJS) -o fgrep

clean :
	rm -f $(grep_OBJS) $(egrep_OBJS) $(fgrep_OBJS) $(PROGS) $(CLEANFILES)
