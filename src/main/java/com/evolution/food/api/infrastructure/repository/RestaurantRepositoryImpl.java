package com.evolution.food.api.infrastructure.repository;

import com.evolution.food.api.domain.model.Restaurant;
import com.evolution.food.api.domain.repository.RestaurantRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurant> find(String name, BigDecimal initialFreightRate, BigDecimal finalFreightRate) {
        var jpql = "from Restaurant where name like :name " +
                "and freightRate between :initialFreightRate and :finalFreightRate";

        return manager.createQuery(jpql, Restaurant.class)
                .setParameter("name", "%" + name + "%")
                .setParameter("initialFreightRate", initialFreightRate )
                .setParameter("finalFreightRate", finalFreightRate)
                .getResultList();
    }

    @Override
    public List<Restaurant> dinamicFind(String name, BigDecimal initialFreightRate, BigDecimal finalFreightRate) {

        var jpql = new StringBuilder();

        var parameters = new HashMap<String, Object>();

            jpql.append("from Restaurant where 0 = 0 ");

            if (null != name) {
                jpql.append("and name like :name ");
                parameters.put("name", "%" + name + "%");
            }

            if (null != initialFreightRate) {
                jpql.append("and freightRate >= :initialFreightRate ");
                parameters.put("initialFreightRate", initialFreightRate);
            }

            if (null != finalFreightRate) {
                jpql.append("and freightRate <= :finalFreightRate ");
                parameters.put("finalFreightRate", finalFreightRate);
            }

        TypedQuery<Restaurant> query = manager.createQuery(jpql.toString(), Restaurant.class);
//            parameters.forEach((key, value) -> query.setParameter(key, value));
            parameters.forEach(query::setParameter);

            return query.getResultList();

    }

    @Override
    public List<Restaurant> findWithCriteria(String name, BigDecimal initialFreightRate, BigDecimal finalFreightRate) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Restaurant> criteriaQuery = builder.createQuery(Restaurant.class);
        criteriaQuery.from(Restaurant.class);

        TypedQuery<Restaurant> query = manager.createQuery(criteriaQuery);
        return query.getResultList();
    }

}
