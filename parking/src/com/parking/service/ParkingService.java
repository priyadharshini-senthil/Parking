package com.parking.service;


import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import com.parking.bean.PermitHolder;
import com.parking.dao.ParkingPermitDAO;
import com.parking.dao.PermitHolderDAO;
import com.parking.bean.ParkingPermit;



public class ParkingService {


    private PermitHolderDAO permitHolderDAO = new PermitHolderDAO();
    private ParkingPermitDAO parkingPermitDAO = new ParkingPermitDAO();

    
    public PermitHolder viewPermitHolderById(String permitHolderID) {

        if (permitHolderID == null || permitHolderID.isEmpty()) {
            return null;
        }

        return permitHolderDAO.findPermitHolder(permitHolderID);
    }

    
    public List<PermitHolder> viewAllPermitHolders() {
        return permitHolderDAO.viewAllPermitHolders();
    }

    public boolean registerNewPermitHolder(PermitHolder holder) {

        if (holder.getPermitHolderID() == null || holder.getPermitHolderID().isEmpty()) return false;
        if (holder.getFullName() == null || holder.getFullName().isEmpty()) return false;

        PermitHolder existing = permitHolderDAO.findPermitHolder(holder.getPermitHolderID());
        if (existing != null) return false;

        if (holder.getStatus() == null || holder.getStatus().isEmpty()) {
            holder.setStatus("ACTIVE");
        }
        
        return permitHolderDAO.insertPermitHolder(holder);
    }


    public ParkingPermit viewParkingPermitDetails(String permitID) {

        if (permitID == null || permitID.isEmpty()) return null;

        return parkingPermitDAO.findParkingPermit(permitID);
    }

    public List<ParkingPermit> viewPermitsByHolder(String permitHolderID) {
        return parkingPermitDAO.findPermitsByHolder(permitHolderID);
    }

    public boolean applyForNewPermit(ParkingPermit permit) {

        if (permit.getPermitID() == null || permit.getPermitID().isEmpty()) return false;
        if (permit.getVehicleRegNo() == null || permit.getVehicleRegNo().isEmpty()) return false;
        if (permit.getZoneCode() == null || permit.getZoneCode().isEmpty()) return false;

        if (permit.getPermitStartDate() == null || permit.getPermitEndDate() == null) return false;

        if (permit.getPermitEndDate().before(permit.getPermitStartDate())) return false;

        PermitHolder holder = permitHolderDAO.findPermitHolder(permit.getPermitHolderID());
        if (holder == null) return false;
        if (!holder.getStatus().equals("ACTIVE")) return false;

        ParkingPermit active = parkingPermitDAO.findActivePermitOfVehicleAndZone(permit.getVehicleRegNo(), permit.getZoneCode());

        if (active != null) return false;

        return parkingPermitDAO.insertParkingPermit(permit);
    }


    public boolean renewPermit(String permitID, Date newEndDate) {

        if (permitID == null || permitID.isEmpty()) return false;
        if (newEndDate == null) return false;

        ParkingPermit p = parkingPermitDAO.findParkingPermit(permitID);
        if (p == null) return false;
        
        if (p.getPermitStatus().equals("CANCELLED")) return false;

        if (newEndDate.before(p.getPermitEndDate())) return false;

        p.setPermitEndDate(newEndDate);
        p.setPermitStatus("ACTIVE");

        return true;
    }

    public boolean recordViolation(String permitID, double fineToAdd) {

        if (permitID == null || permitID.isEmpty()) return false;
        if (fineToAdd <= 0) return false;

        ParkingPermit p = parkingPermitDAO.findParkingPermit(permitID);
        if (p == null) return false;

        if (p.getPermitStatus().equals("CANCELLED") || p.getPermitStatus().equals("EXPIRED")) return false;

        BigDecimal newFine = p.getOutstandingFineAmount().add(BigDecimal.valueOf(fineToAdd));
        p.setOutstandingFineAmount(newFine);

        p.setViolationCount(p.getViolationCount() + 1);

        return true;
    }

    public boolean deactivatePermitHolder(String permitHolderID) {

        if (permitHolderID == null || permitHolderID.isEmpty()) return false;

        PermitHolder holder = permitHolderDAO.findPermitHolder(permitHolderID);
        if (holder == null) return false;
        
        holder.setStatus("INACTIVE");
        
        List<ParkingPermit> permits = parkingPermitDAO.findPermitsByHolder(permitHolderID);

        for (ParkingPermit p : permits) {
            if (p.getPermitStatus().equals("ACTIVE")) {
                p.setPermitStatus("CANCELLED");
            }
        }

        return true;
    }
}
