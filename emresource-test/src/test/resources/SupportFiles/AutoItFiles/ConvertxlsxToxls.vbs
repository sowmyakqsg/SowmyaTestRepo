	dim Arg, StrFileName
	set Arg=WScript.Arguments
	strFilePath = Arg(0)
	StrFileName = Arg(1)

	Call ConvertxlsxToxls(strFilePath, StrFileName)


Private Function ConvertxlsxToxls(ByVal strFilePath, ByVal strFileName)
	'Convert xlsx to xls
	Set objExcel = CreateObject("Excel.Application")
	Set objWorkbook = objExcel.Workbooks.Open(strFilePath + StrFileName + ".xlsx")
	objExcel.Application.DisplayAlerts = False
	objExcel.Application.Visible = True

	objExcel.ActiveWorkbook.SaveAs strFilePath + StrFileName + ".xls",56
	objExcel.ActiveWorkbook.Close

	Set objExcel = Nothing
	Set objWorkbook = Nothing
End Function

