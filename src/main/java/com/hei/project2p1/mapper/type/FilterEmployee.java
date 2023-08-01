package com.hei.project2p1.mapper.type;

import com.hei.project2p1.modele.BoundedPageSize;
import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.modele.PageFromOne;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@NoArgsConstructor
public class FilterEmployee {
  String firsName;
  String lastName;
  String sex;
  String position;
  Date hireDateBefore;
  Date hireDateAfter;
  Date departureDateAfter;
  Date departureDateBefore;
  String firstNameOrder;
  String lastNameOrder;
  String sexOrder;
  String numberCode;
  String positionOrder;
  Integer page;
  Integer pageSize;
}