import React from 'react';
import { useNavigate } from 'react-router-dom';
import { IoMdTrash } from 'react-icons/io';

export default function CartItem({ item, onDelete }) {
  const { productId, img, name, price, quantity, size } = item;
  const navigate = useNavigate();
  const handleNavigate = () => {
    navigate(`/products/${productId}`);
  };

  return (
    <li className="flex items-center text-center p-2 border-2 rounded-md mb-2 bg-gray-200 relative">
      <img
        className="w-24 h-24 object-cover cursor-pointer"
        src={img}
        alt={name}
        onClick={handleNavigate}
      />
      <strong className="w-2/5 cursor-pointer" onClick={handleNavigate}>
        {name}
      </strong>
      <span className="w-1/5">{size}</span>
      <span className="w-1/5">{price.toLocaleString()}</span>
      <span className="w-1/5">{quantity}</span>
      <button
        onClick={() => onDelete(productId, size)}
        className="absolute -left-12 p-4 hover:text-red-600"
      >
        <IoMdTrash />
      </button>
    </li>
  );
}
