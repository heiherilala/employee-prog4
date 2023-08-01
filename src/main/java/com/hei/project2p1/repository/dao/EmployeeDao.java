package com.hei.project2p1.repository.dao;

import com.hei.project2p1.modele.Employee;
import com.hei.project2p1.modele.PhoneNumber;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class EmployeeDao {
  private final EntityManager entityManager;
  public List<Employee> findByCriteria(
      String firstName, String lastName, Employee.sex sex, String position,
      Date hireDateAfter , Date hireDateBefore, Date departureDateAfter, Date departureDateBefore,
      String firstNameOrder, String lastNameOrder, String sexOrder, String numberCode,
      String positionOrder, Pageable pageable
  ){
    CriteriaBuilder builder  = entityManager.getCriteriaBuilder();
    CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
    Root<Employee> root = query.from(Employee.class);
    Join<PhoneNumber, Employee> phoneNumber = root.join("phoneNumbers" , JoinType.LEFT);
    List<Predicate> predicates = new ArrayList<>();

    if (numberCode != null) {
      predicates.add(
          builder.or(
              builder.like(builder.lower(phoneNumber.get("numberCode")), "%" + numberCode + "%"),
              builder.like(phoneNumber.get("numberCode"), "%" + numberCode + "%")
          )
      );
    }

    if (firstName != null) {
      predicates.add(
          builder.or(
              builder.like(builder.lower(root.get("firstName")), "%" + firstName + "%"),
              builder.like(root.get("firstName"), "%" + firstName + "%"),
              builder.isNull(root.get("firstName"))
          )
      );
    }

    if (lastName != null) {
      predicates.add(
          builder.or(
              builder.like(builder.lower(root.get("lastName")), "%" + lastName + "%"),
              builder.like(root.get("lastName"), "%" + lastName + "%"),
              builder.isNull(root.get("lastName"))
          )
      );
    }

    if (sex != null) {
      predicates.add(
          builder.or(
              builder.equal(root.get("sex"), sex ),
              builder.isNull(root.get("sex"))
          )
      );
    }

    if (position != null) {
      predicates.add(
          builder.or(
              builder.like(builder.lower(root.get("position")), "%" + position + "%"),
              builder.like(root.get("position"), "%" + position + "%"),
              builder.isNull(root.get("position"))
          )
      );
    }

    if (hireDateAfter != null) {
      predicates.add(
          builder.or(
              builder.lessThanOrEqualTo(root.get("hireDate"), hireDateAfter)
          )
      );
    }

    if (hireDateBefore != null) {
      predicates.add(
          builder.or(
              builder.greaterThanOrEqualTo(root.get("hireDate"), hireDateBefore)
          )
      );
    }

    if (departureDateAfter != null) {
      predicates.add(
          builder.or(
              builder.greaterThanOrEqualTo(root.get("departureDate"), departureDateAfter)
          )
      );
    }

    if (departureDateBefore != null) {
      predicates.add(
          builder.or(
              builder.lessThanOrEqualTo(root.get("departureDate"), departureDateBefore)
          )
      );
    }
    query.distinct(true).where(builder.and(predicates.toArray(new Predicate[0])));


    Order firstNameSortOrder = getOrder(root, builder, firstNameOrder, "firstName");
    Order lastNameSortOrder = getOrder(root, builder, lastNameOrder, "lastName");
    Order sexSortOrder = getOrder(root, builder, sexOrder, "sex");
    Order positionSortOrder = getOrder(root, builder, positionOrder, "position");

    List<Order> orders = new ArrayList<>();
    if (firstNameSortOrder != null) {
      orders.add(firstNameSortOrder);
    }
    if (lastNameSortOrder != null) {
      orders.add(lastNameSortOrder);
    }
    if (sexSortOrder != null) {
      orders.add(sexSortOrder);
    }
    if (positionSortOrder != null) {
      orders.add(positionSortOrder);
    }

    if (orders.isEmpty()) {
      query.orderBy(QueryUtils.toOrders(pageable.getSort(), root, builder));
    } else {
      query.orderBy(orders);
    }

    return entityManager.createQuery(query)
        .setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
        .setMaxResults(pageable.getPageSize())
        .getResultList();
  }

  private Order getOrder(Root<Employee> root, CriteriaBuilder builder, String order,
                         String property) {
    if (order == null || order.isEmpty()) {
      return null;
    }
    if (order.equalsIgnoreCase("ASC")) {
      return builder.asc(root.get(property));
    } else if (order.equalsIgnoreCase("DESC")) {
      return builder.desc(root.get(property));
    } else {
      return null;
    }
  }
}
