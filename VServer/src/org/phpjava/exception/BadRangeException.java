package org.phpjava.exception;

public class BadRangeException extends HTTPException
 {
   public BadRangeException()
   {
   }
 
   public BadRangeException(String s)
   {
     super(s);
   }
 
   public int getHttpErrorCode() {
     return 416;
   }
 }