import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import Filter from '../components/Filter';
import ProductSection from '../components/ProductSection';

export default function Search() {
  const [orderFilter, setOrderFilter] = useState();
  const { state } = useLocation();

  const handleOrder = (order) => setOrderFilter((prev) => (prev = order));

  return (
    <div className="w-full p-4">
      <p>
        <strong>{state}</strong>의 검색 결과
      </p>
      <Filter onClick={handleOrder} />
      <ProductSection state={state} orderFilter={orderFilter} isSearch={true} />
    </div>
  );
}
