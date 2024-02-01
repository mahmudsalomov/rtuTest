@echo off
set "roclink_process=Roclink.exe"
set "python_script_path=C:\forwarder\forwarder.py"
set "script_started=0"

:loop
tasklist /fi "IMAGENAME eq %roclink_process%" | find /i "%roclink_process%" >nul && (
    tasklist /fi "IMAGENAME eq python3.12.exe" | find /i "python3.12.exe" >nul &&(
	echo Roclink is running. Stopping Python script.
	taskkill /f /im python3.12.exe || echo Error: Failed to stop Python script
    )
    set "script_started=0"
) || (
tasklist /fi "IMAGENAME eq python3.12.exe" | find /i "python3.12.exe" >nul ||(
	set "script_started=0"
)
    if "%script_started%"=="0" (
        echo Roclink is not running. Starting Python script.
        start "" "%python_script_path%" || echo Error: Failed to start Python script
        set "script_started=1"
    )
)

choice /t 5 /d y /n >nul
goto loop