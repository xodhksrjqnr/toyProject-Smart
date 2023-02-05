import React, { useEffect, useState } from 'react';
import OrderQuantity from './OrderQuantity';

export default function OrderList({ cart, price, onChangeQuantity }) {
  const [total, setTotal] = useState(1);

  const handleMinus = () => {
    if (total < 2) return;
    setTotal((prev) => prev - 1);
  };
  const handlePlus = () => {
    setTotal((prev) => prev + 1);
  };

  return (
    <ul className="bg-gray-100 mb-4">
      {cart.items.map((item) => (
        <li
          key={item.size}
          className="border-b-2 px-2 py-1 flex justify-between"
        >
          <span className="text-slate-600">{item.size}</span>
          <div className="flex">
            <div className="flex items-center mr-2">
              <OrderQuantity
                onMinus={handleMinus}
                onPlus={handlePlus}
                size={item.size}
                onChangeQuantity={onChangeQuantity}
              />
            </div>

            <span>{price.toLocaleString()}</span>
          </div>
        </li>
      ))}
      <div className="flex justify-between py-4 px-2 font-bold bg-gray-200">
        <p>총 상품 금액: </p>
        <span>{(total * price).toLocaleString()}원</span>
      </div>
    </ul>
  );
}
