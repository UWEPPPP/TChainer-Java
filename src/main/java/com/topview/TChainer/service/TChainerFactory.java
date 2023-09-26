package com.topview.TChainer.service;

import com.topview.TChainer.service.impl.CnsServiceImpl;
import com.topview.TChainer.service.impl.DataServiceImpl;

public class TChainerFactory {
    public static DataService getDataService() {
        return new DataServiceImpl();
    }

    public static CnsService getCnsService() {
        return new CnsServiceImpl();
    }
}
