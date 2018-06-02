package com.ekakashi.greenhouse.features.timeline.models;

import com.ekakashi.greenhouse.features.timeline_filter.models.FilterField;
import com.ekakashi.greenhouse.features.timeline_filter.models.FilterMember;

import java.util.ArrayList;
import java.util.List;


public class TimelineHelper {

    public static List<Integer> getMemberListId(List<FilterMember> memberList) {
        if (memberList != null && memberList.size() > 0) {
            List<Integer> idList = new ArrayList<>();
            for (int i = 0; i < memberList.size(); i++) {
                idList.add(memberList.get(i).getEkUserId());
            }
            return idList;
        }
        return null;
    }

    public static List<Integer> getPlaceListId(List<FilterField> placeList) {
        if (placeList != null && placeList.size() > 0) {
            List<Integer> idList = new ArrayList<>();
            for (int i = 0; i < placeList.size(); i++) {
                idList.add(placeList.get(i).getId());
            }
            return idList;
        }
        return null;
    }
}
