package taewan.Smart.domain.product.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import taewan.Smart.domain.product.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductDaoImpl implements ProductDao {

    private final EntityManager entityManager;

    @Override
    public boolean existsByName(String name) {
        return entityManager
                .createNativeQuery("select exists(select * from Product where name=:name limit 1)")
                .setParameter("name", name)
                .getSingleResult() == BigInteger.ONE;
    }

    @Override
    public boolean existsByNameAndProductIdNot(Long productId, String name) {
        return entityManager
                .createNativeQuery("select exists(select * from Product where name=:name and product_id!=:productId limit 1)")
                .setParameter("name", name)
                .setParameter("productId", productId)
                .getSingleResult() == BigInteger.ONE;
    }

    @Override
    public Optional<Product> findById(Long productId) {
        try {
            return Optional.of(entityManager.find(Product.class, productId));
        } catch (NullPointerException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Page<Product> findAllByFilter(Pageable pageable, String code, String name) {
        String sql = "select * from Product" +
                createWhere(code, name) +
                createSort(pageable.getSort()) +
                " limit " + pageable.getPageSize() + " offset " + pageable.getOffset();
        Query query = entityManager.createNativeQuery(sql, Product.class);

        if (!code.isEmpty()) {
            query.setParameter("code", "%" + code + "%");
        }
        if (!name.isEmpty()) {
            query.setParameter("name", "%" + name + "%");
        }
        return new PageImpl<Product>(query.getResultList(), pageable, count());
    }

    @Override
    public Long save(Product product) {
        entityManager.persist(product);
        return product.getProductId();
    }

    @Override
    public void deleteById(Long productId) {
        entityManager
                .createQuery("delete from Product p where p.productId=:productId")
                .setParameter("productId", productId)
                .executeUpdate();
    }

    @Override
    public long count() {
        return ((Number) entityManager
                .createNativeQuery("select count(*) from Product")
                .getSingleResult())
                .longValue();
    }

    private String createWhere(String code, String name) {
        return code.isEmpty() && name.isEmpty() ?
                "" : (" where "
                + (code.isEmpty() ? "" : "code like :code")
                + (!code.isEmpty() && !name.isEmpty() ? " and " : "")
                + (name.isEmpty() ? "" : "name like :name"));
    }

    private String createSort(Sort sort) {
        StringBuilder sortSql = new StringBuilder();
        List<Sort.Order> orders = sort.get().collect(Collectors.toList());
        int size = orders.size();

        for (Sort.Order o : orders) {
            sortSql
                    .append(" ")
                    .append(o.getProperty())
                    .append(" ")
                    .append(o.getDirection());
            if (size > 1) {
                sortSql.append(",");
                size--;
            }
        }
        if (sortSql.length() != 0) {
            sortSql.insert(0, " order by");
        } else {

        }
        return sortSql.toString();
    }
}
