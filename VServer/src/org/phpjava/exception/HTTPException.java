package org.phpjava.exception;

 public abstract class HTTPException extends Exception
 {
   private String errorMessage;
 
   public HTTPException(String mess)
   {
     this.errorMessage = mess; }
 
   public HTTPException() {
     this.errorMessage = "HTTP Error"; }
 
   public String toString() {
     return this.errorMessage;
   }
 
   public abstract int getHttpErrorCode();
 }