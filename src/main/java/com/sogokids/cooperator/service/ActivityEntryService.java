package com.sogokids.cooperator.service;

import com.sogokids.cooperator.model.ActivityEntry;

import java.util.List;

/**
 * Created by hoze on 16/4/1.
 */
public interface ActivityEntryService {
    public List<ActivityEntry> getActivityEntrys(int activityId);
}
