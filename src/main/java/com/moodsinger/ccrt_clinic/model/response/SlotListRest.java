package com.moodsinger.ccrt_clinic.model.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SlotListRest {

  private List<SlotRest> Saturday;
  private List<SlotRest> Sunday;
  private List<SlotRest> Monday;
  private List<SlotRest> Tuesday;
  private List<SlotRest> Wednesday;
  private List<SlotRest> Thursday;
  private List<SlotRest> Friday;

  public SlotListRest() {
    Saturday = new ArrayList<>();
    Sunday = new ArrayList<>();
    Monday = new ArrayList<>();
    Tuesday = new ArrayList<>();
    Wednesday = new ArrayList<>();
    Thursday = new ArrayList<>();
    Friday = new ArrayList<>();
  }

  public void sort() {
    Comparator<SlotRest> compareByStartTime = (SlotRest a, SlotRest b) -> a.getStartTime()
        .compareTo(b.getStartTime());
    Collections.sort(Saturday, compareByStartTime);
    Collections.sort(Sunday, compareByStartTime);
    Collections.sort(Monday, compareByStartTime);
    Collections.sort(Tuesday, compareByStartTime);
    Collections.sort(Wednesday, compareByStartTime);
    Collections.sort(Thursday, compareByStartTime);
    Collections.sort(Friday, compareByStartTime);
  }

  public void add(String dayCode, SlotRest slot) {
    if (dayCode.equals("SAT")) {
      Saturday.add(slot);
    } else if (dayCode.equals("SUN")) {
      Sunday.add(slot);
    } else if (dayCode.equals("MON")) {
      Monday.add(slot);
    } else if (dayCode.equals("TUE")) {
      Tuesday.add(slot);
    } else if (dayCode.equals("WED")) {
      Wednesday.add(slot);
    } else if (dayCode.equals("THU")) {
      Thursday.add(slot);
    } else if (dayCode.equals("FRI")) {
      Friday.add(slot);
    }
  }

  public List<SlotRest> getSaturday() {
    return Saturday;
  }

  public void setSaturday(List<SlotRest> saturday) {
    Saturday = saturday;
  }

  public List<SlotRest> getSunday() {
    return Sunday;
  }

  public void setSunday(List<SlotRest> sunday) {
    Sunday = sunday;
  }

  public List<SlotRest> getMonday() {
    return Monday;
  }

  public void setMonday(List<SlotRest> monday) {
    Monday = monday;
  }

  public List<SlotRest> getTuesday() {
    return Tuesday;
  }

  public void setTuesday(List<SlotRest> tuesday) {
    Tuesday = tuesday;
  }

  public List<SlotRest> getWednesday() {
    return Wednesday;
  }

  public void setWednesday(List<SlotRest> wednesday) {
    Wednesday = wednesday;
  }

  public List<SlotRest> getThursday() {
    return Thursday;
  }

  public void setThursday(List<SlotRest> thursday) {
    Thursday = thursday;
  }

  public List<SlotRest> getFriday() {
    return Friday;
  }

  public void setFriday(List<SlotRest> friday) {
    Friday = friday;
  }

}
