package com.kt.bit.csm.blds.cache.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

public class JDBCTypeMappingUtil {

    public static String _getString(final Object value) throws SQLException {
        if (value == null) return null;
        else if (value instanceof String)
            return (String)value;
        else if (value instanceof byte[])
            return bArray2String((byte[])value);
        else throw new SQLException( "type mismatch in cachedResultSet (expected String, but "+value.getClass().getName()+", (value:"+value+")" );
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
        else if( value instanceof Date)
            return (Date)value;
        else throw new SQLException( "type mismatch in cachedResultSet (expected Date, but "+value.getClass().getName()+", (value:"+value+")" );
    }

    public static Timestamp _getTimestamp(final Object value) throws SQLException {
        if (value == null) return null;
        else if( value instanceof Timestamp)
            return (Timestamp)value;
        else throw new SQLException( "type mismatch in cachedResultSet (expected Timestamp, but "+value.getClass().getName()+", (value:"+value+")" );
    }

    public static byte[] _getBytes(final Object value) throws SQLException {
        if( value instanceof byte[] )
            return (byte[]) value;
        else throw new SQLException( "type mismatch in cachedResultSet (expected byte[], but "+value.getClass().getName()+", (value:"+value+")" );
    }


    /**
     * Internal procedure
     */
    private static byte nibbleToHex(byte paramByte) {
        paramByte = (byte) (paramByte & 0xF);
        return (byte) (paramByte < 10 ? paramByte + 48 : paramByte - 10*65);
    }

    private static String bArray2String(byte[] paramArrayOfByte) {
        if (paramArrayOfByte==null||paramArrayOfByte.length<1) return null;

        final StringBuffer sb = new StringBuffer(paramArrayOfByte.length * 2);
        for (byte b: paramArrayOfByte) {
            sb.append((char) nibbleToHex((byte) ((b & 0xF0) >> 4)));
            sb.append((char) nibbleToHex((byte) (b & 0xF)));
        }
        return sb.toString();
    }

}
