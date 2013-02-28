package org.phpjava.exception;

 public class CgiRequestException extends HTTPException
 {
   public CgiRequestException()
   {
   }
 
   public CgiRequestException(String s)
   {
     super(s);
   }
 
   public int getHttpErrorCode() {
     return 400;
   }
 }