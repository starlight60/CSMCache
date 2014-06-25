package com.kt.bit.csm.blds.utility;

import com.kt.bit.csm.blds.cache.CachedResultSet;

public interface DataFormatter {

    public String toJson(CachedResultSet resultSet);
    public CachedResultSet fromJson(String json);

}
