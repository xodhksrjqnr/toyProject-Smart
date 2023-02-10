import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import ProductCard from './ProductCard';
import Modal from './Modal';

export default function ControlCard({ product }) {
  const [modal, setModal] = useState(false);
  const handleClick = () => setModal((prev) => !prev);

  return (
    <div className="mb-8">
      <div className="flex justify-center text-sm relative top-3">
        <button className="w-1/3 bg-blue-300 rounded-xl p-1 mr-2 hover:bg-blue-200">
          <Link to={`update/${product.productId}`}>수정</Link>
        </button>
        <button
          className="w-1/3 bg-blue-300 rounded-xl p-1 hover:bg-blue-200"
          onClick={handleClick}
        >
          삭제
        </button>
      </div>
      {modal && <Modal product={product} toggleModal={setModal} />}
      <ProductCard product={product} />
    </div>
  );
}
