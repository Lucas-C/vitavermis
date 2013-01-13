/*!
	@file Macros.hpp
	@brief Macros diverses. Inclu <iostream>
	@author CIMON Lucas
	@date été 2010
*/
#ifndef _DEF_H_Macros
#define _DEF_H_Macros

#include <iostream>

/*!
	@def DISALLOW_COPY_AND_ASSIGN(TypeName)
	@brief A macro to disallow the copy constructor and operator= functions.
	This should be used in the private: declarations for a class
	FROM: http://google-styleguide.googlecode.com/svn/trunk/cppguide.xml
*/
#define DISALLOW_COPY_AND_ASSIGN(TypeName)\
  TypeName(const TypeName&);              \
  void operator=(const TypeName&)
  

/*!
 UNIX colors "\033[3"_c_"m" :
	_c_ = 0 => black
	_c_ = 1 => red
	_c_ = 2 => green
	_c_ = 3 => yellow
	_c_ = 4 => dark blue
	_c_ = 5 => purple
	_c_ = 6 => soft blue
	_c_ = 7 => grey
WINDOWS colors "0x??" :
	bit 0 => foreground intensity
	bit 1 => foreground red
	bit 2 => foreground green
	bit 3 => foreground blue
	bit 4 => background intensity
	bit 5 => background red
	bit 6 => background green
	bit 7 => background blue
 */

/*!
	@def COLOR_STRM_STRING(strm, color, string)
	@brief Put a colored text in a specified ostream, on Linux or Windows.
	Available colors : RED GREEN YELLOW BLUE.
*/
#if defined(__unix__)
	#define RED		"\033[31m"
	#define GREEN	"\033[32m"
	#define YELLOW	"\033[33m"
	#define BLUE	"\033[34m"
	#define END		"\033[0m"
	#define COLOR_STRM_STRING(strm, color, string)	strm << color << string << END
#elif defined(WIN32)
	#define WIN32_LEAN_AND_MEAN
	#include <windows.h>
	static HANDLE hstdout = GetStdHandle( STD_OUTPUT_HANDLE );
	#define RED		0x0C
	#define GREEN	0x02
	#define YELLOW	0x06
	#define BLUE	0x01
	#define END		0x07
	#define COLOR_STRM_STRING(strm, color, string)\
		do {\
			SetConsoleTextAttribute(hstdout, color);\
			strm << string;\
			SetConsoleTextAttribute(hstdout, END);\
		} while (false)
#else
	#define COLOR_STRM_STRING(strm, color, string)	strm << string
#endif


/*!
	@def TRACE(string)
	@brief Track the execution. Append a newline.
 */
#define TRACE(string) 		COLOR_STRM_STRING(std::cout, YELLOW, string << std::endl)

/*!
	@def SHOW_ERROR(string)
	@brief Track execution errors. Append a newline.
 */
#define SHOW_ERROR(string)		COLOR_STRM_STRING(std::cerr, RED, string << std::endl)

/*!
	@def DBG(string)
	@brief Debug track of the execution.
	Deactivated by defining NDEBUG. Append a newline.
 */
#ifdef NDEBUG
	#define DBG(string)		((void)0)
#else
	#define DBG(string) 	COLOR_STRM_STRING(std::cerr, GREEN, string << std::endl)
#endif

/*!
	@def DBG_ONCE(string)
	@brief Debug track of the execution. Printed only once.
	Deactivated by defining NDEBUG. Append a newline.
 */
#define DBG_ONCE(string)\
	do {\
		static bool once = false;\
		if (once) {\
			DBG(string);\
			once = false;\
		}\
	} while(false)

/*!
	@def DUMP(filename, message)
	@brief Debug macro, really useful with 'tail -f'
	Deactivated by defining NDEBUG. Append a newline.
 */
#include <fstream>
#define DUMP(filename, message)\
	if (filename) {\
		static std::ofstream outFile(filename, std::ofstream::app);\
		outFile << message << "\n";\
	}


/*!
	@def ASSERT(test, message)
	@brief Standard assertion with error message
 */
#ifdef NDEBUG
	#define ASSERT(test, message)		((void)0)
#else
	#define ASSERT(test, message)\
		if (!(test)) {\
			SHOW_ERROR(message);\
			exit(42);\
		}
#endif


/*!
	@def FOREACH_IT(stl_iterator, container, var)
	@brief A macro to loop through every standard STL container.
	The container need to have an operator++(), begin() & end() members.
*/
#define FOREACH_IT(stl_iterator, container, var)\
	for (std::stl_iterator (var) = (container).begin();\
	(var) != (container).end();\
	++(var))

/*!
	@def FOREACH(stl_type, container, var)
	@brief FOREACH_IT specialisation for containers with an \b iterator typedef.
*/
#define FOREACH(stl_type, container, var) FOREACH_IT(stl_type::iterator, container, var)

/*!
	@def FOREACH_C(stl_type, container, var)
	@brief FOREACH_IT specialisation for containers with an \b const_iterator typedef.
*/
#define FOREACH_C(stl_type, container, var) FOREACH_IT(stl_type::const_iterator, container, var)


/*!
	@def F_EQUAL(a,b)
	@brief Compare \em small floats
 */
#include <cmath>
#define EPSILON 0.0001
#define F_EQUAL(a,b) (fabs((a) - (b)) < EPSILON)


/*!
	@def DO_PRAGMA(x)
	@brief Apply a compiler pragma
 */
/*!
	@def TODO(string)
	@brief Track the compilation and print TODO messages, deactivated by defining NDEBUG
 */
#ifdef NDEBUG
	#define TODO(string)		((void)0)
#else
	#define DO_PRAGMA(x)		_Pragma (#x)
	#define TODO(string) 		DO_PRAGMA(message ("TODO - " string))
#endif


#endif // _DEF_H_Macros
