package com.sogokids.home.service;

import com.sogokids.home.model.Event;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/10/12.
 */
public interface EventService {

    public List<Event> getEntitys();
    public Event get(int id);
    public int insert(Event entity);
    public int update(Event entity);
    public int delete(int id);
    public Event formEntity(HttpServletRequest request, int id);
}
