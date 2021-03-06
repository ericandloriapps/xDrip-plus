package com.eveningoutpost.dexdrip.utils;

import com.eveningoutpost.dexdrip.Home;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by andy on 01/06/16.
 */
public enum DexCollectionType {

    None("None"),
    BluetoothWixel("BluetoothWixel"),
    DexcomShare("DexcomShare"),
    DexbridgeWixel("DexbridgeWixel"),
    LimiTTer("LimiTTer"),
    WifiBlueToothWixel("WifiBlueToothWixel"),
    WifiWixel("WifiWixel"),
    DexcomG5("DexcomG5"),
    WifiDexBridgeWixel("WifiDexbridgeWixel"),
    Follower("Follower");

    String internalName;
    private static final Map<String, DexCollectionType> mapToInternalName;
    private static final HashSet<DexCollectionType> usesBluetooth = new HashSet<>();
    private static final HashSet<DexCollectionType> usesBtWixel = new HashSet<>();
    private static final HashSet<DexCollectionType> usesWifi = new HashSet<>();
    private static final HashSet<DexCollectionType> usesXbridge = new HashSet<>();
    private static final HashSet<DexCollectionType> usesFiltered = new HashSet<>();

    public static boolean does_have_filtered = false; // TODO this could get messy with GC

    static {
        mapToInternalName = new HashMap<>();

        for (DexCollectionType dct : values()) {
            mapToInternalName.put(dct.internalName, dct);
        }

        Collections.addAll(usesBluetooth, BluetoothWixel, DexcomShare, DexbridgeWixel, LimiTTer, WifiBlueToothWixel, DexcomG5, WifiDexBridgeWixel);
        Collections.addAll(usesBtWixel, BluetoothWixel, LimiTTer, WifiBlueToothWixel);
        Collections.addAll(usesWifi, WifiBlueToothWixel,WifiWixel,WifiDexBridgeWixel);
        Collections.addAll(usesXbridge, DexbridgeWixel,WifiDexBridgeWixel);
        Collections.addAll(usesFiltered, DexbridgeWixel, WifiDexBridgeWixel, DexcomG5, WifiWixel, Follower); // Bluetooth and Wifi+Bluetooth need dynamic mode
    }


    DexCollectionType(String name) {
        this.internalName = name;
    }


    public static DexCollectionType getType(String dexCollectionType) {

        if (mapToInternalName.containsKey(dexCollectionType))
            return mapToInternalName.get(dexCollectionType);
        else
            return None;
    }

    public static DexCollectionType getDexCollectionType() {
        return getType(Home.getPreferencesStringWithDefault("dex_collection_method", "BluetoothWixel"));
    }

    public static boolean hasBluetooth() {
        return usesBluetooth.contains(getDexCollectionType());
    }

    public static boolean hasBtWixel() { return usesBtWixel.contains(getDexCollectionType()); }

    public static boolean hasXbridgeWixel() {
        return usesXbridge.contains(getDexCollectionType());
    }

    public static boolean hasWifi() {
        return usesWifi.contains(getDexCollectionType());
    }

    public static boolean hasFiltered() {
        return does_have_filtered || usesFiltered.contains(getDexCollectionType());
    }

}
