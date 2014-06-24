package com.kt.bit.csm.blds.cache.util;

import oracle.jdbc.util.RepConversion;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class JDBCTypeMappingUtil {

    public static String _getString(final Object value) throws SQLException {
        if (value == null) return null;
        else if (value instanceof String)
            return (String)value;
        else if (value instanceof List)
            return RepConversion.bArray2String(listToArray((List) value));
        else if (value instanceof byte[])
            return RepConversion.bArray2String((byte[]) value);
        else throw new SQLException( "type mismatch in cachedResultSet (expected String, but "+value.getClass().getName()+", (value:"+value+")" );
    }

    private static byte[] listToArray(final List value) {
        if (value==null||value.size()<1) return null;

        byte[] bytes = new byte[value.size()];
        int i = 0;
        for (Object obj: value) {
            if (obj!=null&&obj instanceof Double) {
                bytes[i] = ((Double) obj).byteValue();
                i++;
            }
        }
        return bytes;
    }

    public static Integer _getInt(final Object value) throws SQLException {
        if (value == null) return null;
        else if( value instanceof BigDecimal) {
            return ((BigDecimal) value).intValue();
        } else if (value instanceof Double) {
            return ((Double) value).intValue();
        } else if (value instanceof Integer)
            return (Integer) value;
        else throw new SQLException( "type mismatch in cachedResultSet (expected int, but "+value.getClass().getName()+", (value:"+value+")" );
    }

    public static Long _getLong(final Object value) throws SQLException {
        if (value == null) return null;
        else if( value instanceof BigDecimal)
            return ((BigDecimal) value).longValue();
        else if (value instanceof Long)
            return (Long) value;
        else if (value instanceof Integer)
            return ((Integer) value).longValue();
        else if (value instanceof Double)
            return ((Double) value).longValue();
        else throw new SQLException( "type mismatch in cachedResultSet (expected Long, but "+value.getClass().getName()+", (value:"+value+")" );
    }

    public static Date _getDate(final Object value) throws SQLException {
        if (value == null) return null;
        // From JDBC
        else if( value instanceof Timestamp)
            return new Date(((Timestamp)value).getTime());
        else if( value instanceof Date)
            return (Date)value;
        // From JSON
        else if (value instanceof String) {
            try {
                return new Date(Long.valueOf((String)value));
            } catch (NumberFormatException e) {
                throw new SQLException( "this is not timestamp type (value:"+value+")" );
            }
        }
        else throw new SQLException( "type mismatch in cachedResultSet (expected Date, but "+value.getClass().getName()+", (value:"+value+")" );
    }

    public static Timestamp _getTimestamp(final Object value) throws SQLException {
        if (value == null) return null;
        // From JDBC
        else if (value instanceof Timestamp)
            return (Timestamp)value;
        else if (value instanceof Date)
            return new Timestamp(((Date)value).getTime());
        // From JSON
        else if (value instanceof String) {
            try {
                return new Timestamp(Long.valueOf((String)value));
            } catch (NumberFormatException e) {
                throw new SQLException( "this is not timestamp type (value:"+value+")" );
            }
        } else throw new SQLException( "type mismatch in cachedResultSet (expected Timestamp, but "+value.getClass().getName()+", (value:"+value+")" );
    }

    public static byte[] _getBytes(final Object value) throws SQLException {
        if( value instanceof byte[] )
            return (byte[]) value;
        else throw new SQLException( "type mismatch in cachedResultSet (expected byte[], but "+value.getClass().getName()+", (value:"+value+")" );
    }

}
