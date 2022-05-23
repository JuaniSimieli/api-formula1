
# API Formula 1 

## Pantalla 1
[Info Carreras](http://ergast.com/api/f1/current.json) \
Pantalla hija: carrera especifica round: 
*MRData.RaceTable.Races.round*
/current/($NroCarrera).json


## Pantalla 2 
[Driver Standings](http://ergast.com/api/f1/current/driverStandings.json) \
Pantalla hija: Detalles piloto
Piloto id esta en: 
*MRData.StandingsTable.StandingList.DriverStandings.Driver.driverId*
/drivers/($driverID).json
	
## Pantalla 3 
[Constructor Standings](http://ergast.com/api/f1/current/constructorStandings.json) \
Pantalla Hija: detalles constructores
constructorID esta en: 
*MRData.StandingsTable.StandingList.ConstructorStandings.Constructor.constructorId*
/constructors/($constructorId).json

<sub>Base URL:</sub>
<sub>ergast.com/api/f1</sub>
