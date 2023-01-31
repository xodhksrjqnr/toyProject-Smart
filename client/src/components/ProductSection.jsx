import React, { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { useProductApi } from '../context/ProductApiContext';
import ProductCard from './ProductCard';
import Pagination from './Pagination';

export default function ProductSection({ state, orderFilter, isSearch }) {
  const { product } = useProductApi();
  const [page, setPage] = useState(0);
  const {
    isLoading,
    error,
    data: products,
  } = useQuery(
    ['products', state, orderFilter, page],
    () => product.products(state, orderFilter, page, isSearch),
    {
      staleTime: 1000 * 60 * 5,
    }
  );
  const handlePage = (num) => setPage((prev) => (prev = num));

  if (isLoading) return <p className="w-full">Loading...</p>;
  if (error) return <p className="w-full">Something is wrong</p>;

  const { totalPages, number, first, last } = products;

  return (
    <section>
      <ul className="w-full flex justify-start flex-wrap">
        {products.content.length === 0 ? (
          <div>상품이 존재 하지 않습니다.</div>
        ) : null}
        {products.content.map((product) => (
          <ProductCard key={product.id} product={product} />
        ))}
      </ul>
      <Pagination
        totalPages={totalPages}
        page={number}
        first={first}
        last={last}
        onClickPage={handlePage}
      />
    </section>
  );
}
