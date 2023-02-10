import React from 'react';
import { Link } from 'react-router-dom';

export default function ProductCard({ product }) {
  return (
    <li className="m-2 bg-white rounded-lg overflow-hidden drop-shadow-xl hover:scale-95 cursor-pointer border-2 border-neutral-200">
      <Link to={`/products/${product.productId}`}>
        <img
          className="w-48 h-48 object-cover object-center "
          src={product.imgFiles[0]}
          alt={product.name}
        />
        <div className="p-2 text-sm">
          <p className="underline underline-offset-2 text-slate-700 w-40  whitespace-nowrap block text-ellipsis overflow-hidden">
            {product.name}
          </p>
          <span className="text-xs text-slate-500">
            {product.price.toLocaleString()}원
          </span>
        </div>
      </Link>
    </li>
  );
}
