import React, { useEffect, useState } from 'react';
import CartItem from '../components/CartItem';
import { v4 as uuidv4 } from 'uuid';
import { payment } from '../api/payment';

export default function Carts() {
  const [cartList, setCartList] = useState(
    localStorage.getItem('cart')
      ? JSON.parse(localStorage.getItem('cart'))
      : { items: [] }
  );

  const totalPrice =
    cartList &&
    cartList.items.reduce(
      (prev, current) => prev + current.price * current.quantity,
      0
    );

  const handleDelete = (id, size) => {
    setCartList((prev) => ({
      ...prev,
      items: prev.items.filter(
        (item) => item.productId !== id || item.size !== size
      ),
    }));
  };

  useEffect(() => {
    localStorage.setItem('cart', JSON.stringify(cartList));
  }, [cartList]);

  const handlePayment = () => {
    payment(cartList.items) //
      .then((res) => console.log(res));
  };

  return (
    <div className="w-full py-8">
      <div className="flex flex-col items-center">
        <header className="w-2/3 max-w-96 flex text-center bg-white p-2 border-2 border-blue-800 rounded-2xl mb-4">
          <span className="w-24">이미지</span>
          <span className="w-2/5">제품명</span>
          <span className="w-1/5">사이즈</span>
          <span className="w-1/5">가격</span>
          <span className="w-1/5">수량</span>
        </header>
        <ul className="w-2/3 max-w-96">
          {cartList &&
            cartList.items.map((item) => (
              <CartItem key={uuidv4()} item={item} onDelete={handleDelete} />
            ))}
        </ul>
      </div>
      <div className="w-2/3 flex flex-col my-0 mx-auto items-end">
        <strong className="mb-2 text-2xl">
          총 금액: {totalPrice && totalPrice.toLocaleString()}원
        </strong>
        <button
          className={`${
            cartList.items.length === 0
              ? 'bg-gray-400'
              : 'bg-blue-600 hover:brightness-125'
          } py-2 px-8 text-white text-xl rounded-lg`}
          onClick={handlePayment}
          disabled={cartList.items.length === 0}
        >
          결제
        </button>
      </div>
    </div>
  );
}
