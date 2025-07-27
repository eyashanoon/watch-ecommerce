package com.watches.backend.Dto.ProductDto;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ProductFeaturesDto {

    Boolean waterProof;
    String waterResistant;
    Boolean shockResistant;
    String ScratchResistantGlass;
    Boolean DustResistant;
    Boolean antiMagnetic;

    String batteryLife;
    Boolean solarPowered;
    Boolean kineticCharging;
    Boolean fastCharging;
    Boolean wirelessCharging;

    Boolean stopWatch;
    Boolean alarm;
    Boolean dualTimeZone;
    Boolean compass;
    Boolean thermometer;
    Boolean moonPhaseDisplay;
    Boolean SmartNotification; // for calls, texts, and emails

    Boolean Bluetooth;
    Boolean GPS;
    Boolean HeartRateMonitoring;
    Boolean bloodOxygenMonitoring;
    Boolean sleepTracking;
    Boolean stepCounter;
    Boolean musicControl;
    String VoiceAssistantSupport; // Google, Siri, etc..
    Boolean fallDetection;

    String caseMaterial; // Stainless Steel, Titanium, Ceramic, Plastic
    String BandMaterial; // Leather, Silicone, Stainless Steel, Nylon
    String DialType; // Analog, Digital, Hybrid
    String DisplayType; // AMOLED, LCD, E-Ink
    String size; // 42mm, 44mm
    String weight;
    String color;

    Boolean passcode;
    Boolean CustomizableWatchFaces;

    public static Map<String, Object> getAllTags(ProductFeaturesDto product) {
        Map<String, Object> map = new HashMap<>();

        for(Field field : product.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(product));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }


    public Boolean getWaterProof() {
        return waterProof;
    }

    public void setWaterProof(Boolean waterProof) {
        this.waterProof = waterProof;
    }

    public String getWaterResistant() {
        return waterResistant;
    }

    public void setWaterResistant(String waterResistant) {
        this.waterResistant = waterResistant;
    }

    public Boolean getShockResistant() {
        return shockResistant;
    }

    public void setShockResistant(Boolean shockResistant) {
        this.shockResistant = shockResistant;
    }

    public String getScratchResistantGlass() {
        return ScratchResistantGlass;
    }

    public void setScratchResistantGlass(String scratchResistantGlass) {
        ScratchResistantGlass = scratchResistantGlass;
    }

    public Boolean getDustResistant() {
        return DustResistant;
    }

    public void setDustResistant(Boolean dustResistant) {
        DustResistant = dustResistant;
    }

    public Boolean getAntiMagnetic() {
        return antiMagnetic;
    }

    public void setAntiMagnetic(Boolean antiMagnetic) {
        this.antiMagnetic = antiMagnetic;
    }

    public String getBatteryLife() {
        return batteryLife;
    }

    public void setBatteryLife(String batteryLife) {
        this.batteryLife = batteryLife;
    }

    public Boolean getSolarPowered() {
        return solarPowered;
    }

    public void setSolarPowered(Boolean solarPowered) {
        this.solarPowered = solarPowered;
    }

    public Boolean getKineticCharging() {
        return kineticCharging;
    }

    public void setKineticCharging(Boolean kineticCharging) {
        this.kineticCharging = kineticCharging;
    }

    public Boolean getFastCharging() {
        return fastCharging;
    }

    public void setFastCharging(Boolean fastCharging) {
        this.fastCharging = fastCharging;
    }

    public Boolean getWirelessCharging() {
        return wirelessCharging;
    }

    public void setWirelessCharging(Boolean wirelessCharging) {
        this.wirelessCharging = wirelessCharging;
    }

    public Boolean getStopWatch() {
        return stopWatch;
    }

    public void setStopWatch(Boolean stopWatch) {
        this.stopWatch = stopWatch;
    }

    public Boolean getAlarm() {
        return alarm;
    }

    public void setAlarm(Boolean alarm) {
        this.alarm = alarm;
    }

    public Boolean getDualTimeZone() {
        return dualTimeZone;
    }

    public void setDualTimeZone(Boolean dualTimeZone) {
        this.dualTimeZone = dualTimeZone;
    }

    public Boolean getCompass() {
        return compass;
    }

    public void setCompass(Boolean compass) {
        this.compass = compass;
    }

    public Boolean getThermometer() {
        return thermometer;
    }

    public void setThermometer(Boolean thermometer) {
        this.thermometer = thermometer;
    }

    public Boolean getMoonPhaseDisplay() {
        return moonPhaseDisplay;
    }

    public void setMoonPhaseDisplay(Boolean moonPhaseDisplay) {
        this.moonPhaseDisplay = moonPhaseDisplay;
    }

    public Boolean getSmartNotification() {
        return SmartNotification;
    }

    public void setSmartNotification(Boolean smartNotification) {
        SmartNotification = smartNotification;
    }

    public Boolean getBluetooth() {
        return Bluetooth;
    }

    public void setBluetooth(Boolean bluetooth) {
        Bluetooth = bluetooth;
    }

    public Boolean getGPS() {
        return GPS;
    }

    public void setGPS(Boolean GPS) {
        this.GPS = GPS;
    }

    public Boolean getHeartRateMonitoring() {
        return HeartRateMonitoring;
    }

    public void setHeartRateMonitoring(Boolean heartRateMonitoring) {
        HeartRateMonitoring = heartRateMonitoring;
    }

    public Boolean getBloodOxygenMonitoring() {
        return bloodOxygenMonitoring;
    }

    public void setBloodOxygenMonitoring(Boolean bloodOxygenMonitoring) {
        this.bloodOxygenMonitoring = bloodOxygenMonitoring;
    }

    public Boolean getSleepTracking() {
        return sleepTracking;
    }

    public void setSleepTracking(Boolean sleepTracking) {
        this.sleepTracking = sleepTracking;
    }

    public Boolean getStepCounter() {
        return stepCounter;
    }

    public void setStepCounter(Boolean stepCounter) {
        this.stepCounter = stepCounter;
    }

    public Boolean getMusicControl() {
        return musicControl;
    }

    public void setMusicControl(Boolean musicControl) {
        this.musicControl = musicControl;
    }

    public String getVoiceAssistantSupport() {
        return VoiceAssistantSupport;
    }

    public void setVoiceAssistantSupport(String voiceAssistantSupport) {
        VoiceAssistantSupport = voiceAssistantSupport;
    }

    public Boolean getFallDetection() {
        return fallDetection;
    }

    public void setFallDetection(Boolean fallDetection) {
        this.fallDetection = fallDetection;
    }

    public String getCaseMaterial() {
        return caseMaterial;
    }

    public void setCaseMaterial(String caseMaterial) {
        this.caseMaterial = caseMaterial;
    }

    public String getBandMaterial() {
        return BandMaterial;
    }

    public void setBandMaterial(String bandMaterial) {
        BandMaterial = bandMaterial;
    }

    public String getDialType() {
        return DialType;
    }

    public void setDialType(String dialType) {
        DialType = dialType;
    }

    public String getDisplayType() {
        return DisplayType;
    }

    public void setDisplayType(String displayType) {
        DisplayType = displayType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getPasscode() {
        return passcode;
    }

    public void setPasscode(Boolean passcode) {
        this.passcode = passcode;
    }

    public Boolean getCustomizableWatchFaces() {
        return CustomizableWatchFaces;
    }

    public void setCustomizableWatchFaces(Boolean customizableWatchFaces) {
        CustomizableWatchFaces = customizableWatchFaces;
    }
}
