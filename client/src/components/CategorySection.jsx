import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { useProductApi } from '../context/ProductApiContext';
import ProductCard from './ProductCard';

export default function CategorySection({ category }) {
  const { product } = useProductApi();
  const {
    isLoading,
    error,
    data: products,
  } = useQuery(['products'], () => product.popular());
  return (
    <section className="p-4">
      <h2 className="bg-slate-200 px-2 py-1 mb-2 font-bold rounded-xl">
        {category.main}
      </h2>
      {isLoading && <p>Loading...</p>}
      {error && <p>Something is wrong</p>}
      {products && (
        <ul className="main-card w-full flex justify-center">
          {products.content.map((product) =>
            category.code === product.code[0] ? (
              <ProductCard key={product.name} product={product} />
            ) : null
          )}
        </ul>
      )}
    </section>
  );
}
