import React, { useState } from 'react';
import { Link, useParams } from 'react-router-dom';

import Filter from '../components/Filter';
import ProductSection from '../components/ProductSection';
import { categories } from '../constants/categories';

export default function CategoryProducts() {
  const { id } = useParams();
  const [orderFilter, setOrderFilter] = useState();
  const subdivision = getSubdivision(id);
  const handleOrder = (order) => setOrderFilter((prev) => (prev = order));

  return (
    <div className="flex flex-col w-full p-4">
      <h2 className="flex mb-4">
        {subdivision.map((category) => (
          <Link to={`/categories/${category.code}`} key={category.code}>
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
      <Filter onClick={handleOrder} />
      <ProductSection state={id} orderFilter={orderFilter} />
    </div>
  );
}

const getSubdivision = (id) => {
  for (const category of categories) {
    if (category.code === id[0]) return category.subdivision;
  }
};
