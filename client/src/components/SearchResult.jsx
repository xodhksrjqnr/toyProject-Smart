import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { useProductApi } from '../context/ProductApiContext';
import ProductCard from '../components/ProductCard';

export default function SearchResult({ state, orderFilter }) {
  const { product } = useProductApi();
  const {
    isLoading,
    error,
    data: search,
  } = useQuery(
    ['search', state, orderFilter],
    () => product.search(state, orderFilter),
    {
      staleTime: 1000 * 60 * 5,
    }
  );

  if (isLoading) return <p className="w-full">Loading...</p>;
  if (error) return <p className="w-full">Something is wrong</p>;

  return (
    <section>
      <ul className="w-full flex justify-start flex-wrap">
        {search.content.length === 0 ? (
          <div>상품이 존재 하지 않습니다.</div>
        ) : null}
        {search.content.map((product) => (
          <ProductCard key={product.id} product={product} />
        ))}
      </ul>
    </section>
  );
}
