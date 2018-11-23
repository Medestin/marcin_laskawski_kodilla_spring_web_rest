call runcrud.bat
if "%ERRORLEVEL%" == "0" goto runmozilla
echo runcrud.bat failed
goto fail

:runmozilla
"C:\Program Files (x86)\Mozilla Firefox\firefox.exe" "http://localhost:8080/crud/v1/tasks/"
if "%ERRORLEVEL%" == "0" goto end
echo There were issues while opening browser
goto fail

:fail
echo.
echo There were errors.

:end
echo finished executing showtasks.bat