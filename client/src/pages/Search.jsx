import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import Filter from '../components/Filter';
import ProductSection from '../components/ProductSection';

export default function Search() {
  const [orderFilter, setOrderFilter] = useState();
  const location = useLocation();
  const keyword = new URLSearchParams(location.search).get('keyword');

  const handleOrder = (order) => setOrderFilter((prev) => (prev = order));

  return (
    <div className="w-full p-4">
      <p>
        <strong>{keyword}</strong>의 검색 결과
      </p>
      <Filter onClick={handleOrder} />
      <ProductSection
        state={keyword}
        orderFilter={orderFilter}
        isSearch={true}
      />
    </div>
  );
}
