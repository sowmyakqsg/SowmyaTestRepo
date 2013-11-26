
#region --- AutoIt Macro Generator V 0.21 beta ---
Opt("WinTitleMatchMode", 4)

dim $winfoundflag

Do
	   sleep(250)
	   If(WinExists("File Download")) Then
			$winfoundflag = True
		EndIf
	Until $winfoundflag = True


WinWait("File Download","Do you want to open or save th")
WinWaitActive("File Download","Do you want to open or save th")
Sleep(1000)
ControlClick("File Download","Do you want to open or save th","Button2")
Sleep(2000)
WinWait("Save As","Save &in:")
WinWaitActive("Save As","Save &in:")
Sleep(1000);
Send ($CmdLine[1])
ControlClick("Save As","Save &in:","Button2")
Sleep(2000)

;Do
	   ;sleep(250)
	  ; If(WinExists("Download complete")) Then
			;$winfoundflag = False
		;EndIf
	;Until $winfoundflag = False

;WinKill("Download complete")

#endregion --- End ---
