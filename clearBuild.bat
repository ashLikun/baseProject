cd "%1"

 
@echo off

echo ���������׿��Ŀ�����ļ������Ե�......

 
::@echo off ɾ��class��dex�ļ�

::del *.class *.dex /s /f /a /q

 
::rd /s /q .gradle

@echo off ���������ļ���ɾ������build�ļ���

:: /s ����ɾ�����е���Ŀ¼�� /q ��ʾɾ��Ŀ¼��ʱ����ʾȷ�ϣ�

:: 1>nul ��ʾ����ȷɾ��Ŀ¼������Ϣ��ֹ�����2>nul ��ʾ��ɾ�������еĴ�����Ϣ��ֹ���

 
for /f "delims=" %%a in ('dir /b/s/ad ^|findstr /c ".\build"')do rd /s /q "%%a" 2>nul

pause