@echo off
title upload changes

setlocal enabledelayedexpansion
set bf=".metadata" ".recommenders"
set blocked=%bf%
for %%i in (*) do set blocked=!blocked! "%%i"
::this for loop looks for files (not dirs) in the repo and adds them to blocked
set bc=0
for %%i in (%blocked%) do set /a "bc=!bc!+1"
::used to find number of items in list
set dirlist=
for /f "tokens=* USEBACKQ" %%i in (`dir /b`) do (
	set count=0
	for %%e in (%blocked%) do (
		if not "%%i"==%%e (
			set /a "count=!count!+1"
		)
		::checks item with current item in blocked list, if they dont match, then it adds 1 to count
		if !count!==!bc! (
			::if the current line has been checked through every blocked line count/total, then let it pass ::
			set dirlist=!dirlist! "%%i"
		)
	)
)
set list=!dirlist!
echo Here are the folders that will be checked:
echo #########################################
for %%i in (%list%) do echo %%i
echo #########################################
echo Are these all the folders you want to commit changes to?
echo if not, then answer n and fix it or ask a mentor for help
set /p prompt=commit folders? [Y/n]: 
if not !prompt!==Y (goto :eof)
set /p comment=Set commit comment: 
for %%i in (%list%) do (
	git add %%i
)
git commit -m "%comment%"
git push
endlocal