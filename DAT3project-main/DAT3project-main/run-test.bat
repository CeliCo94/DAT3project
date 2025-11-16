@echo off
REM run-test.bat - run JsonStorageTest from repository root
REM Place this file in the project root (DAT3project-main\DAT3project-main)
REM Usage: double-click or run from cmd/powershell in the repo root: run-test.bat
cd /d %~dp0
mvnw.cmd -Dtest=JsonStorageTest test
