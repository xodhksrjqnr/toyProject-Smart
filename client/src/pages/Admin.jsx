import React, { useEffect, useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { useProductApi } from '../context/ProductApiContext';
import ControlCard from '../components/ControlCard';
import Pagenation from '../components/Pagenation';

export default function Admin() {
  const { product } = useProductApi();
  const [page, setPage] = useState(0);
  const location = useLocation();
  const queryPageNum = new URLSearchParams(location.search).get('page');

  const {
    isLoading,
    error,
    data: products,
  } = useQuery(['products', page], () => product.registeredProducts(page), {
    staleTime: 1000 * 60 * 5,
  });
  const handlePage = (num) => setPage((prev) => (prev = num));
  useEffect(() => {
    if (!queryPageNum) return;
    setPage((prev) => (prev = queryPageNum - 1));
  }, [queryPageNum]);

  if (isLoading) return <p className="w-full">Loading...</p>;
  if (error) return <p className="w-full">Something is wrong</p>;
  const { totalPages, number, first, last } = products;

  return (
    <div className="w-full p-8">
      <button className="bg-rose-400 rounded-xl p-1 hover:bg-rose-300 mb-4">
        <Link to="register" className="text-sm text-white text-bold">
          상품 추가
        </Link>
      </button>
      <Pagenation
        totalPages={totalPages}
        page={number}
        first={first}
        last={last}
        state="admin"
        onClickPage={handlePage}
      />
      <section>
        <ul className="w-full flex justify-start flex-wrap">
          {products.content.map((product) => (
            <ControlCard key={product.productId} product={product} />
          ))}
        </ul>
      </section>
    </div>
  );
}
