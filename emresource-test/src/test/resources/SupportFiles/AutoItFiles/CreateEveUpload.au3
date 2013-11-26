
#region --- AutoIt Macro Generator V 0.21 beta ---
Opt("WinTitleMatchMode", 4)
dim $winfoundflag

Do
	   sleep(250)
	   If(WinExists("Choose File to Upload")) Then
			$winfoundflag = True
		EndIf
	Until $winfoundflag = True

WinWait("Choose File to Upload","Look &in:")
WinActivate("Choose File to Upload","Look &in:")
Sleep(2500);
WinWait("Choose File to Upload","Look &in:")
WinActivate("Choose File to Upload","Look &in:")
Sleep(2500);
WinActivate("Choose File to Upload","Look &in:")
Sleep(2500);
ControlClick("Choose File to Upload","Look &in:","Edit1")
Sleep(1000)
Send ($CmdLine[1])
;Send ("D:\eclipse\com.qsgsoft.EMResource\SupportFiles\ImoprtFiles\Import.xls")
Sleep(1000)
ControlClick("Choose File to Upload","Look &in:","Button2")
Sleep(2000)
#endregion --- End ---")