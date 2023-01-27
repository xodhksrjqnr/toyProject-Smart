import React from 'react';
import { Link } from 'react-router-dom';

export default function Admin() {
  return (
    <div className="w-full p-8">
      <button className="bg-rose-400 rounded-xl p-1 hover:bg-rose-300">
        <Link to="register" className="text-sm text-white text-bold">
          상품 추가
        </Link>
      </button>
    </div>
  );
}
