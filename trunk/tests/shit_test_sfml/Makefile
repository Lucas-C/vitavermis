# Executable créé par défaut :
EXEC		:= PPG_shit_test

# Repertoires du projet : doivent se terminer par un '/'
CMAKE_REP	:= CMake/
DATA_REP	:= data/
DOC_REP		:= html_doc/
SRC_REP		:= src/

# True Linux
CMAKE			:= cmake -G "Unix Makefiles"
CP				:= cp
ISLINUX			:= $(findstring Linux,$(shell uname -o))
LIB_EXT			:= so

ifeq (,$(ISLINUX))
    EXEC		:= $(EXEC).exe
    LIB_EXT		:= dll.a

    ifdef SystemRoot
	# Pure Windows
        RM		:= del /Q
		CMAKE	:= cmake -G "MinGW Makefiles"
		CP		:= copy
# 		SHELL	:= cmd
	else
	# Cygwin
		UNIX_DEF := -D__unix__
    endif
endif


######################################################

.SILENT:

.PHONY: build clean go doc wiz read help
all: build

install-debug:
	@cd $(CMAKE_REP) && $(CMAKE) . -DDEBUG:STRING=1

build: install-debug
	@cd $(CMAKE_REP) && $(MAKE)
#VERBOSE=1

clean:
	@cd $(CMAKE_REP) && $(MAKE) clean || true # to not stop Makefile execution if it fails
	@$(RM) $(CMAKE_REP)CMakeCache.txt $(CMAKE_REP)cmake_install.cmake $(CMAKE_REP)Makefile && $(RM) -r $(CMAKE_REP)CMakeFiles

go: build
	@./$(EXEC)

doc:
	@doxygen $(DOC_REP)Doxyfile

wiz:
	@cd $(DOC_REP) && doxywizard Doxyfile &

read: doc
	@firefox $(DOC_REP)html/index.html &

help:
	@echo "### Makefile targets (default: build)"
	@echo " help"
	@echo " build \t\t : generate the executable '$(EXEC)' (call '$(CMAKE_REP)Makefile')"
	@echo " clean"
	@echo " go \t\t : make '$(EXEC)' and launch it"
	@echo " doc \t\t : generate the documentation"
	@echo " wiz \t\t : access Doxygen configuration pannel"
	@echo " read \t\t : generate the documentation and open it"
