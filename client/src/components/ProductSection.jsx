import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { useProductApi } from '../context/ProductApiContext';
import ProductCard from './ProductCard';

export default function ProductSection({ state, orderFilter, isSearch }) {
  const { product } = useProductApi();
  const {
    isLoading,
    error,
    data: products,
  } = useQuery(
    ['products', state, orderFilter],
    () => product.products(state, orderFilter, isSearch),
    {
      staleTime: 1000 * 60 * 5,
    }
  );

  if (isLoading) return <p className="w-full">Loading...</p>;
  if (error) return <p className="w-full">Something is wrong</p>;

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
    </section>
  );
}
