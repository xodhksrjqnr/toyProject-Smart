import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import Filter from '../components/Filter';
import SearchResult from '../components/SearchResult';

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
      <SearchResult state={state} orderFilter={orderFilter} />
    </div>
  );
}
