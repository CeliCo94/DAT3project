# run-test.ps1 - Run JsonStorageTest from repository root
# Place this file in the project root (DAT3project-main\DAT3project-main)
# Usage:
#   From anywhere: & 'C:\full\path\to\DAT3project-main\DAT3project-main\run-test.ps1'
#   Or cd into the repo and run: .\run-test.ps1

Set-Location -LiteralPath $PSScriptRoot
& "$PSScriptRoot\mvnw.cmd" -Dtest=JsonStorageTest test
