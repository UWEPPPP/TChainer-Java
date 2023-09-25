package com.topview.TChainer.service;

public interface DataService {

     <T>T get(int id) throws InstantiationException, IllegalAccessException;

     <T>Boolean set(int id, T data);

     <T>Boolean add(T data);

     Integer getEventsBlock(Integer id);
}
