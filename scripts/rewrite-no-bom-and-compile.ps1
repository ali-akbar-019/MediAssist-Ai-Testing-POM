$files = @(
 'D:\eclipse-workspace\MediAssistAi\src\test\java\testCases\TC_003_SymptomAnalyzerTest.java',
 'D:\eclipse-workspace\MediAssistAi\src\test\java\testCases\TC_004_ChatTest.java',
 'D:\eclipse-workspace\MediAssistAi\src\test\java\testCases\TC_005_MedicineTest.java',
 'D:\eclipse-workspace\MediAssistAi\src\test\java\testCases\TC_006_HospitalTest.java',
 'D:\eclipse-workspace\MediAssistAi\src\test\java\testCases\TC_007_ReportTest.java',
 'D:\eclipse-workspace\MediAssistAi\src\test\java\testCases\TC_008_LoginSecurityTest.java'
)

foreach($f in $files){
    $c = Get-Content -Raw -LiteralPath $f
    [System.IO.File]::WriteAllText($f, $c, (New-Object System.Text.UTF8Encoding($false)))
    Write-Host "Rewrote (no BOM): $f"
}

Set-Location 'D:\eclipse-workspace\MediAssistAi'
Write-Host 'Compiling tests (mvn -q test-compile)...'
$mvnExit = & mvn -q test-compile
Write-Host 'Done.'
