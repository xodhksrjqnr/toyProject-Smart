import React, { useState } from 'react';
import { categories } from '../constants/categories';
import OrderList from './OrderList';

export default function ProductOrderForm({ productDetail }) {
  const [orderSize, setOrderSize] = useState([]);
  const { name, price, size, code } = productDetail;
  const [main, sub] = classification(code);
  const handleClick = (e) => {
    const { value } = e.target;
    if (value === '') return;

    setOrderSize((prev) => {
      if (orderSize.includes(value)) return [...prev];
      return [...prev, value];
    });
  };
  const handleSumbit = (e) => {
    e.preventDefault();
  };

  return (
    <section className="p-2 w-full lg:w-96 border-2">
      <h1 className="text-2xl font-bold mb-4">{name}</h1>
      <div className="mb-2 py-2 border-b-2 border-slate-200">
        <h2 className="font-bold">Product Info</h2>
        <p className="text-sm text-slate-800">
          {main} &gt; {sub}
        </p>
      </div>
      <div className="mb-12 py-2 border-b-2 border-slate-200">
        <h2 className="font-bold">Price Info</h2>
        <span>{price.toLocaleString()}원</span>
      </div>
      <div>
        <form onSubmit={handleSumbit}>
          <select
            name="size"
            id="size"
            className="w-full border-2 border-black"
            onClick={handleClick}
          >
            <option value="">사이즈 선택</option>
            {size.split(',').map((value) => (
              <option key={value} value={value}>
                {value}
              </option>
            ))}
          </select>
          {orderSize && <OrderList orderSize={orderSize} price={price} />}
          <button className="bg-blue-400 p-2 w-full font-bold rounded-md">
            구매하기
          </button>
        </form>
      </div>
    </section>
  );
}

function classification(code) {
  let main;
  let sub;

  for (const category of categories) {
    if (category.code === code[0]) {
      main = category.main;

      for (const subdivision of category.subdivision) {
        if (subdivision.code === code) {
          sub = subdivision.title;
        }
      }
    }
  }
  return [main, sub];
}
