package org.phpjava.core;

import org.phpjava.config.Language;

 public class CharBuffer
 {
   private int DEFAULTLEN;
   private byte[] buff;
   private int point;
 
   public CharBuffer()
   {
     this(30);
   }
 
   public CharBuffer(int len)
   {
     this.DEFAULTLEN = 30;
     this.buff = new byte[0];
     this.point = 0;
 
     this.buff = new byte[len];
     this.DEFAULTLEN = len;
   }
 
   public void append(byte c)
   {
     if (this.point >= this.buff.length) {
       reAllotArray();
     }
     this.buff[(this.point++)] = c;
   }
 
   public void append(char c)
   {
     append((byte)c);
   }
 
   public void append(int c)
   {
     append((byte)c);
   }
 
   private void reAllotArray() {
     byte[] newbuff = new byte[this.buff.length * 2];
     copy(newbuff, this.buff);
     this.buff = newbuff;
 
     System.gc();
   }
 
   private void copy(byte[] src, byte[] dec) {
     if (src.length < dec.length) {
       throw new IllegalArgumentException(Language.arrayException + ".");
     }
     for (int i = 0; i < dec.length; ++i)
       src[i] = dec[i];
   }
 
   public String toString()
   {
     return new String(this.buff, 0, this.point);
   }
 
   public void delete()
   {
     this.buff = new byte[this.DEFAULTLEN];
     this.point = 0;
     System.gc();
   }
 }