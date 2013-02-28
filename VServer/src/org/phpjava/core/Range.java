package org.phpjava.core;

import org.phpjava.exception.BadRangeException;

 public class Range
 {
   private long firstPos = 0L;
   private long lastPos = 0L;
 
   public Range(String r)
     throws BadRangeException
   {
     String BYTESUNIT = "bytes";
     String Dividing = "-";
     String byte_range_spec = null;
     int div = 0;
     if (r.toLowerCase().startsWith("bytes")) {
       int begin = r.indexOf(61);
       if (begin >= "bytes".length()) {
         byte_range_spec = r.substring(begin + 1).trim();
         div = byte_range_spec.indexOf("-"); } }
     try {
       this.firstPos = Long.parseLong(byte_range_spec.substring(0, div));
       String last_byte_pos = byte_range_spec.substring(div + 1);
       if (last_byte_pos.length() > 0)
         this.lastPos = Long.parseLong(last_byte_pos);
       else {
         this.lastPos = -1L;
       }
 
       return;
     }
     catch (Exception localException)
     {
       throw new BadRangeException();
     }
   }
 
   public Range(long first, long last)
   {
     this.firstPos = first;
     this.lastPos = last;
   }
 
   public void setLastPos(long newLast)
   {
     if (this.lastPos == -1L)
       this.lastPos = newLast;
   }
 
   public long getFirstPos()
   {
     return this.firstPos;
   }
 
   public long getLastPos()
   {
     return this.lastPos;
   }
 }