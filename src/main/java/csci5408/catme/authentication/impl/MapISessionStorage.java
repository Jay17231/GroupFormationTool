package csci5408.catme.authentication.impl;

import csci5408.catme.authentication.ISessionStore;
import csci5408.catme.dto.UserSummary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MapISessionStorage implements ISessionStore {

    Map<String, UserSummary> map;

    public MapISessionStorage() {
        map = new HashMap<>();
    }

    @Override
    public UserSummary getSession(String key) {
        return map.get(key);
    }

    @Override
    public String setSession(UserSummary userSummary) {
        String key = UUID.randomUUID().toString();
        map.put(key, userSummary);
        return key;
    }

    @Override
    public boolean invalidateSession(String value) {
        if(map.containsKey(value)) {
            map.remove(value);
            return true;
        }
        return false;
    }
}
