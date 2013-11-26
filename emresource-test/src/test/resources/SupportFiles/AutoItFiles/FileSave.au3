Opt("WinTitleMatchMode", 4)

WinWait("File Download","Do you want to open or save th")
WinWaitActive("File Download","Do you want to open or save th")
Sleep(5000);
ControlClick("File Download","Do you want to open or save th","Button2")
Sleep(5000);

WinWaitActive("Save As","Save &in:")
Sleep(1000);
Send ($CmdLine[1])
ControlClick("Save As","Save &in:","Button2")
Sleep(10000)

