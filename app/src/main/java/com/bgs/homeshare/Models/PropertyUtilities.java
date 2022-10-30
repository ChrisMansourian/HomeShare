package com.bgs.homeshare.Models;

public class PropertyUtilities {
    private Boolean pool;
    private Boolean ac;
    private Boolean laundry;
    private Boolean dishwasher;
    private Boolean balcony;
    private Boolean fireplace;

    public PropertyUtilities(Boolean pool, Boolean ac, Boolean laundry, Boolean dishwasher, Boolean balcony, Boolean fireplace){
        this.pool = pool;
        this.ac = ac;
        this.laundry = laundry;
        this.dishwasher = dishwasher;
        this.balcony = balcony;
        this.fireplace = fireplace;
    }

    public Boolean getPool() {
        return pool;
    }

    public Boolean getAC() {
        return ac;
    }

    public Boolean getLaundry() {
        return laundry;
    }

    public Boolean getDishwasher() {
        return dishwasher;
    }

    public Boolean getBalcony() {
        return balcony;
    }

    public Boolean getFireplace() {
        return fireplace;
    }

    public void setPool(Boolean val) {
        this.pool = val;
    }

    public void setAC(Boolean val) {
        this.ac = val;
    }

    public void setLaundry(Boolean val) {
        this.laundry = val;
    }

    public void setDishwasher(Boolean val) {
        this.dishwasher = val;
    }

    public void setBalcony(Boolean val) {
        this.balcony = val;
    }

    public void setFireplace(Boolean val) {
        this.fireplace = val;
    }

    public String getUtilities() {
        String utilities = "";
        if (pool ) {
            utilities += "Pool";
        }
        if (ac ) {
            utilities += ", AC";
        }
        if (laundry ) {
            utilities += ", Laundry, ";
        }
        if (dishwasher) {
            utilities += ", Dishwasher, ";
        }
        if (balcony) {
            utilities += ", Balcony";
        }
        if (fireplace ) {
            utilities += ", Fireplace";
        }

        if((!pool) && (utilities.length() != 0)){//removing trailing ", " if it exists
            utilities = utilities.substring(2, utilities.length()-1);
        }
        return utilities;
    }

}
