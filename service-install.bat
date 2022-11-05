nssm install KW-API-Service %CD%/KW-Jobs.bat
nssm set KW-API-Service Description KitchenWorld API Service
nssm start KW-API-Service