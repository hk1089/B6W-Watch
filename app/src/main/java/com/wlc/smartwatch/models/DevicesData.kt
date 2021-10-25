package com.wlc.smartwatch.models

class DevicesData : Comparable<DevicesData>{
    var name: String
    var macId: String
    var rssi : Int

    constructor(name: String, macId: String, rssi : Int){
        this.name = name
        this.macId = macId
        this.rssi = rssi
    }

    override fun compareTo(other: DevicesData): Int {
        return other.rssi - this.rssi
    }
}