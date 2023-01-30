import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { useProductApi } from '../context/ProductApiContext';
import ProductCard from '../components/ProductCard';

export default function CategoryResult({ id, orderFilter }) {
  const { product } = useProductApi();
  const {
    isLoading,
    error,
    data: categoryProduct,
  } = useQuery(
    ['categoryProduct', id, orderFilter],
    () => product.categoryProduct(id, orderFilter),
    {
      staleTime: 1000 * 60 * 5,
    }
  );
  if (isLoading) return <p className="w-full">Loading...</p>;
  if (error) return <p className="w-full">Something is wrong</p>;
  return (
    <section>
      <ul className="w-full flex justify-start flex-wrap">
        {categoryProduct.content.map((product) => (
          <ProductCard key={product.id} product={product} />
        ))}
      </ul>
    </section>
  );
}
