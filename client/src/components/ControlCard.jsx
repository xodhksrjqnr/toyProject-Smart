import React from 'react';
import ProductCard from './ProductCard';

export default function ControlCard({ product }) {
  return (
    <div className="mb-8">
      <div className="flex justify-center text-sm relative top-3">
        <button className="w-1/3 bg-blue-300 rounded-xl p-1 mr-2 hover:bg-blue-200">
          수정
        </button>
        <button className="w-1/3 bg-blue-300 rounded-xl p-1 hover:bg-blue-200">
          삭제
        </button>
      </div>
      <ProductCard product={product} />
    </div>
  );
}
