package com.parking.dao;

import java.util.ArrayList;
import java.util.List;
import com.parking.bean.PermitHolder;

public class PermitHolderDAO {

    private List<PermitHolder> holders = new ArrayList<>();
    public PermitHolder findPermitHolder(String permitHolderID) {
        for (PermitHolder h : holders) {
            if (h.getPermitHolderID().equals(permitHolderID)) {
                return h;
            }
        }
        return null;
    }

    public List<PermitHolder> viewAllPermitHolders() {
        return holders;
    }

    public boolean insertPermitHolder(PermitHolder holder) {
        holders.add(holder);
        return true;
    }

    public boolean updatePermitHolderStatus(String permitHolderID, String status) {
        PermitHolder h = findPermitHolder(permitHolderID);
        if (h != null) {
            h.setStatus(status);
            return true;
        }
        return false;
    }

    public boolean deletePermitHolder(String permitHolderID) {
        PermitHolder h = findPermitHolder(permitHolderID);
        if (h != null) {
            holders.remove(h);
            return true;
        }
        return false;
    }
}

