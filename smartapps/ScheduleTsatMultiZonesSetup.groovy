/**
 *  ScheduleTsatZones
 *
 *  Copyright 2015 Yves Racine
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
	name: "ScheduleTstatZones",
	namespace: "yracine",
	author: "Yves Racine",
	description: "Enable Heating/Cooling Zoned Solutions for thermostats coupled with z-wave vents (optional) for better temp settings control throughout your home",
	category: "My Apps",
	iconUrl: "https://s3.amazonaws.com/smartapp-icons/Partner/ecobee.png",
	iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Partner/ecobee@2x.png"
)



preferences {

	page(name: "generalSetup")
	page(name: "roomsSetup")
	page(name: "zonesSetup")
	page(name: "scheduleSetup")
	page(name: "Notifications")

}

def generalSetup() {

	dynamicPage(name: "generalSetup", uninstall: true, nextPage: roomsSetup) {
		section("About") {
			paragraph "ScheduleTstatZones, the smartapp that enables Heating/Cooling zoned settings at selected thermostat(s) coupled with z-wave vents (optional) for better temp settings control throughout your home"
			paragraph "Version 0.9\n\n" +
				"If you like this app, please support the developer via PayPal:\n\nyracine@yahoo.com\n\n" +
				"Copyright©2015 Yves Racine"
			href url: "http://github.com/yracine", style: "embedded", required: false, title: "More information...",
				description: "http://github.com/yracine/device-type.myecobee/blob/master/README.md"
		}
		section("Main thermostat at home") {
			input "thermostat", "capability.thermostat", title: "Which main thermostat?"
		}
		section("Rooms count") {
			input "roomsCount", title: "Rooms count (max=16)?", "number"
		}
		section("Zones count") {
			input "zonesCount", title: "Zones count (max=8)?", "number"
		}
		section("Schedules count") {
			input "schedulesCount", title: "Schedules count (max=12)?", "number"
		}

	}
}



def roomsSetup() {

	dynamicPage(name: "roomsSetup", title: "Rooms Setup", uninstall: true, nextPage: zonesSetup) {

		for (int i = 1;
			((i <= settings.roomsCount) && (i <= 16)); i++) {
            
			section("Room" + i + " Setup") {
				input "roomName" + i, title: "Room Name", "string", description: "Zone Name " + i
			}
			section("Room" + i +  " Thermostat") {
				input "roomTstat" + i, title: "Room thermostat to be set (optional)", "capability.thermostat", description: "roomTstat " + i, required: false

			}
			section("Room" + i +  " TempSensor") {
				input "tempSensor" + i, title: "Temp sensor (if any) to be used in current room for better temp adjustment", "capability.temperatureMeasurement", description: "tempSensor " + i, required: false

			}
			section("Room" + i +  "  Vent no1") {
				input "vent1Switch" + i, title: "1st vent switch to be turned on/off in current room (optional)", "capability.switch", description: "Vent1Switch " + i, required: false
			}           
			section("Room" + i +  " Vent no2") {
				input "vent2Switch" + i, title: "2nd vent switch to be turned on/off in current room (optional)", "capability.switch", description: "Vent2Switch " + i, required: false
			}
			section("Room" + i +  " Vent no3") {
				input "vent3Switch" + i, title: "3rd vent switch to be turned on/off in current room (optional)", "capability.switch", description: "Vent3Switch " + i, required: false
			}
			section("Room" + i +  " Vent no4") {
				input "vent4Switch" + i, title: "4th vent switch to be turned on/off in current room (optional)", "capability.switch", description: "Vent4Switch " + i, required: false
			}
			section("Room" + i +  " Vent no5") {
				input "vent5Switch" + i, title: "5th vent switch to be turned on/off in current room (optional)", "capability.switch", description: "Vent4Switch " + i, required: false
			}

			section("Room" + i + " MotionSensor") {
				input "motionSensor" + i, title: "Motion sensor (if any) to be used in current room to detect if room is occupied", "capability.motionSensor", description: "motionSensor " + i, required: false

			}
			section("Do temp adjustment when occupied room only") {
				input "needOccupiedFlag" + i, title: "Will do temp adjustement only when Occupied (default=false)", "Boolean", metadata: [values: ["true", "false"]], description: "needOccupied " + i, required: false

			}
			section("Do temp adjustment with this occupied's threshold") {
				input "residentsQuietThreshold" + i, title: "Threshold in minutes for motion detection (default=15 min)", "number", description: "residentsQuietThreshold " + i, required: false

			}
			section() {
				paragraph "**** Done for room $i **** "

			}                
		}

	}

}

def ventsSetup(params) {
	log.debug "params: ${params}"
    
	dynamicPage(name: "ventsSetup", title: "Room Vents Setup", uninstall: true) {
			section("Room Vent no1 Setup") {
				input "vent1Switch" + "${params?.indiceRoom}", title: "1st vent switch to be turned on/off in current room (optional)", "capability.switch", description: "Vent1Switch " + "${params?.indiceRoom}", required: false
			}           
			section("Room Vent no2 Setup") {
				input "vent2Switch" + "${params?.indiceRoom}", title: "2nd vent switch to be turned on/off in current room (optional)", "capability.switch", description: "Vent2Switch " + "${params?.indiceRoom}", required: false
			}
			section("Room Vent no3 Setup") {
				input "vent3Switch" + "${params?.indiceRoom}", title: "3rd vent switch to be turned on/off in current room (optional)", "capability.switch", description: "Vent3Switch " + "${params?.indiceRoom}", required: false
			}
			section("Room Vent no4 Setup") {
				input "vent4Switch" + "${params?.indiceRoom}", title: "4th vent switch to be turned on/off in current room (optional)", "capability.switch", description: "Vent4Switch " + "${params?.indiceRoom}", required: false
			}
			section("Room Vent no5 Setup") {
				input "vent5Switch" +  "${params?.indiceRoom}", title: "5th vent switch to be turned on/off in current room (optional)", "capability.switch", description: "Vent4Switch " + "${params?.indiceRoom}", required: false
			}

	}

}

def zonesSetup() {

	def rooms = []
	for (i in 1..settings.roomsCount) {
		def key = "roomName$i"
		def room = "${i}:${settings[key]}"
		rooms = rooms + room
	}
	log.debug "rooms: $rooms"

	dynamicPage(name: "zonesSetup", title: "Zones Setup", nextPage: scheduleSetup) {
		for (int i = 1;((i <= settings.zonesCount) && (i <= 8)); i++) {
			section("Zone " + i + " Setup") {
				input "zoneName" + i, title: "Zone Name", "string", description: "Zone Name " + i
			}
			section("Zone " + i + " Included rooms") {
				input "includedRooms" + i, title: "Rooms included in the zone", "enum", description: "Rooms included in this zone" + i,
					options: rooms,
					multiple: true
			}
			section() {
				paragraph "**** Done for zone $i **** "

			}                
		}            
	}
}

def scheduleSetup() {
	
	def ecobeePrograms=[]
	// try to get the thermostat programs list (ecobee)
	try {
		ecobeePrograms = thermostat?.currentClimateList.toString().minus('[').minus(']').tokenize(',')
		ecobeePrograms.sort()        
	} catch (any) {
		log.debug("Not able to get the list of climates (ecobee)")    	
	}    
    
    
	log.debug "programs: $ecobeePrograms"

	def zones = []
    
	for (i in 1..settings.zonesCount) {
		def key = "zoneName$i"
		def zoneName =  "${i}:${settings[key]}"   
		zones = zones + zoneName
	}
	log.debug "zones: $zones"

	
	def enumModes=[]
	location.modes.each {
    	enumModes << it.name
	}    

	dynamicPage(name: "scheduleSetup", title: "Schedule Setup", uninstall: true, nextPage: Notifications) {
		for (int i = 1;((i <= settings.schedulesCount) && (i <= 12)); i++) {
			section("Schedule " + i + " Setup") {
				input "scheduleName" + i, title: "Schedule Name", "string", description: "Schedule Name " + i
			}
			section("Schedule " + i + " Included zones") {
				input "includedZones" + i, title: "Zones included in this schedule", "enum", description: "Zones included in this schedule" + i,
					options: zones,
					multiple: true
			}
			section("Schedule " + i + " Day & Time of the desired Heating/Cooling settings for the selected zone(s)") {
				input "dayOfWeek" + i, "enum",
					title: "Which day of the week to trigger the zoned heating/cooling settings?",
					multiple: false,
					metadata: [
						values: [
							'All Week',
							'Monday to Friday',
							'Saturday & Sunday',
							'Monday',
							'Tuesday',
							'Wednesday',
							'Thursday',
							'Friday',
							'Saturday',
							'Sunday'
						]
					]
				input "begintime" + i, "time", title: "Beginning time to trigger the zoned heating/cooling settings (format: 24H:MM)"
				input "endtime" + i, "time", title: "End time(format: 24H:MM)"
			}
			section("Schedule " + i + " ,select the program at ecobee thermostat to be applied (optional, only for ecobee)") {
				input "givenClimate" + i, "enum", title: "Which ecobee program? ", options: ecobeePrograms, required: false
			}
			section("Desired cool Temp in the selected zone(s)") {
				input "desiredCoolTemp" + i, "decimal", title: "Cool Temp, default = 75°F/23°C", required: false
			}
			section("And desired heat Temp in the selected zone(s)") {
				input "desiredHeatTemp" + i, "decimal", title: "Heat Temp, default=72°F/21°C", required: false
			}
			section("Schedule " + i + " set Room thermostats Only Indicator") {
				input "setRoomThermostatsOnlyFlag" + i, "Boolean", title: "Set room thermostats only (default=false,main & room thermostats setpoints are set)", metadata: [values: ["true", "false"]], required: false
			}
			section("Schedule " + i + " Max temp adjustment at the main thermostat based on temp Sensors") {
				input "givenMaxTempDiff" + i, "decimal",  title: "Max Temp adjustment (default= +/-5°F/2°C)", required: false
			}
			section("Schedule " + i + " set for specific mode(s) (default=all)")  {
				input "selectedMode" + i, "enum", title: "Choose Mode", options: enumModes, required: false, multiple:true
			}
			section() {
				paragraph "**** Done for schedule $i **** "

			}                
		}
	}        
}

def Notifications() {
	dynamicPage(name: "Notifications", title: "Other Options", install: true) {
		section("Notifications") {
			input "sendPushMessage", "enum", title: "Send a push notification?", metadata: [values: ["Yes", "No"]], required: false
			input "phone", "phone", title: "Send a Text Message?", required: false
		}
		section("Detailed Notifications") {
			input "detailedNotif", "Boolean", title: "Detailed Notifications?", metadata: [values: ["true", "false"]], required:
				false
		}
		section([mobileOnly: true]) {
			label title: "Assign a name for this SmartApp", required: false
		}
	}
}


def installed() {
	initialize()
}

def updated() {
	unsubscribe()
	unschedule()
	initialize()
}

def initialize() {
	state.lastScheduleLastName=""
	state.lastStartTime=null 
    
	Integer delay =5 				// wake up every 5 minutes to apply zone settings if any
	log.debug "Scheduling setZoneSettings every ${delay} minutes to check for zone settings to be applied"

	runEvery5Minutes(setZoneSettings)

	subscribe(app, appTouch)
    
/*	Commented out as not a reliable way to do scheduling
	schedule("0 0/${delay} * * * ?", setZoneSettings)

	def currTime = now()
	for (i in 1..4) {
		key = "scheduleName$i"
		def scheduleName = settings[key]
		def key = "begintime$i"
		def startTime = settings[key]
		def startTimeToday = timeToday(startTime)
		if (startTime != null) {
			if (startTimeToday.time < currTime) {
				startTimeToday = startTimeToday + 1
			}        
			int startHourInLocal = startTimeToday.format("HH", location.timeZone).toInteger()           
			int startMinutesInLocal = startTimeToday.format("mm", location.timeZone).toInteger()           
			String startInLocalTime = startTimeToday.format("yyyy-MM-dd HH:mm", location.timeZone)
			String nowInLocalTime = new Date().format("yyyy-MM-dd HH:mm", location.timeZone)
			log.debug "initialize>scheduled ${scheduleName} at ${startInLocalTime}, (${startHourInLocal}:${startMinutesInLocal}) now = ${nowInLocalTime}, startTime UTC =${startTimeToday}"

//			schedule("${startMinutesInLocal} ${startHourInLocal} * * * ?", setZoneSettings) 
//			schedule(startTimeToday, setZoneSettings)

		}            
	}
*/

}

def appTouch(evt) {
	setZoneSettings()
}



def setZoneSettings() {

	def currTime = now()

	def ventSwitchesOn = []
	for (int i = 1;((i <= settings.schedulesCount) && (i <= 12)); i++) {
        
		def key = "selectedMode$i"
		def selectedModes = settings[key]
		key = "scheduleName$i"
		def scheduleName = settings[key]

		Boolean foundMode=false        
		selectedModes.each {
        
			if (it==location.mode) {
				foundMode=true            
			}            
		}        
        
		if ((selectedModes != null) && (!foundMode)) {
        
			log.debug "setZoneSettings>schedule=${scheduleName} does not apply,location.mode= $location.mode, selectedModes=${selectedModes},foundMode=${foundMode}, continue"
			continue			
		}
		key = "begintime$i"
		def startTime = settings[key]
		if (startTime == null) {
        		continue
        	}
		def startTimeToday = timeToday(startTime,location.timeZone)
		key = "endtime$i"
		def endTime = settings[key]
		def endTimeToday = timeToday(endTime,location.timeZone)
		if (endTimeToday.time < startTimeToday.time) {
			endTimeToday = endTimeToday + 1
		}        
        
		String startInLocalTime = startTimeToday.format("yyyy-MM-dd HH:mm", location.timeZone)
		String nowInLocalTime = new Date().format("yyyy-MM-dd HH:mm", location.timeZone)
		log.debug "setZoneSettings>found schedule ${scheduleName},nowInLocalTime= ${nowInLocalTime},startInLocalTime= ${startInLocalTime}, currTime= ${currTime}, begintime=${startTime} (${startTimeToday.time}), endTime=${endTime} (${endTimeToday.time})"
        
		if ((currTime >= startTimeToday.time) && (currTime <= endTimeToday.time) && (state.lastScheduleName != scheduleName) &&
			(state.lastStartTime != startTimeToday)) {
        
			// let's set the given schedule
            
            
			log.debug "setZoneSettings>schedule ${scheduleName},currTime= ${currTime}, current time seems OK for execution, need to check the day of Week"
			def doChange = IsRightDayForChange(i)

			log.debug "setZoneSettings>schedule ${scheduleName}, doChange=$doChange"
			// If we have hit the condition to schedule this then let's do it

			if (doChange) {

				state.lastScheduleName = scheduleName
				state.lastStartTime = startTimeToday
				if (detailedNotif == 'true') {
					send("ScheduleTstatZones>running schedule ${scheduleName},about to set zone settings as requested")
				}
        
				// set the zoned vent switches to 'on'
				def ventSwitchesZoneSet= control_vent_switches_in_zone(i)
				log.debug "setZoneSettings>schedule ${scheduleName},List of Vents turned 'on'= ${ventSwitchesZoneSet}"
				// adjust the temperature at the thermostat(s)
				adjust_thermostat_setpoint_in_zone(i)
 				ventSwitchesOn = ventSwitchesOn + ventSwitchesZoneSet              
			} else {
				if (detailedNotif == 'true') {
					send("ScheduleTstatZones>schedule: ${scheduleName}: change not scheduled at this time ${nowInLocalTime}...")
				}
			}
		}
		else if ((state.lastScheduleName == scheduleName) && (currTime >= startTimeToday.time) && (currTime <= endTimeToday.time)) {
        
			// let's adjust the vent settings according to desired Temp
            
			adjust_vent_settings_in_zone(i)
        
		}

	} /* end for */ 	
	if (ventSwitchesOn != []) {
		log.debug "setZoneSettings>list of Vents turned on= ${ventSwitchesOn}"
		turn_off_all_other_vents(ventSwitchesOn)
	}
	log.debug "End of Fcn"
}

private def isRoomOccupied(sensor, indiceRoom) {
	def key = "residentsQuietThreshold$indiceRoom"
	def threshold = (settings[key]) ?: 15 // By default, the delay is 15 minutes 

	key = "roomName$indiceRoom"
	def roomName = settings[key]

	def result = false
	def t0 = new Date(now() - (threshold * 60 * 1000))
	def recentStates = sensor.statesSince("motion", t0)
	if (recentStates.find {it.value == "active"}) {
		log.debug "isRoomOccupied>room ${roomName} has been occupied, motion was detected at sensor ${sensor} in the last ${threshold} minutes"
		result = true
	}
	log.debug "isRoomOccupied>result = $result"
	return result
}

private def getSensorTempForAverage(indiceRoom, typeSensor='tempSensor') {
	def key 
	def currentTemp=null
    
	if (typeSensor == 'tempSensor') {
		key = "tempSensor$indiceRoom"
	} else {
		key = "roomTstat$indiceRoom"
	}
	def tempSensor = settings[key]
	if (tempSensor != null) {
		log.debug("getTempSensorForAverage>found sensor ${tempSensor}")
		currentTemp = tempSensor.currentTemperature
	}
	return currentTemp
}

private def setRoomTstatSettings(indiceZone, indiceRoom) {

	def scale = getTemperatureScale()
	float desiredHeat, desiredCool
	Boolean setClimate = false
	def key = "zoneName$indiceZone"
	def zoneName = settings[key]

	key = "givenClimate$indiceZone"
	def climateName = settings[key]

	key = "roomTstat$indiceRoom"
	def roomTstat = settings[key]

	key = "roomName$indiceRoom"
	def roomName = settings[key]

	log.debug("ScheduleTstaZones>in room ${roomName},about to apply zone's temp settings at ${roomTstat}")
	String mode = thermostat?.currentThermostatMode.toString() // get the mode at the main thermostat
	if (mode == 'heat') {
		roomTstat.heat()
		if ((climateName != null) && (climateName.trim() != "")) {
			try {
				roomTstat?.setClimate("", climateName)
				setClimate = true
			} catch (any) {
				log.debug("setRoomTstatSettings>in room ${roomName},not able to set climate ${climateName} for heating at the thermostat ${roomTstat}")

			}
		}
		if (!setClimate) {
			log.debug("ScheduleTstatZones>in room ${roomName},about to apply zone's temp settings")
			key = "desiredHeatTemp$indiceZone"
			def heatTemp = settings[key]
			if ((heatTemp == null) || (heatTemp?.trim()=="")) {
				log.debug("setRoomTstatSettings>in room ${roomName},about to apply default heat settings")
				desiredHeat = (scale=='C') ? 21:72				// by default, 21°C/72°F is the target heat temp
			} else {
				desiredHeat = heatTemp.toFloat()
			}
			log.debug("setRoomTstatSettings>in room ${roomName},${roomTstat}'s desiredHeat=${desiredHeat}")
			roomTstat.setHeatingSetpoint(desiredHeat)
			send("ScheduleTstatZones>in room ${roomName}, ${roomTstat}'s heating setPoint now =${desiredHeat}°")
		}
	} else if (mode == 'cool') {

		roomTstat.cool()
		if ((climateName != null) && (climateName.trim() != "")) {
			try {
				roomTstat?.setClimate("", climateName)
				setClimate = true
			} catch (any) {
				if (detailedNotif == 'true') {
					send("ScheduleTstatZones>in room ${roomName}, not able to set climate ${climateName} for cooling at the thermostat ${roomTstat}")
				}
				log.debug("setRoomTstatSettings>in room ${roomName},not able to set climate ${climateName} for cooling at the thermostat ${roomTstat}")

			}
		}
		if (!setClimate) {
			log.debug("ScheduleTstatZones>in room ${roomName},about to apply zone's temp settings")
			key = "desiredCoolTemp$indiceZone"
			def coolTemp = settings[key]
			if ((coolTemp == null) || (coolTemp?.trim()=="")) {
				log.debug("setRoomTstatSettings>in room ${roomName},about to apply default cool settings")
				desiredCool = (scale=='C') ? 23:75				// by default, 23°C/75°F is the target cool temp
			} else {
            
				desiredCool = coolTemp.toFloat()
			}
			log.debug("setRoomTstatSettings>in room ${roomName}, ${roomTstat}'s desiredCool=${desiredCool}")
			roomTstat.setCoolingSetpoint(desiredCool)
			send("ScheduleTstatZones>in room ${roomName}, ${roomTstat}'s cooling setPoint now =${desiredCool}°")
		}
	}
}

private def setAllRoomTstatsSettings(indiceZone) {
	def foundRoomTstat = false

	def key = "includedRooms$indiceZone"
	def rooms = settings[key]
	for (room in rooms) {

		def roomDetails=room.split(':')
		def indiceRoom = roomDetails[0]
		def roomName = roomDetails[1]

		key = "needOccupiedFlag$indiceRoom"
		def needOccupied = (settings[key]) ?: 'false'
		key = "roomTstat$indiceRoom"
		def roomTstat = settings[key]

		if (!roomTstat) {
			continue
		}
		log.debug("setAllRoomTstatsSettings>found a room Tstat ${roomTstat}, needOccupied=${needOccupied} in room ${roomName}, indiceRoom=${indiceRoom}")
		foundRoomTstat = true
		if (needOccupied == 'true') {

			key = "motionSensor$indiceRoom"
			def motionSensor = settings[key]
			if (motionSensor != null) {

				if (isRoomOccupied(motionSensor, indiceRoom)) {
					log.debug("setAllRoomTstatsSettings>for occupied room ${roomName},about to call setRoomTstatSettings ")
					setRoomTstatSettings(indiceZone, indiceRoom)
				} else {
                
					log.debug("setAllRoomTstatsSettings>room ${roomName} not occupied,skipping it")
                
				}
			}
		} else {

			log.debug("setAllRoomTstatsSettings>for room ${roomName},about to call setRoomTstatSettings ")
			setRoomTstatSettings(indiceZone, indiceRoom)
		}
	}
	return foundRoomTstat
}

private def getAllTempsForAverage(indiceZone) {
	def tempAtSensor

	def indoorTemps = []
	def key = "includedRooms$indiceZone"
	def rooms = settings[key]
	for (room in rooms) {

		def roomDetails=room.split(':')
		def indiceRoom = roomDetails[0]
		def roomName = roomDetails[1]

		key = "needOccupiedFlag$indiceRoom"
		def needOccupied = (settings[key]) ?: 'false'
		log.debug("getAllTempsForAverage>looping thru all rooms,now room=${roomName},indiceRoom=${indiceRoom}, needOccupied=${needOccupied}")

		if (needOccupied == 'true') {

			key = "motionSensor$indiceRoom"
			def motionSensor = settings[key]
			if (motionSensor != null) {

				if (isRoomOccupied(motionSensor, indiceRoom)) {

					tempAtSensor = getSensorTempForAverage(indiceRoom)
					if (tempAtSensor != null) {
						indoorTemps = indoorTemps + tempAtSensor.toFloat().round(1)
						log.debug("getAllTempsForAverage>added ${tempAtSensor.toString()} due to occupied room ${roomName} based on ${motionSensor}")
					}
					tempAtSensor = getSensorTempForAverage(indiceRoom,'roomTstat')
					if (tempAtSensor != null) {
						indoorTemps = indoorTemps + tempAtSensor.toFloat().round(1)
						log.debug("getAllTempsForAverage>added ${tempAtSensor.toString()} due to occupied room ${roomName} based on ${motionSensor}")
					}
				}
			}

		} else {

			tempAtSensor = getSensorTempForAverage(indiceRoom)
			if (tempAtSensor != null) {
				log.debug("getAllTempsForAverage>added ${tempAtSensor.toString()} in room ${roomName}")
				indoorTemps = indoorTemps + tempAtSensor.toFloat().round(1)
			}
			tempAtSensor = getSensorTempForAverage(indiceRoom,'roomTstat')
			if (tempAtSensor != null) {
				indoorTemps = indoorTemps + tempAtSensor.toFloat().round(1)
 				log.debug("getAllTempsForAverage>added ${tempAtSensor.toString()} in room ${roomName}")
			}

		}
	} /* end for */
	return indoorTemps
}


private def adjust_thermostat_setpoint_in_zone(indiceSchedule) {
	def scale = getTemperatureScale()
	float desiredHeat, desiredCool, avg_indoor_temp

	def key = "scheduleName$indiceSchedule"
	def scheduleName = settings[key]

	key = "includedZones$indiceSchedule"
	def zones = settings[key]
	key = "setRoomThermostatsOnlyFlag$indiceSchedule"
	def setRoomThermostatsOnlyFlag = settings[key]
	def setRoomThermostatsOnly = (setRoomThermostatsOnlyFlag) ?: 'false'
	def indoor_all_zones_temps=[]
    
	log.debug("adjust_thermostat_setpoint_in_zone>schedule ${scheduleName}: zones= ${zones}")

	for (zone in zones) {

		def zoneDetails=zone.split(':')
		log.debug("adjust_thermostat_setpoint_in_zone>zone=${zone}: zoneDetails= ${zoneDetails}")
		def indiceZone = zoneDetails[0]
		def zoneName = zoneDetails[1]
        
		log.debug("adjust_thermostat_setpoint_in_zone>schedule ${scheduleName}: looping thru all zones, now zoneName=${zoneName}, about to apply room Tstat's settings")
		setAllRoomTstatsSettings(indiceZone) 

		if (setRoomThermostatsOnly == 'true') { // Does not want to set the main thermostat, only the room ones

			if (detailedNotif == 'true') {
				send("ScheduleTstatZones>schedule ${scheduleName},zone ${zoneName}: all room Tstats set and setRoomThermostatsOnlyFlag= true, continue...")
			}
            
		} else {

			def indoorTemps = getAllTempsForAverage(indiceZone)
			indoor_all_zones_temps = indoor_all_zones_temps + indoorTemps
		}
	}
	if (setRoomThermostatsOnly=='true') {
		log.debug("adjust_thermostat_setpoint_in_zone>schedule ${scheduleName}: schedule ${scheduleName},all room Tstats set and setRoomThermostatsOnlyFlag= true,exiting")
		return				    
	}    
	//	Now will do an avg temp calculation based on all temp sensors to apply the desired temp settings at the main Tstat correctly

	thermostat?.poll()
	float currentTemp = thermostat?.currentTemperature.toFloat()
	String mode = thermostat?.currentThermostatMode.toString()
	//	This is the avg indoor temp based on indoor temp sensors in all rooms in the zone
	log.debug("adjust_thermostat_setpoint_in_zone>schedule ${scheduleName},all temps collected from sensors=${indoor_all_zones_temps}")
	if (indoor_all_zones_temps != [] ) {
		avg_indoor_temp = (indoor_all_zones_temps.sum() / indoor_all_zones_temps.size()).round(1)
	} else {
		avg_indoor_temp = currentTemp
	}

	float temp_diff = (avg_indoor_temp - currentTemp).round(1)
	if (detailedNotif == 'true') {
		send("ScheduleTstatZones>schedule ${scheduleName}:avg temp= ${avg_indoor_temp},main Tstat's currentTemp= ${currentTemp},temp adjustment=${temp_diff.abs()}")
	}
	desiredCool = (scale=='C') ? 23:75					// by default, 23°C/75°F is the target cool temp

	key = "givenMaxTempDiff$indiceSchedule"
	def givenMaxTempDiff = settings[key]
	def max_temp_diff = givenMaxTempDiff ?: (scale=='C')? 2: 5 // 2°C/5°F temp differential is applied by default

	key = "givenClimate$indiceSchedule"
	def climateName = settings[key]
	if (mode == 'heat') {
	
		if ((climateName == null) || (climateName.trim() == "")) {
			log.debug("adjust_thermostat_setpoint_in_zone>schedule ${scheduleName}:climate for the heating settings to be applied not found")
			key = "desiredHeatTemp$indiceSchedule"
			def heatTemp = settings[key]
			if ((heatTemp == null) || (heatTemp?.trim()=="")) {
				log.debug("adjust_thermostat_setpoint_in_zone>schedule ${scheduleName}:about to apply default heat settings")
				desiredHeat = (scale=='C') ? 21:72 					// by default, 21°C/72°F is the target heat temp
			} else {
            
				desiredHeat = heatTemp.toFloat()
			}
			log.debug("adjust_thermostat_setpoint_in_zone>schedule ${scheduleName},desiredHeat=${desiredHeat}")
		} else {
			try {
				thermostat.setClimate("", climateName)
			} catch (any) {
				if (detailedNotif == 'true') {
					send("ScheduleTstatZones>schedule ${scheduleName}:not able to set climate ${climateName} for heating at the thermostat ${thermostat}")
				}
				log.error("adjust_thermostat_setpoint_in_zone>schedule ${scheduleName}:not able to set climate  ${climateName} for heating at the thermostat ${thermostat}")
			}
			desiredHeat = thermostat.currentHeatingSetpoint
			log.debug("adjust_thermostat_setpoint_in_zone>schedule ${scheduleName},according to climateName ${climateName}, desiredHeat=${desiredHeat}")
		}
		temp_diff = (temp_diff <0-max_temp_diff)?max_temp_diff:(temp_diff >max_temp_diff)?max_temp_diff:temp_diff // determine the temp_diff based on max_temp_diff
		float targetTstatTemp = (desiredHeat - temp_diff).round(1)
		thermostat?.setHeatingSetpoint(targetTstatTemp)
		send("ScheduleTstatZones>schedule ${scheduleName},in zones=${zones},heating setPoint now =${targetTstatTemp}°,adjusted by avg temp diff (${temp_diff.abs()}°) between all temp sensors in zone")

	} else if (mode == 'cool') {

		if ((climateName == null) || (climateName.trim() == "")) {
			log.debug("adjust_thermostat_setpoint_in_zone>${scheduleName},climate associated to the cooling settings to be applied not found")
			key = "desiredCoolTemp$indiceSchedule"
			def coolTemp = settings[key]
			if ((coolTemp == null) || (coolTemp?.trim()=="")) {
				log.debug("adjust_thermostat_setpoint_in_zone>schedule ${scheduleName},about to apply default cool settings")
				desiredCool = (scale=='C') ? 23:75					// by default, 23°C/75°F is the target cool temp
			} else {
            
				desiredCool = coolTemp.toFloat()
			}
            
			log.debug("adjust_thermostat_setpoint_in_zone>schedule ${scheduleName},desiredCool=${desiredCool}")
		} else {
			try {
				thermostat?.setClimate("", climateName)
			} catch (any) {
				if (detailedNotif == 'true') {
					send("ScheduleTstatZones>schedule ${scheduleName},not able to set climate ${climateName} for cooling at the thermostat(s) ${thermostat}")
				}
				log.error("adjust_thermostat_setpoint_in_zone>schedule ${scheduleName},not able to set climate ${climateName} associated for cooling at the thermostat ${thermostat}")
			}
			desiredCool = thermostat.currentCoolingSetpoint
			log.debug("adjust_thermostat_setpoint_in_zone>schedule ${scheduleName},according to climateName ${climateName}, desiredCool=${desiredCool}")
		}
		temp_diff = (temp_diff <0-max_temp_diff)?max_temp_diff:(temp_diff >max_temp_diff)?max_temp_diff:temp_diff // determine the temp_diff based on max_temp_diff
		float targetTstatTemp = (desiredCool - temp_diff).round(1)
		thermostat?.setCoolingSetpoint(targetTstatTemp)
		send("ScheduleTstatZones>schedule ${scheduleName}, in zones=${zones},cooling setPoint now =${targetTstatTemp}°,adjusted by avg temp diff (${temp_diff}°) between all temp sensors in zone")
	}

}


private def adjust_vent_settings_in_zone(indiceSchedule) {
	float desiredTemp, avg_indoor_temp, avg_temp_diff

	def key = "scheduleName$indiceSchedule"
	def scheduleName = settings[key]

	key = "includedZones$indiceSchedule"
	def zones = settings[key]
	key = "setRoomThermostatsOnlyFlag$indiceSchedule"
	def setRoomThermostatsOnlyFlag = settings[key]
	def setRoomThermostatsOnly = (setRoomThermostatsOnlyFlag) ?: 'false'
	def indoor_all_zones_temps=[]
	def indiceRoom
	Boolean closeAllVentsInZone=true
	int nbVents=0
	def switchLevel    
    
	log.debug("adjust_vent_settings_in_zone>schedule ${scheduleName}: zones= ${zones}")

	if (setRoomThermostatsOnly=='true') {
		log.debug("adjust_vent_settings_in_zone>schedule ${scheduleName}: schedule ${scheduleName},all room Tstats set and setRoomThermostatsOnlyFlag= true,exiting")
		return				    
	}    
	thermostat.poll()
	String mode = thermostat?.currentThermostatMode.toString()
	desiredTemp = thermostat.currentThermostatSetpoint.toFloat().round(1)
    
	log.debug("adjust_vent_settings_in_zone>schedule ${scheduleName}, desiredTemp=${desiredTemp}")
    
	for (zone in zones) {

		def zoneDetails=zone.split(':')
		log.debug("adjust_vent_settings_in_zone>zone=${zone}: zoneDetails= ${zoneDetails}")
		def indiceZone = zoneDetails[0]
		def zoneName = zoneDetails[1]
		def indoorTemps = getAllTempsForAverage(indiceZone)

		float currentTemp = thermostat?.currentTemperature.toFloat()
		if (indoorTemps != [] ) {
			avg_indoor_temp = (indoorTemps.sum() / indoorTemps.size()).round(1)
			avg_temp_diff = (avg_indoor_temp - desiredTemp).round(1)
			            
		} else {
			log.debug("adjust_vent_settings_in_zone>schedule ${scheduleName}, in zone ${zoneName}, no data from temp sensors, exiting")
		}        
		if (detailedNotif == 'true') {
			log.debug("adjust_vent_settings_in_zone>schedule ${scheduleName}, in zone ${zoneName}, avg_temp_diff=${avg_temp_diff}, all temps collected from sensors=${indoorTemps}")
		}
		key = "includedRooms$indiceZone"
		def rooms = settings[key]
		for (room in rooms) {
			def roomDetails=room.split(':')
			indiceRoom = roomDetails[0]
			def roomName = roomDetails[1]
			if ((roomName == null) || (roomName.trim() == "")) {
				continue
			}
			def tempAtSensor =getSensorTempForAverage(indiceRoom)			
			if (tempAtSensor != null) {
				float temp_diff_at_sensor = tempAtSensor.toFloat().round(1) - desiredTemp 
				log.debug("adjust_vent_settings_in_zone>schedule ${scheduleName}, in zone ${zoneName}, room ${roomName}, temp_diff_at_sensor=${temp_diff_at_sensor}, avg_temp_diff=${avg_temp_diff}")
				switchLevel = ((temp_diff_at_sensor / avg_temp_diff) * 100).round() 			                
				switchLevel =( switchLevel >=0)?((switchLevel<100)? switchLevel: 100):(switchlevel< (-100))?0:100+switchLevel
				if (switchLevel >=10) {	
					closeAllVentsInZone=false
				}                    
				key = "vent1Switch$indiceRoom"
				def vent1Switch = settings[key]
				if (vent1Switch != null) {
					setVentSwitchLevel(indiceRoom, vent1Switch, switchLevel)                
					log.debug "adjust_vent_settings_in_zone>in zone=${zoneName},room ${roomName},set ${vent1Switch} at switchLevel =${switchLevel}%"
					nbVents++                    
				}           
				key = "vent2Switch$indiceRoom"
				def vent2Switch = settings[key]
				if (vent2Switch != null) {
					setVentSwitchLevel(indiceRoom, vent2Switch, switchLevel)                
					log.debug "adjust_vent_settings_in_zone>in zone=${zoneName},room ${roomName},set ${vent2Switch} at switchLevel =${switchLevel}%"
					nbVents++                    
				}           
				key = "vent3Switch$indiceRoom"
				def vent3Switch = settings[key]
				if (vent3Switch != null) {
					setVentSwitchLevel(indiceRoom, vent3Switch, switchLevel)                
					log.debug "adjust_vent_settings_in_zone>in zone=${zoneName},room ${roomName},set ${vent3Switch} at switchLevel =${switchLevel}%"
					nbVents++                    
				}           
				key = "vent4Switch$indiceRoom"
				def vent4Switch = settings[key]
				if (vent4Switch != null) {
					setVentSwitchLevel(indiceRoom, vent4Switch, switchLevel)                
					log.debug "adjust_vent_settings_in_zone>in zone=${zoneName},room ${roomName},set ${vent4Switch} at switchLevel =${switchLevel}%"
					nbVents++                    
				}           
				key = "vent5Switch$indiceRoom"
				def vent5Switch = settings[key]
				if (vent5Switch != null) {
					setVentSwitchLevel(indiceRoom, vent5Switch, switchLevel)                
					log.debug "adjust_vent_settings_in_zone>in zone=${zoneName},room ${roomName},set ${vent5Switch} at switchLevel =${switchLevel}%"
					nbVents++                    
				}  
			}                
		} /* end for rooms */
		
	} /* end for zones */

	if (closeAllVentsInZone) {
    
		if (nbVents > 2) {        
			switchLevel=10        
			control_vent_switches_in_zone(indiceSchedule, switchLevel)		    
		} else {
			switchLevel=25        
			control_vent_switches_in_zone(indiceSchedule, switchLevel)		    
	        
		}
		log.debug "adjust_vent_settings_in_zone>in zone=${zoneName},set all ventSwitches at ${switchLevel}% to avoid closing all of them"
		if (detailedNotif == 'true') {
			send("ScheduleTstatZones>schedule ${scheduleName},set all ventSwitches at ${switchLevel}% to avoid closing all of them")
		}
	}    
}

private def turn_off_all_other_vents(ventSwitchesOnSet) {
	def foundVentSwitch
    
	for (indiceZone in 1..zonesCount) {
		def key = "zoneName$indiceZone"  
		def zoneName = settings[key]
    
		if ((zoneName == null) || (zoneName.trim() == "")) {
        
			continue
		}

		key = "includedRooms$indiceZone"
		def rooms = settings[key]
		for (room in rooms) {
			def roomDetails=room.split(':')
			def indiceRoom = roomDetails[0]
			def roomName = roomDetails[1]
			if ((roomName == null) || (roomName.trim() == "")) {
				continue
			}

			key = "vent1Switch$indiceRoom"
			def vent1Switch = settings[key]
			if (vent1Switch != null) {
				log.debug "turn_off_all_other_vents>in zone=${zoneName},room ${roomName},found= ${vent1Switch}"
				foundVentSwitch = ventSwitchesOnSet.find{it == vent1Switch}
				if (foundVentSwitch ==null) {
					vent1Switch.off()
					log.debug("turn_off_all_other_vents>in zone ${zoneName},turned off ${vent1Switch} in room ${roomName} as requested to create the desired zone(s)")
				}                
			}
			key = "vent2Switch$indiceRoom"
			def vent2Switch = settings[key]
			if (vent2Switch != null) {
				log.debug "turn_off_all_other_vents>in zone=${zoneName},room ${roomName},found= ${vent2Switch}"
				foundVentSwitch = ventSwitchesOnSet.find{it == vent2Switch}
            			if (foundVentSwitch==null) {
					vent2Switch.off()
					log.debug("turn_off_all_other_vents>in zone ${zoneName},turned off ${vent2Switch} in room ${roomName} as requested to create the desired zone(s)")
				}                
			}
			key = "vent3Switch$indiceRoom"
			def vent3Switch = settings[key]
			if (vent3Switch != null) {
				foundVentSwitch = ventSwitchesOnSet.find{it == vent3Switch}
				log.debug "turn_off_all_other_vents>in zone=${zoneName},room ${roomName},found= ${vent3Switch}"
				if (foundVentSwitch==null) {
					vent3Switch.off()
					log.debug("turn_off_all_other_vents>in zone ${zoneName},turned off ${vent3Switch} in room ${roomName} as requested to create the desired zone(s)")
				}                
			}
			key = "vent4Switch$indiceRoom"
			def vent4Switch = settings[key]
			if (vent4Switch != null) {
				log.debug "turn_off_all_other_vents>in zone=${zoneName},room ${roomName},found= ${vent4Switch}"
				foundVentSwitch = ventSwitchesOnSet.find{it == vent4Switch}
				if (foundVentSwitch==null) {
					vent4Switch.off()
					log.debug("turn_off_all_other_vents>in zone ${zoneName},turned off ${vent4Switch} in room ${roomName} as requested to create the desired zone(s)")
				}                
			}
			key = "vent5Switch$indiceRoom"
			def vent5Switch = settings[key]
			if (vent5Switch != null) {
				log.debug "turn_off_all_other_vents>in zone=${zoneName},room ${roomName},found= ${vent5Switch}"
				foundVentSwitch = ventSwitchesOnSet.find{it == vent5Switch}
				if (foundVentSwitch==null) {
					vent5Switch.off()
					log.debug("turn_off_all_other_vents>in zone ${zoneName},turned off ${vent5Switch} in room ${roomName} as requested to create the desired zone(s)")
				}                
			}
            
		}  /* end for rooms */          
	} /* end for zones */

}


private def setVentSwitchLevel(indiceRoom, ventSwitch, switchLevel=100) {

	def key = "roomName$indiceRoom"
	def roomName = settings[key]

	try {
		ventSwitch.setLevel(switchLevel)
		log.debug("ScheduleTstatZones>set ${ventSwitch} at level ${switchLevel} in room ${roomName} to reach desired temperature")
	} catch (any) {
		log.error "setVentSwitchLevel>in room ${roomName}, not able to set ${ventSwitch} at ${switchLevel}"

	}
    
}



private def control_vent_switches_in_zone(indiceSchedule, switchLevel=100) {
	def key = "scheduleName$indiceSchedule"
	def scheduleName = settings[key]

	key = "includedZones$indiceSchedule"
	def zones = settings[key]
	def ventSwitchesOnSet=[]
    
	for (zone in zones) {

		def zoneDetails=zone.split(':')
		def indiceZone = zoneDetails[0]
		def zoneName = zoneDetails[1]
		key = "includedRooms$indiceZone"
		def rooms = settings[key]
    
		for (room in rooms) {
			def roomDetails=room.split(':')
			def indiceRoom = roomDetails[0]
			def roomName = roomDetails[1]

			key = "vent1Switch$indiceRoom"
			def vent1Switch = settings[key]
			if (vent1Switch != null) {
				ventSwitchesOnSet.add(vent1Switch)
				setVentSwitchLevel(indiceRoom, vent1Switch, switchLevel)
			}
			key = "vent2Switch$indiceRoom"
			def vent2Switch = settings[key]
			if (vent2Switch != null) {
				ventSwitchesOnSet.add(vent2Switch)
				setVentSwitchLevel(indiceRoom, vent2Switch, switchLevel)
			}
			key = "vent3Switch$indiceRoom"
			def vent3Switch = settings[key]
			if (vent3Switch != null) {
				ventSwitchesOnSet.add(vent3Switch)
				setVentSwitchLevel(indiceRoom, vent3Switch, switchLevel)
			}
			key = "vent4Switch$indiceRoom"
			def vent4Switch = settings[key]
			if (vent4Switch != null) {
				ventSwitchesOnSet.add(vent4Switch)
				setVentSwitchLevel(indiceRoom, vent4Switch, switchLevel)
			}
			key = "vent5Switch$indiceRoom"
			def vent5Switch = settings[key]
			if (vent5Switch != null) {
				ventSwitchesOnSet.add(vent5Switch)
				setVentSwitchLevel(indiceRoom, vent5Switch, switchLevel)
			}                
		} /* end for rooms */
	} /* end for zones */
	return ventSwitchesOnSet
}


def IsRightDayForChange(indiceSchedule) {

	def key = "zoneName$indiceSchedule"
	def scheduleName = settings[key]

	key ="dayOfWeek$indiceSchedule"
	def dayOfWeek = settings[key]
    
	def makeChange = false
	Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
	int currentDayOfWeek = localCalendar.get(Calendar.DAY_OF_WEEK);

	// Check the condition under which we want this to run now
	// This set allows the most flexibility.
	if (dayOfWeek == 'All Week') {
		makeChange = true
	} else if ((dayOfWeek == 'Monday' || dayOfWeek == 'Monday to Friday') && currentDayOfWeek == Calendar.instance.MONDAY) {
		makeChange = true
	} else if ((dayOfWeek == 'Tuesday' || dayOfWeek == 'Monday to Friday') && currentDayOfWeek == Calendar.instance.TUESDAY) {
		makeChange = true
	} else if ((dayOfWeek == 'Wednesday' || dayOfWeek == 'Monday to Friday') && currentDayOfWeek == Calendar.instance.WEDNESDAY) {
		makeChange = true
	} else if ((dayOfWeek == 'Thursday' || dayOfWeek == 'Monday to Friday') && currentDayOfWeek == Calendar.instance.THURSDAY) {
		makeChange = true
	} else if ((dayOfWeek == 'Friday' || dayOfWeek == 'Monday to Friday') && currentDayOfWeek == Calendar.instance.FRIDAY) {
		makeChange = true
	} else if ((dayOfWeek == 'Saturday' || dayOfWeek == 'Saturday & Sunday') && currentDayOfWeek == Calendar.instance.SATURDAY) {
		makeChange = true
	} else if ((dayOfWeek == 'Sunday' || dayOfWeek == 'Saturday & Sunday') && currentDayOfWeek == Calendar.instance.SUNDAY) {
		makeChange = true
	}

	log.debug "IsRightDayforChange>schedule ${scheduleName}, makeChange=${makeChange},Calendar DOW= ${currentDayOfWeek}, dayOfWeek=${dayOfWeek}"

	return makeChange
}


private send(msg) {
	if (sendPushMessage != "No") {
		log.debug("sending push message")
		sendPush(msg)
	}

	if (phone) {
		log.debug("sending text message")
		sendSms(phone, msg)
	}
	log.debug msg
}
