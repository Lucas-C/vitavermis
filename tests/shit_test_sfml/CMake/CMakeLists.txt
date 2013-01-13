cmake_minimum_required(VERSION 2.6)

project(PPG_shit_test)

###############
# Variables d'environnement
###############
set(ROOT_DIR_PATH ${CMAKE_SOURCE_DIR}/..)
set(LIBRARY_OUTPUT_PATH ${ROOT_DIR_PATH})
set(EXECUTABLE_OUTPUT_PATH ${ROOT_DIR_PATH})
set(SRC_DIR_PATH ${ROOT_DIR_PATH}/src)
set(LIB_DIR_PATH ${ROOT_DIR_PATH}/lib)
include_directories(SYSTEM ${ROOT_DIR_PATH}/include)
include_directories(${SRC_DIR_PATH})
set(DEPEND_LIB sfml-graphics sfml-system sfml-window)

###############
# Dependance à l'OS et l'architecture
###############
# (FROM: http://www.openguru.com/2009/04/cmake-detecting-platformoperating.html)
if (CMAKE_SYSTEM_NAME STREQUAL "Windows")
	message("OS Windows detected")
	set(DEPEND_LIB sfml-graphics-2 sfml-system-2 sfml-window-2)
	link_directories(${ROOT_DIR_PATH} ${LIB_DIR_PATH}/win32)
else()
	message("OS Linux supposed")
	if(NOT CMAKE_SIZEOF_VOID_P)
		message ("*** Architecture possible detection error ***")
	endif()
	if(CMAKE_SIZEOF_VOID_P EQUAL 8)
		message("Architecture 64bits detected")
		link_directories(${ROOT_DIR_PATH} ${LIB_DIR_PATH}/x64)
	else()
		message("Architecture 32bits detected")
		link_directories(${ROOT_DIR_PATH} ${LIB_DIR_PATH}/x32)
	endif()
endif()

###############
# Wildcards
###############
file(
	GLOB_RECURSE
	src_files
	${SRC_DIR_PATH}/*/*.hpp
	${SRC_DIR_PATH}/*/*.cpp
)
file(
	GLOB
	exec_src_files
	${SRC_DIR_PATH}/*.hpp
	${SRC_DIR_PATH}/*.cpp
)

###############
# Mode debug/release
###############
if(DEBUG)
	message("Debug version")
	add_definitions(-g -DFAT_DEBUG -Wall -Wextra -pedantic-errors -Wfloat-equal -Wshadow  -Wconversion -Weffc++)
else()
	message("Release version")
	add_definitions(-O3)
endif()

###############
# Génération de l'exécutable
###############
add_executable(
	${PROJECT_NAME}
	${exec_src_files}
#	${src_files}
)
# Dépendances
target_link_libraries(
	${PROJECT_NAME}
	${DEPEND_LIB}
)
