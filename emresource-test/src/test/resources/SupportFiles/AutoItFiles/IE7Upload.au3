#region --- AutoIt Macro Generator V 0.21 beta ---
Opt("WinTitleMatchMode", 4)
dim $winfoundflag

Do
	   sleep(250)
	   If(WinExists("Choose file")) Then
			$winfoundflag = True
		EndIf
	Until $winfoundflag = True

WinWait("Choose file","Look &in:")
WinActivate("Choose file","Look &in:")
Sleep(2500);
Send ($CmdLine[1])
Sleep(1000)
ControlClick("Choose file","Look &in:","Button2")
#endregion --- End ---")

