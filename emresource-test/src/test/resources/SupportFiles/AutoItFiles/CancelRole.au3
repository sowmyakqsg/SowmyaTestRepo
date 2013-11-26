
#region --- AutoIt Macro Generator V 0.21 beta ---
dim $winfoundflag

Do
	   sleep(500)
	   If(WinExists("Message from webpage")) Then
			$winfoundflag = True
		EndIf
	Until $winfoundflag = True

Opt("WinTitleMatchMode", 4)
WinWait("Message from webpage","This role will be permanently ")
ControlClick("Message from webpage","This role will be permanently ","Button2")

#endregion --- End ---")