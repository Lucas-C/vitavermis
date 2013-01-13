@echo OFF
set PATH=%PATH%;%cd%\lib\win32\
PPG_shit_test.exe 100 1000
if ERRORLEVEL 1 pause
