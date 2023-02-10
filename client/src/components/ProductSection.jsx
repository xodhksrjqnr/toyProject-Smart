import React, { useEffect, useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { useProductApi } from '../context/ProductApiContext';
import ProductCard from './ProductCard';
import Pagenation from './Pagenation';
import { useLocation } from 'react-router-dom';

export default function ProductSection({ state, orderFilter, isSearch }) {
  const { product } = useProductApi();
  const [page, setPage] = useState(0);
  const location = useLocation();
  const queryPageNum = new URLSearchParams(location.search).get('page');
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
  useEffect(() => setPage((prev) => (prev = queryPageNum - 1)), [queryPageNum]);

  if (isLoading) return <p className="w-full"></p>;
  if (error) return <p className="w-full">Something is wrong</p>;

  const { totalPages, number, first, last } = products;

  return (
    <section className="flex flex-col items-center">
      <ul className="w-full max-w-6xl flex flex-wrap">
        {products.content.length === 0 ? (
          <div>상품이 존재 하지 않습니다.</div>
        ) : null}
        {products.content.map((product) => (
          <ProductCard key={product.productId} product={product} />
        ))}
      </ul>
      <Pagenation
        totalPages={totalPages}
        page={number}
        first={first}
        last={last}
        state={state}
        onClickPage={handlePage}
      />
    </section>
  );
}
