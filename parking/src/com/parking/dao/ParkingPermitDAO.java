package com.parking.dao;

import java.util.ArrayList;
import java.util.List;

import com.parking.bean.ParkingPermit;

public class ParkingPermitDAO {

    private List<ParkingPermit> permits = new ArrayList<>();

    public ParkingPermit findParkingPermit(String permitID) {
        for (ParkingPermit p : permits) {
            if (p.getPermitID().equals(permitID)) {
                return p;
            }
        }
        return null;
    }
    
    public List<ParkingPermit> findPermitsByHolder(String permitHolderID) {
        List<ParkingPermit> result = new ArrayList<>();
        for (ParkingPermit p : permits) {
            if (p.getPermitHolderID().equals(permitHolderID)) {
                result.add(p);
            }
        }
        return result;
    }

    public boolean insertParkingPermit(ParkingPermit permit) {
        permits.add(permit);
        return true;
    }
    
    public boolean updatePermitStatus(String permitID, String newStatus) {
        ParkingPermit p = findParkingPermit(permitID);
        if (p != null) {
            p.setPermitStatus(newStatus);
            return true;
        }
        return false;
    }
    
    public ParkingPermit findActivePermitOfVehicleAndZone(String vehicleRegNo, String zoneCode) {
        for (ParkingPermit p : permits) {
            if (p.getPermitStatus().equals("ACTIVE") &&
                p.getVehicleRegNo().equals(vehicleRegNo) &&
                p.getZoneCode().equals(zoneCode)) {
                return p;
            }
        }
        return null;
    }
}

