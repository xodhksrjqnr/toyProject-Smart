import React from 'react';
import { Link } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { useProductApi } from '../context/ProductApiContext';
import ControlCard from '../components/ControlCard';

export default function Admin() {
  const { product } = useProductApi();
  const {
    isLoading,
    error,
    data: products,
  } = useQuery(['products'], () => product.registeredProducts(), {
    staleTime: 1000 * 60 * 5,
  });

  if (isLoading) return <p className="w-full">Loading...</p>;
  if (error) return <p className="w-full">Something is wrong</p>;
  return (
    <div className="w-full p-8">
      <button className="bg-rose-400 rounded-xl p-1 hover:bg-rose-300 mb-4">
        <Link to="register" className="text-sm text-white text-bold">
          상품 추가
        </Link>
      </button>
      <section>
        <ul className="w-full flex justify-start flex-wrap">
          {products.content.map((product) => (
            <ControlCard key={product.id} product={product} />
          ))}
        </ul>
      </section>
    </div>
  );
}
