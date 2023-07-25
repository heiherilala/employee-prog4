package com.hei.project2p1.mapper.type;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CreatePhoneNumber {
  private String id;
  private String number;
  private String employeeId;
  private String companyId;
}