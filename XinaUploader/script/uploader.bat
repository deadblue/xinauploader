@ECHO OFF

SET UPLOADER_HOME="%~dp0"

CD /D %UPLOADER_HOME%
FOR %%i IN (%UPLOADER_HOME%lib\*.jar) DO CALL %UPLOADER_HOME%\cpappend.bat "%%i"

java -cp %UPLOADER_CP% CUILoader "%~1"