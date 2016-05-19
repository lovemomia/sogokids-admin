package com.sogokids.dealGroup.service;

import com.sogokids.dealGroup.model.DealGroupJoined;

import java.util.List;

/**
 * Created by hoze on 16/4/21.
 */
public interface DealGroupJoinedService {
    public List<DealGroupJoined> getJoinsByGroupId(int group_id);
}
