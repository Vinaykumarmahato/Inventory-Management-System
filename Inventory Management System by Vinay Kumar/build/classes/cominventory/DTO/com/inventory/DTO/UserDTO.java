// Source code is decompiled from a .class file using FernFlower decompiler.
package com.inventory.DTO;

public class UserDTO {
   private int ID;
   private String fullName;
   private String location;
   private String phone;
   private String username;
   private String password;
   private String userType;
   private String inTime;
   private String outTime;

   public UserDTO() {
   }

   public String getInTime() {
      return this.inTime;
   }

   public void setInTime(String inTime) {
      this.inTime = inTime;
   }

   public String getOutTime() {
      return this.outTime;
   }

   public void setOutTime(String outTime) {
      this.outTime = outTime;
   }

   public int getID() {
      return this.ID;
   }

   public void setID(int ID) {
      this.ID = ID;
   }

   public String getFullName() {
      return this.fullName;
   }

   public void setFullName(String fullName) {
      this.fullName = fullName;
   }

   public String getLocation() {
      return this.location;
   }

   public void setLocation(String location) {
      this.location = location;
   }

   public String getPhone() {
      return this.phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getUsername() {
      return this.username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getUserType() {
      return this.userType;
   }

   public void setUserType(String userType) {
      this.userType = userType;
   }
}
