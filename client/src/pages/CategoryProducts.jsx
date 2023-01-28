import React from 'react';
import { Link, useLocation, useParams } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { useProductApi } from '../context/ProductApiContext';
import ProductCard from '../components/ProductCard';

export default function CategoryProducts() {
  const { id } = useParams();
  const { state } = useLocation();
  const { product } = useProductApi();
  const {
    isLoading,
    error,
    data: categoryProduct,
  } = useQuery(['categoryProduct', id], () => product.mainProduct(id), {
    staleTime: 1000 * 60 * 5,
  });

  if (isLoading) return <p className="w-full">Loading...</p>;
  if (error) return <p className="w-full">Something is wrong</p>;

  return (
    <div className="flex flex-col w-full p-4">
      <h2 className="flex mb-4">
        {state.map((category) => (
          <Link
            to={`/categories/${category.code}`}
            state={state}
            key={category.code}
          >
            <p
              className={`text-sm mr-2 px-2 py-1 text-gray-500 cursor-pointer bg-gray-300 rounded-full shadow-md hover:text-blue-900 ${
                category.code === id ? 'text-blue-600 font-bold' : 'text-black'
              }`}
            >
              {category.title}
            </p>
          </Link>
        ))}
      </h2>
      <section>
        <ul className="w-full flex justify-start">
          {categoryProduct.content.map((product) => (
            <ProductCard key={product.id} product={product} />
          ))}
        </ul>
      </section>
    </div>
  );
}
