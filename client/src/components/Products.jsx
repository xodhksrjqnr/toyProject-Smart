import React from 'react';
import { categories } from '../constants/categories';
import CategorySection from './CategorySection';

export default function Products() {
  return (
    <div className="w-full">
      {categories.map((category, index) => (
        <CategorySection key={index} category={category} />
      ))}
    </div>
  );
}
