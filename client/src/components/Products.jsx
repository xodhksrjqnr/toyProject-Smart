import React from 'react';
import { useQuery } from '@tanstack/react-query';
import ProductCard from './ProductCard';
import FakeProduct from '../api/fakeProduct';
import Product from '../api/product';

export default function Products() {
  const {
    isLoading,
    error,
    data: products,
  } = useQuery(['products'], () => {
    const product = new Product();
    return product.popular();
  });
  return (
    <>
      {isLoading && <p>Loading...</p>}
      {error && <p>Something is wrong</p>}
      {products && (
        <ul>
          {products.map((product) => (
            <ProductCard key={product.name} product={product} />
          ))}
        </ul>
      )}
    </>
  );
}
