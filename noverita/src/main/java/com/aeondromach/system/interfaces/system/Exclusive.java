package com.aeondromach.system.interfaces.system;

import java.util.ArrayList;

import com.aeondromach.system.minor.Grant;
import com.aeondromach.system.minor.OtherStat;

public interface Exclusive {
    public void reCheck();
    public String getId();
    public ArrayList<Grant> getGrantList();
    public ArrayList<OtherStat> getStatList();
    public Boolean hasStatList();
    public Boolean hasGrantList();
}