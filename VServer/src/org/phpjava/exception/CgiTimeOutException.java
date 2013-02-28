package org.phpjava.exception;

public class CgiTimeOutException extends HTTPException
 {
   public CgiTimeOutException()
   {
   }
 
   public CgiTimeOutException(String s)
   {
     super(s);
   }
 
   public int getHttpErrorCode() {
     return 501;
   }
 }