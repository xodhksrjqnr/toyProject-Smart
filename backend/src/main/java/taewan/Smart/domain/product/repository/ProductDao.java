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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductDao implements ProductRepository {

    private final EntityManager entityManager;

    @Override
    public boolean existsByName(String name) {
        return (boolean) entityManager
                .createNativeQuery("select exists(select name from Product where name =:name limit 1)")
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return Optional.of(
                entityManager.find(Product.class, productId)
        );
    }

    @Override
    public Optional<Product> findByName(String name) {
        return Optional.of(
                (Product) entityManager
                        .createNativeQuery("select * from Product where name=:name limit 1")
                        .setParameter("name", name)
                        .getSingleResult()
        );
    }

    @Override
    public Page<Product> findAllByFilter(Pageable pageable, String code, String name) {
        String sql = new StringBuilder()
                .append("select * from Product")
                .append(createWhere(code, name))
                .append(createSort(pageable.getSort()))
                .append(" limit " + pageable.getPageSize() + " offset " + pageable.getOffset())
                .toString();
        Query query = entityManager.createNativeQuery(sql);

        if (!code.isEmpty()) {
            query.setParameter("code", code);
        }
        if (!name.isEmpty()) {
            query.setParameter("name", name);
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
                + (code.isEmpty() ? "" : "code like \"%:code%\"")
                + (name.isEmpty() ? "" : "name like \"%:name%\""));
    }

    private String createSort(Sort sort) {
        StringBuilder sortSql = new StringBuilder();
        List<Sort.Order> orders = sort.get().collect(Collectors.toList());

        for (Sort.Order o : orders) {
            sortSql
                    .append(" ")
                    .append(o.getProperty())
                    .append(" ")
                    .append(o.getDirection());
        }
        if (sortSql.length() != 0) {
            sortSql.insert(0, " order by");
        }
        return sortSql.toString();
    }
}
